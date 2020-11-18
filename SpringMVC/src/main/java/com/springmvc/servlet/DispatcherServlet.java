/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: DispatcherServlet
 * Author:   Slatter
 * Date:     2020/11/17 0:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.springmvc.servlet;

import com.springmvc.annotation.Controller;
import com.springmvc.annotation.RequestMapping;
import com.springmvc.annotation.RequestParam;
import com.springmvc.context.WebApplicationContext;
import com.springmvc.exception.ContextException;
import com.springmvc.handler.MyHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/11/17
 * @description: SpringMVC核心控制器
 * @since 1.0.0
 */
@WebServlet(name = "DispatcherServlet", urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    // 存储url和对象方法的映射关系
    List<MyHandler> handlerList = new ArrayList<MyHandler>();
    private WebApplicationContext webApplicationContext;

    @Override
    public void init() throws ServletException {
        // 1.servlet初始化的时候,读取初始化参数
        String contextConfigLocation = this.getServletConfig().getInitParameter("contextConfigLocation");

        // 2.创建Spring容器
        webApplicationContext = new WebApplicationContext(contextConfigLocation);
        // 3.初始化Spring容器
        webApplicationContext.refresh();
        // 4.初始化请求映射
        initHandleMapping();
        System.out.println("请求地址和控制器方法的映射关系" + handlerList);
    }

    // 初始化请求映射
    public void initHandleMapping() {
        // 判断iocMap中是否有bean对象
        if (webApplicationContext.iocMap.isEmpty()) {
            throw new ContextException("Spring容器为空");
        } else {
            for (Map.Entry<String, Object> entry : webApplicationContext.iocMap.entrySet()) {
                Class<?> aClass = entry.getValue().getClass();
                if (aClass.isAnnotationPresent(Controller.class)) {
                    Method[] declaredMethods = aClass.getDeclaredMethods();
                    for (Method declaredMethod : declaredMethods) {
                        if (declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMappingAnnotation = declaredMethod.getAnnotation(RequestMapping.class);
                            //user/query
                            String url = requestMappingAnnotation.value();
                            MyHandler handler = new MyHandler(url, entry.getValue(), declaredMethod);
                            handlerList.add(handler);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 进行请求的分发处理
        executeDispatch(req, resp);
    }

    // 请求的分发处理
    public void executeDispatch(HttpServletRequest req, HttpServletResponse resp) {
        MyHandler handler = getHandler(req);
        try {
            if (handler == null) {
                resp.getWriter().print("<h1>404 NOT FOUND </h1>");
            } else {
                Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
                // 定义一个参数的数组
                Object[] params = new Object[parameterTypes.length];

                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    if ("HttpServletRequest".equals(parameterType.getSimpleName())) {
                        params[i] = req;
                    } else if ("HttpServletResponse".equals(parameterType.getSimpleName())) {
                        params[i] = resp;
                    }
                }

                // 获取请求中的参数集合
                Map<String, String[]> parameterMap = req.getParameterMap();
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue()[0];
                    int index = hasRequestParm(handler.getMethod(), name);
                    if (index != -1) {
                        params[index] = value;
                    }
                }

                // 调用控制器中的方法
                Object direct = handler.getMethod().invoke(handler.getController(), params);
                if (direct instanceof String) {
                    // 跳转jsp
                    String viewName = (String) direct;
                    if (viewName.contains(":")) {
                        String viewType = viewName.split(":")[0];
                        String viewPage = viewName.split(":")[1];
                        if (viewType.equals("forward")) {
                            // 请求转发
                            req.getRequestDispatcher(viewPage).forward(req, resp);
                        } else {
                            // 重定向
                            resp.sendRedirect(viewPage);
                        }
                    } else {
                        // 默认请求转发
                        req.getRequestDispatcher(viewName).forward(req, resp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 获取请求对应的handler
    public MyHandler getHandler(HttpServletRequest req) {
        String requestUrl = req.getRequestURI();
        for (MyHandler myHandler : handlerList) {
            if (myHandler.getUrl().equals(requestUrl)) {
                return myHandler;
            }
        }
        return null;
    }

    // 判断控制器方法的参数是否有Requestparm的注解 且找到对应value值 如果找到返回这个参数的位置，没有找到返回-1
    public int hasRequestParm(Method method, String name) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(RequestParam.class)) {
                RequestParam requestParm = parameter.getAnnotation(RequestParam.class);
                String requestParmValue = requestParm.value();
                if ("".equals(requestParmValue)) {
                    requestParmValue = getParameterNames(method).get(i);
                }
                if (name.equals(requestParmValue)) {
                    return i;
                }
            }
        }
        return -1;
    }

    // 获取控制器方法的参数的名字
    public List<String> getParameterNames(Method method) {
        List<String> list = new ArrayList<String>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            list.add(parameter.getName());
        }
        return list;
    }
}
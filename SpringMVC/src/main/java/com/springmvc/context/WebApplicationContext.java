/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: WebApplicationContext
 * Author:   Slatter
 * Date:     2020/11/17 16:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.springmvc.context;

import com.springmvc.annotation.AutoWired;
import com.springmvc.annotation.Controller;
import com.springmvc.annotation.Service;
import com.springmvc.exception.ContextException;
import com.springmvc.xml.XmlParser;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/11/17
 * @since 1.0.0
 */
public class WebApplicationContext {
    // Spring的ioc容器
    public Map<String, Object> iocMap = new ConcurrentHashMap<String, Object>();
    // springmvc.xml
    String contextConfigLocation;
    // 存储扫描后得到的所有类名
    List<String> classNameList = new ArrayList<String>();


    public WebApplicationContext(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }

    // 初始化Spring容器
    public void refresh() {
        //解析springmvc.xml文件
        String basePackage = XmlParser.getbasePackage(contextConfigLocation.split(":")[1]);
        String[] basePackages = basePackage.split(",");
        if (basePackages.length > 0) {
            for (String pack : basePackages) {
                // 依次扫描每个文件夹得到其中的类   com.slatter.service    com.slatter.controller
                executeScanPackage(pack);
            }
        }
        // 实例化Spring容器中的bean
        executeInstance();

        // 实现Spring容器中对象的注入
        executeAutoWired();
    }

    // 扫描包
    public void executeScanPackage(String pack) {
        // 得到全路径并将.转换为/         com/slatter/service        com/slatter/controller
        URL url = this.getClass().getClassLoader().getResource("/" + pack.replaceAll("\\.", "/"));
        String path = url.getFile();
        File dir = new File(path);
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                // 当前是文件夹  com.slatter.service.impl
                executeScanPackage(pack + "." + f.getName());
            } else {
                // 当前是文件,获取全路径   com.slatter.service.impl.UserServiceImpl
                String className = pack + "." + f.getName().replaceAll(".class", "");
                classNameList.add(className);
            }
        }
    }

    // 实例化Spring容器中的bean对象
    public void executeInstance() {
        if (classNameList.size() == 0) {
            //没有扫描到需要实例化的类
            throw new ContextException("No class to instantiate");
        } else {
            for (String className : classNameList) {
                try {
                    Class<?> aClass = Class.forName(className);
                    // 判断某个类是否使用到了某个注解
                    if (aClass.isAnnotationPresent(Controller.class)) {
                        // com.slatter.controller.UserController   控制层的类   userController
                        Controller controllerAnnotation = aClass.getAnnotation(Controller.class);
                        String beanName = controllerAnnotation.value();
                        if ("".equals(beanName)) {
                            // 未对Controller命名则用类名（首字母小写）
                            String name = aClass.getSimpleName().substring(0, 1).toLowerCase() + aClass.getSimpleName().substring(1);
                            iocMap.put(name, aClass.newInstance());
                        } else {
                            iocMap.put(beanName, aClass.newInstance());
                        }
                    } else if (aClass.isAnnotationPresent(Service.class)) {
                        // com.slatter.service.FileService   fileService
                        Service serviceAnnotation = aClass.getAnnotation(Service.class);
                        String beanName = serviceAnnotation.value();
                        if ("".equals(beanName)) {
                            // 未对Service命名则用类名（首字母小写）
                            String name = aClass.getSimpleName().substring(0, 1).toLowerCase() + aClass.getSimpleName().substring(1);
                            iocMap.put(name, aClass.newInstance());
                        } else {
                            iocMap.put(beanName, aClass.newInstance());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // 实现Spring容器中的对象的依赖注入
    public void executeAutoWired() {
        try {
            if (iocMap.isEmpty()) {
                throw new ContextException("No bean to instantiate");
            } else {
                for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
                    String key = entry.getKey();
                    Object bean = entry.getValue();
                    Field[] declaredFields = bean.getClass().getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        if (declaredField.isAnnotationPresent(AutoWired.class)) {
                            AutoWired autoWiredAnnotation = declaredField.getAnnotation(AutoWired.class);
                            String beanName = autoWiredAnnotation.value();
                            if ("".equals(beanName)) {
                                Class<?> type = declaredField.getType();
                                beanName = type.getSimpleName().substring(0, 1).toLowerCase() + type.getSimpleName().substring(1);
                            }
                            declaredField.setAccessible(true);
                            // 属性注入  调用反射给属性赋值
                            declaredField.set(bean, iocMap.get(beanName));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
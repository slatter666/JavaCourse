package listeners; /**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: filters.ClientFilter
 * Author:   Slatter
 * Date:     2020/10/21 17:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import utility.ClientUtil;
import utility.HttpUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/21
 * @since 1.0.0
 */
@WebFilter(filterName = "clientFilter", urlPatterns = "/*")
public class ClientFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        // 判断是否有局部会话
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");

        if(isLogin!=null && isLogin){
            // 有局部会话
            chain.doFilter(request,response);
            return;
        }
        // 判断地址栏中是否携带token参数
        String token = req.getParameter("token");
        if(token != null && token.length() > 0 && token.trim().length() > 0) {
            //token信息不为空,说明地址中包含了token,拥有令牌,判断token是否由统一认证中心产生
            String httpURL = ClientUtil.SERVER_URL_PREFIX + "/verify";
            Map<String, String> params = new HashMap<String, String>();
            params.put("token", token);
            params.put("client", ClientUtil.getClientLogOutUrl());
            params.put("sessionId", session.getId());
            try {
                String isVerify = HttpUtil.sendHttpRequest(httpURL, params);
                if("true".equals(isVerify)) {
                    //如果返回的字符串是true,说明这个token是由统一认证中心产生的
                    //创建局部会话
                    session.setAttribute("isLogin", true);
                    chain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有局部会话,重定向到统一认证中心,检查是否有其它系统已经登陆过
        ClientUtil.redirectToSSO(req, resp);
    }
}
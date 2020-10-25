/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: LoginController
 * Author:   Slatter
 * Date:     2020/10/22 20:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/22
 * @since 1.0.0
 */
@WebServlet(name = "checkLogin", urlPatterns = "/checkLogin")
public class CheckLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String redirectUrl = req.getParameter("redirectUrl");
        // 判断是否有全局会话
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute("token");
        if(token==null || !(token.length() > 0)){
            // 没有全局会话
            req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req,resp);
        } else {
            // 有全局会话，取出令牌信息，重定向到redirectUrl，并把令牌带上
            resp.sendRedirect(redirectUrl+"?token="+token);
        }
    }
}
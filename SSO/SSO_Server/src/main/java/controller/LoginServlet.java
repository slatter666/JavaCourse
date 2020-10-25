/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SSOServer
 * Author:   Slatter
 * Date:     2020/10/22 1:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package controller;

import database.DB;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/22
 * @since 1.0.0
 */
@WebServlet(name = "loginServle", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirectUrl = req.getParameter("redirectUrl");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User(username, password);

        if (DB.findUser(user)) {
            // 账号密码匹配
            String token = UUID.randomUUID().toString();
            // 创建全局会话，把令牌信息放入会话中
            HttpSession session = req.getSession();
            session.setAttribute("token", token);
            // 把令牌信息放入数据库中
            DB.addToken(token);
            resp.sendRedirect(redirectUrl+"?token="+token);
        } else {
            // 如果账号密码有误重新回到登陆界面，还需要把redirectUrl的地址放入到request域中
            req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req,resp);
        }
    }
}
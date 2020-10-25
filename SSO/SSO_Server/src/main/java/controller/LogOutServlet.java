/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: LogOutServlet
 * Author:   Slatter
 * Date:     2020/10/25 10:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
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
 * @create 2020/10/25
 * @since 1.0.0
 */
@WebServlet(name = "logOutServlet", urlPatterns = "/logOut")
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 销毁全局会话
        HttpSession session = req.getSession();
        session.invalidate();
        // 获取出注册的子系统，依次调用子系统的登出方法
        req.getRequestDispatcher("WEB-INF/views/logOut.jsp").forward(req,resp);
    }
}
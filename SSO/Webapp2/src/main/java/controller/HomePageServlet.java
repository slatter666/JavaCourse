/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: HomePageServlet
 * Author:   Slatter
 * Date:     2020/10/21 23:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package controller;

import utility.ClientUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/21
 * @since 1.0.0
 */

@WebServlet(name = "homepageServlet", urlPatterns = "/home")
public class HomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("serverLogOutUrl", ClientUtil.getServerLogOutUrl());
        req.getRequestDispatcher("WEB-INF/views/home.jsp").forward(req, resp);
    }
}
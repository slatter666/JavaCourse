/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: VerifyServlet
 * Author:   Slatter
 * Date:     2020/10/24 22:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package controller;

import com.sun.istack.internal.Nullable;
import database.DB;
import domain.ClientInfo;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/24
 * @since 1.0.0
 */
@WebServlet(name = "verifyServlet", urlPatterns = "/verify")
public class VerifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = copyToString(req.getInputStream(), Charset.forName("utf-8"));
        String[] list = message.split("=|&");
        Map<String,String> params = new HashMap<String, String>();
        for(int i=0;i<list.length; i++)
        {
            if(i%2==0)
                params.put(list[i],list[i+1]);
        }
        String token = params.get("token");
        String clientLogOutUrl = params.get("client");
        String sessionId = params.get("sessionId");
        if(DB.isContainToken(token)){
            // 把客户端的登出地址记录
            List<ClientInfo> clientInfoList= DB.T_CLIENT_INFO.get(token);
            if(clientInfoList==null) {
                clientInfoList = new ArrayList<ClientInfo>();
                DB.T_CLIENT_INFO.put(token, clientInfoList);
            }
            ClientInfo info = new ClientInfo();
            info.setClientLogOutUrl(clientLogOutUrl);
            info.setSessionId(sessionId);
            clientInfoList.add(info);
            resp.getOutputStream().write("true".getBytes());
        } else {
            resp.getOutputStream().write("false".getBytes());
        }
    }

    public static String copyToString(@Nullable InputStream in, Charset charset) throws IOException {
        if (in == null) {
            return "";
        } else {
            StringBuilder out = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, charset);
            char[] buffer = new char[4096];

            int charsRead;
            while((charsRead = reader.read(buffer)) != -1) {
                out.append(buffer, 0, charsRead);
            }

            return out.toString();
        }
    }
}
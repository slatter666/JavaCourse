/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: FilterServlet
 * Author:   Slatter
 * Date:     2020/10/22 21:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package listeners;


import Utils.HttpUtil;
import database.DB;
import domain.ClientInfo;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/22
 * @since 1.0.0
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String token = (String) session.getAttribute("token");
        // 删除t_token表中的数据
        DB.removeToken(token);
        List<ClientInfo> infoList = DB.removeByToken(token);
        try {
            for (ClientInfo info : infoList) {
                // 获取出注册的子系统，依次调用子系统的登出方法
                HttpUtil.sendHttpRequest(info.getClientLogOutUrl(), info.getSessionId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
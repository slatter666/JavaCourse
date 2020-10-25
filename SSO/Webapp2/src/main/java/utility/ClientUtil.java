/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ClientUtil
 * Author:   Slatter
 * Date:     2020/10/21 23:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/21
 * @since 1.0.0
 */
public class ClientUtil {
    private static Properties ssoProperties = new Properties();
    public static String SERVER_URL_PREFIX;    //统一认证中心地址  http://www.sso.com:8080
    public static String CLIENT_HOST_URL;    //当前客户端地址 http://www.webapp1.com:8081
    static {
        try{
            ssoProperties.load(ClientUtil.class.getClassLoader().getResourceAsStream("sso.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SERVER_URL_PREFIX = ssoProperties.getProperty("server-url-prefix");
        CLIENT_HOST_URL = ssoProperties.getProperty("client-host-url");
    }

    // 当客户端请求被拦截,就跳往统一认证中心,需要带redirectUrl参数,统一认证中心登陆后回调原地址
    public static String getRedirectUrl(HttpServletRequest request) {
        // 获取请求URL
        return CLIENT_HOST_URL+ request.getServletPath();
    }

    public static void redirectToSSO(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String redirectURL = getRedirectUrl(request);
        StringBuilder url = new StringBuilder();
        url.append(SERVER_URL_PREFIX).append("/checkLogin?redirectUrl=").append(redirectURL);
        response.sendRedirect(url.toString());
    }

    // 获取客户端完整登出地址
    public static String getClientLogOutUrl() {
        return CLIENT_HOST_URL + "/logOut";
    }

    // 获取认证中心登出地址
    public static String getServerLogOutUrl() {
        return SERVER_URL_PREFIX + "/logOut";
    }

}
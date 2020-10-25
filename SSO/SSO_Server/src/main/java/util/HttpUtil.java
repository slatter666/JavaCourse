/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: HttpUtil
 * Author:   Slatter
 * Date:     2020/10/22 22:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package util;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/22
 * @since 1.0.0
 */
public class HttpUtil {
    /*模拟浏览器的请求
    httpURL 发送请求地址
    sessionId 会话Id
     */
    public static void sendHttpRequest(String httpURL, String sessionId) throws Exception {
        // 建立URL连接对象
        URL url = new URL(httpURL);
        // 创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置请求的方式
        conn.setRequestMethod("POST");
        // 设置需要输出
        conn.setDoOutput(true);
        conn.addRequestProperty("Cookie", "JSESSIONID="+sessionId);    //此处JSESSIONID不能写错了
        // 发送到请求服务器
        conn.connect();
        conn.getInputStream();
        conn.disconnect();
    }
}
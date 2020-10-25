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
package utility;

import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

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
    params 请求参数
     */
    public static String sendHttpRequest(String httpURL, Map<String, String> params) throws Exception {
        // 建立URL连接对象
        URL url = new URL(httpURL);
        // 创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置请求的方式
        conn.setRequestMethod("POST");
        // 设置需要输出
        conn.setDoOutput(true);
        // 判断是否有参数
        if(params!=null && params.size()>0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry: params.entrySet()) {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            // sb.substring(1)去除最前面的&
            conn.getOutputStream().write(sb.substring(1).toString().getBytes("utf-8"));
        }
        // 发送到请求服务器
        conn.connect();
        // 获取远程响应的内容
        String responseContent = copyToString(conn.getInputStream(), Charset.forName("utf-8"));
        conn.disconnect();
        return responseContent;
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
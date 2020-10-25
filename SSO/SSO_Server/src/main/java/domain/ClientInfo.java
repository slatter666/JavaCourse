/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ClientInfo
 * Author:   Slatter
 * Date:     2020/10/25 9:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package domain;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/25
 * @since 1.0.0
 */
public class ClientInfo {
    private String clientLogOutUrl;
    private String sessionId;

    public String getClientLogOutUrl() {
        return clientLogOutUrl;
    }

    public void setClientLogOutUrl(String clientLogOutUrl) {
        this.clientLogOutUrl = clientLogOutUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
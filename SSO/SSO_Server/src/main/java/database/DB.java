/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: DB
 * Author:   Slatter
 * Date:     2020/10/22 19:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package database;

import domain.ClientInfo;
import domain.User;

import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/22
 * @since 1.0.0
 */
public class DB {
    private static Set<User> users = new HashSet<>();
    private static Set<String> T_TOKEN = new HashSet<String>();
    public static Map<String, List<ClientInfo>> T_CLIENT_INFO = new HashMap<String, List<ClientInfo>>();

    static {
        addUser("xiaoming","123456");
        addUser("xiaohong","456789");
    }

    // 添加用户，可用于扩展注册功能，此处暂不实现
    public static void addUser(String username, String password) {
        User user = new User(username, password);
        users.add(user);
    }

    // 查找数据库是否存在这位用户
    public static boolean findUser(User user) {
        for (User u : users) {
            if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    // 存储token信息
    public static void addToken(String token) {
        T_TOKEN.add(token);
    }

    // 移除token信息
    public static void removeToken(String token) {
        T_TOKEN.remove(token);
    }

    // 查找T_TOKEN中是否包含传来的token信息
    public static boolean isContainToken(String token) {
        if(T_TOKEN.contains(token)){
            return true;
        } else {
            return false;
        }
    }


}
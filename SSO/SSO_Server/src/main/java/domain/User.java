/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: User
 * Author:   Slatter
 * Date:     2020/10/25 13:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package domain;

import java.util.Objects;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Slatter
 * @create 2020/10/25
 * @since 1.0.0
 */
public class User {
    private String username;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }


}
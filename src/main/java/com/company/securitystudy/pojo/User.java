package com.company.securitystudy.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: Liuchong
 * Description: 自定义用户类
 * date: 2019/8/20 10:50
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 3497935890426858541L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    /**
     * 用户没有过期
     */
    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    /**
     * 用户没有被锁定
     */
    @Column(name = "account_non_locked")
    private boolean accountNonLocked= true;

    /**
     * 凭证没有过期
     */
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired= true;

    /**
     * 用户启用
     */
    @Column(name = "enabled")
    private boolean enabled= true;

    public User() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"userName\":\"")
                .append(userName).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"accountNonExpired\":")
                .append(accountNonExpired);
        sb.append(",\"accountNonLocked\":")
                .append(accountNonLocked);
        sb.append(",\"credentialsNonExpired\":")
                .append(credentialsNonExpired);
        sb.append(",\"enabled\":")
                .append(enabled);
        sb.append('}');
        return sb.toString();
    }
}

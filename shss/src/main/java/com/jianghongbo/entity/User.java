package com.jianghongbo.entity;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-17 20:02
 * @description：用户类
 */
public class User {

    private int id;
    //用户名
    private String username;
    //用户密码
    private String password;
    //用户头像
    private String portrait;
    //上次登陆时间
    private String login_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }
}

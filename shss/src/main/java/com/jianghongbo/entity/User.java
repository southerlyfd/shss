package com.jianghongbo.entity;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-17 20:02
 * @description：用户类
 */
public class User {

    private String id;
    private String userName;
    private String passWord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

package com.jianghongbo.entity;

import lombok.*;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-17 20:02
 * @description：用户类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    //上次登陆时间
    private String token;


}

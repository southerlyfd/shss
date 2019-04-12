package com.jianghongbo.dao;

import com.jianghongbo.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao {

    /**
     * 用户列表查询
     * @param user
     * @return
     */
    List<UserInfo> getUserList(UserInfo user);

    /**
     * 用户信息查询
     * @param user
     * @return
     */
    UserInfo getUser(UserInfo user);

    /**
     * 用户注册
     * @param user
     */
    void registerUserInfo(UserInfo user);
    /**
     * 用户信息修改
     * @param user
     */
    void updateUserInfo(UserInfo user);

    /**
     * 根据shssToken查询用户信息
     * @param shssToken
     * @return
     */
    List<UserInfo> getUserInfoByToken(String shssToken);
}

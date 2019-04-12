package com.jianghongbo.dao;

import com.jianghongbo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao {

    /**
     * 用户列表查询
     * @param user
     * @return
     */
    List<User> getUserList(User user);

    /**
     * 用户信息查询
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 用户注册
     * @param user
     */
    void registerUserInfo(User user);
    /**
     * 用户信息修改
     * @param user
     */
    void updateUserInfo(User user);

    /**
     * 根据shssToken查询用户信息
     * @param shssToken
     * @return
     */
    List<User> getUserInfoByToken(String shssToken);
}

package com.jianghongbo.service.api;

import com.jianghongbo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-16 21:42
 * @description：
 */

public interface UserService {

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

}

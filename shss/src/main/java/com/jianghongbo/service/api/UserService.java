package com.jianghongbo.service.api;

import com.jianghongbo.common.ServiceResult;
import com.jianghongbo.entity.UserInfo;

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
     * 获取用户登录信息
     * @param shssToken
     * @return
     */
    ServiceResult<UserInfo> getLoginInfo(String shssToken);

    // 测试异步
    void sendSms();
}

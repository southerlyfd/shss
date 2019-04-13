package com.jianghongbo.dao;

import com.jianghongbo.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoWriterMapper {

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
}

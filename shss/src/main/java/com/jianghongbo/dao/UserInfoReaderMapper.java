package com.jianghongbo.dao;

import com.jianghongbo.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoReaderMapper {

    /**
     * 用户列表查询
     * @param user
     * @return
     */
    List<UserInfo> getUserList(UserInfo user);


    /**
     * 根据shssToken查询用户信息
     * @param shssToken
     * @return
     */
    List<UserInfo> getUserInfoByToken(String shssToken);
}

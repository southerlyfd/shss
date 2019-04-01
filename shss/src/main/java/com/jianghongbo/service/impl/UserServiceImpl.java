package com.jianghongbo.service.impl;

import com.jianghongbo.dao.UserDao;
import com.jianghongbo.entity.User;
import com.jianghongbo.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-16 21:44
 * @description：
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUserList(User user) {
        log.info("getUserInfo：" + user.toString());
        return userDao.getUserList(user);
    }

    @Override
    public User getUser(User user) {
        log.info("getUserInfo：" + user.toString());
        return userDao.getUser(user);
    }

    @Override
    public void registerUserInfo(User user) {
        log.info("registerUserInfo：" + user.toString());
        userDao.registerUserInfo(user);
    }

    @Override
    public void updateUserInfo(User user) {
        log.info("updateUserInfo：" + user.toString());
        userDao.updateUserInfo(user);
    }
}

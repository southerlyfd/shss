package com.jianghongbo.service.impl;

import com.jianghongbo.dao.UserDao;
import com.jianghongbo.entity.User;
import com.jianghongbo.service.api.UserService;
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
@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUserList(User user) {
        logger.info("getUserInfo：" + user.toString());
        return userDao.getUserList(user);
    }

    @Override
    public void registerUserInfo(User user) {
        logger.info("registerUserInfo：" + user.toString());
        userDao.registerUserInfo(user);
    }

    @Override
    public void updateUserInfo(User user) {
        logger.info("updateUserInfo：" + user.toString());
        userDao.updateUserInfo(user);
    }
}

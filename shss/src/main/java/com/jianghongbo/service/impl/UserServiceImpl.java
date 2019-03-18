package com.jianghongbo.service.impl;

import com.jianghongbo.dao.UserDao;
import com.jianghongbo.entity.User;
import com.jianghongbo.service.api.UserService;
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

    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUserList(User user) {
        return userDao.getUserList(user);
    }
}

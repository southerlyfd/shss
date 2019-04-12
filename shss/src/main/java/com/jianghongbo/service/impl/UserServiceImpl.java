package com.jianghongbo.service.impl;

import com.jianghongbo.common.ServiceResult;
import com.jianghongbo.dao.UserDao;
import com.jianghongbo.entity.User;
import com.jianghongbo.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
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

	@Override
	public ServiceResult<User> getLoginInfo(String shssToken) {
		ServiceResult<User> result = new ServiceResult<User>();
		// 判断用户信息是否已过期
		List<User> userInfoList = userDao.getUserInfoByToken(shssToken);
		if (userInfoList == null || userInfoList.size() == 0) {
			result.setOk(false);
		} else {
			result.setOk(true);
			// 增加用户信息返回值
			result.setData(userInfoList.get(0));
		}
		return result;
	}
    
    
}

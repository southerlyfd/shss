package com.jianghongbo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianghongbo.common.ServiceResult;
import com.jianghongbo.entity.User;
import com.jianghongbo.service.api.UserService;

/**
 * @ClassName: BaseController.java
 * @Description: 类说明
 * @author: jianghb
 * @date: 2019年4月12日
 */
public class BaseController {

	@Autowired
    UserService userService;
	
	/**
	 * 判断用户登录是否过期
	 * @param shssToken 用户登陆钥匙
	 * @return 用户信息
	 */
	protected User getEffectiveLogin(String shssToken) {
		// 返回值定义
		User user = null;
		// 获取用户登录信息
		ServiceResult<User> userList = userService.getLoginInfo(shssToken);
		// 判断登陆有效时间是否已过期
		if (userList.isOk() && userList.getData() != null) {
			user = userList.getData();
		}		
		return user;
	}
}

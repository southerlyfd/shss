package com.jianghongbo.service.impl;

import com.jianghongbo.common.ServiceResult;
import com.jianghongbo.common.consts.CommonConst;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.common.exception.ShssException;
import com.jianghongbo.common.util.StringUtil;
import com.jianghongbo.dao.UserInfoReaderMapper;
import com.jianghongbo.dao.UserInfoWriterMapper;
import com.jianghongbo.entity.UserInfo;
import com.jianghongbo.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-16 21:44
 * @description：
 */
@Slf4j
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserInfoReaderMapper userInfoReaderMapper;
    @Autowired
    UserInfoWriterMapper userInfoWriterMapper;

    @Value("${images.url}")
    private String url;

    @Override
    public List<UserInfo> getUserList(UserInfo user) {
        log.info("getUserInfo：" + user.toString());
        List<UserInfo> userInfoList = userInfoReaderMapper.getUserList(user);
        if (userInfoList == null || userInfoList.size() == 0) {
            log.info("userInfoList 为 null 未找到结果！");
            return null;
        }
        for (UserInfo userInfo : userInfoList) {
            userInfo.setPortrait(url + StringUtil.trimNull(userInfo.getPortrait()));
        }
        return userInfoList;
    }

    @Override
    public UserInfo getUser(UserInfo user) {
        log.info("getUserInfo：" + user.toString());
        List<UserInfo> userInfoList = userInfoReaderMapper.getUserList(user);
        if (userInfoList == null || userInfoList.size() == 0) {
            log.info("userInfoList 为 null 未找到用户！");
            return null;
        }
        userInfoList.get(0).setPortrait(url + StringUtil.trimNull(userInfoList.get(0).getPortrait()));
        return userInfoList.get(0);
    }

    @Override
    public void registerUserInfo(UserInfo user) {
        log.info("registerUserInfo：[{}]", user.toString());
        List<UserInfo> userInfoList = userInfoReaderMapper.getUserList(user);
        if (userInfoList != null && userInfoList.size() > 0) {
            throw new ShssException(StateCodeConstant.ERROR_CODE, CommonConst.USERNAME_IS_EXIST);
        }
        userInfoWriterMapper.registerUserInfo(user);
    }

    @Override
    public void updateUserInfo(UserInfo user) {
        log.info("updateUserInfo：[{}]", user.toString());
        userInfoWriterMapper.updateUserInfo(user);
    }

	@Override
	public ServiceResult<UserInfo> getLoginInfo(String shssToken) {
		ServiceResult<UserInfo> result = new ServiceResult<UserInfo>();
		// 判断用户信息是否已过期
		List<UserInfo> userInfoList = userInfoReaderMapper.getUserInfoByToken(shssToken);
		if (userInfoList == null || userInfoList.size() == 0) {
			result.setOk(false);
		} else {
			result.setOk(true);
			// 增加用户信息返回值
			result.setData(userInfoList.get(0));
		}
		return result;
	}

    /**
     * 测试异步
     */
    @Async
    @Override
    public void sendSms() {
        System.out.println("###IndexController###     2");
        IntStream.range(0, 5).forEach(d -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        System.out.println("###IndexController###     3");
    }


}

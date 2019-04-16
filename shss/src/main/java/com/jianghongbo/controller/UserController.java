package com.jianghongbo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianghongbo.common.JsonResult;
import com.jianghongbo.common.annotation.UserLoginToken;
import com.jianghongbo.common.consts.CommonConst;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.common.exception.ShssException;
import com.jianghongbo.common.util.StringUtil;
import com.jianghongbo.entity.UserInfo;
import com.jianghongbo.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ：taoyl
 * @date ：Created in 2019-03-16 21:36
 * @description：
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;



    /**
     * 用户列表查询
     * @param userParam
     * @return
     */
    @UserLoginToken
    @RequestMapping("/queryUserList")
    public JsonResult queryUserList(UserInfo userParam){
        JsonResult result = new JsonResult();
        PageHelper.startPage(1, 10);
        List<UserInfo> userList = userService.getUserList(userParam);
        PageInfo<UserInfo> userPageInfo = new PageInfo<>(userList);
        result.setData(userPageInfo);
        return result;
    }

    /**
     * 查询用户
     * @param userParam
     * @return
     */
    @RequestMapping("/queryUser")
    public JsonResult queryUser(UserInfo userParam){
    	JsonResult result = new JsonResult();
        UserInfo user = userService.getUser(userParam);
        result.setData(user);
        return result;
    }

    /**
     *  用户注册
     * @param user
     * @return
     */
    @RequestMapping("/registerUser")
    public JsonResult registerUserInfo(UserInfo user){
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(StringUtils.trim(username))) {
            throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, CommonConst.USERNAME_NOT_NULL);
        }
        if (StringUtils.isBlank(StringUtils.trim(password))) {
            throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, CommonConst.PASSWORD_NOT_NULL);
        }
        JsonResult result = new JsonResult();
        userService.registerUserInfo(user);
        result.setErrMsg("注册成功");
        return result;
    }

    /**
     *  用户登陆
     * @param userParam
     * @return
     */
    @RequestMapping("/login")
    //@Transactional  //事务回滚
    public JsonResult login(UserInfo userParam){
        JsonResult result = new JsonResult();
        String username = userParam.getUsername();
        String password = userParam.getPassword();
        if (StringUtils.isBlank(StringUtils.trim(username))) {
            throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, CommonConst.USERNAME_NOT_NULL);
        }
        if (StringUtils.isBlank(StringUtils.trim(password))) {
            throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, CommonConst.PASSWORD_NOT_NULL);
        }
        UserInfo user = userService.getUser(userParam);
        if (user != null){
            if (!userParam.getPassword().equals(user.getPassword())) {
                throw new ShssException(StateCodeConstant.ERROR_PARAM_CODE, CommonConst.PASSWORD_IS_WRONG);
            } else {
                int id = user.getId();
                UserInfo login_user = new UserInfo();
                login_user.setId(id);
                login_user.setLoginTime("now");
                String token = StringUtil.getToken();
                login_user.setShssToken(token);
                // 修改token
                userService.updateUserInfo(login_user);
                Map<String, String> map = new HashMap<>();
                map.put(CommonConst.SHSS_TOKEN, token);
                result.setData(map);
            }
        } else {
            throw new ShssException(StateCodeConstant.ERROR_PARAM_CODE, CommonConst.USER_NOT_EXIST);
        }
        result.setErrMsg("登陆成功");
        return result;
    }

    /**
     * 获取用户信息
     * @param shssToken
     * @return
     */
    @UserLoginToken
    @RequestMapping("/findUserInfo")
    public JsonResult findUserInfo (String shssToken) {
    	JsonResult result = new JsonResult();
    	log.info("shssToken:" + shssToken);
        if (StringUtil.isBlank(shssToken)) {
            throw new ShssException(StateCodeConstant.ERROR_TOKEN_INVALID, CommonConst.TOKEN_WRONGFUL);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setShssToken(shssToken);
    	UserInfo user = userService.getUser(userInfo);
    	Map<String, Object> map = new HashMap<>();
    	map.put("username", user.getUsername());
    	map.put("portrait", user.getPortrait());
    	map.put("loginTime", user.getLoginTime());
    	result.setState(true);
    	result.setErrMsg("成功");
    	result.setData(map);
    	result.setStateCode(StateCodeConstant.SUCCESS_CODE);
    	return result;
    }
}

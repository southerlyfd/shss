package com.jianghongbo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianghongbo.common.JsonResult;
import com.jianghongbo.common.annotation.UserLoginToken;
import com.jianghongbo.common.consts.CommonConst;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.common.exception.ShssException;
import com.jianghongbo.common.util.StringUtil;
import com.jianghongbo.entity.RecordLogger;
import com.jianghongbo.entity.UserInfo;
import com.jianghongbo.service.api.LogRecordService;
import com.jianghongbo.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


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

    @Autowired
    LogRecordService logRecordService;

    // 头像访问路径
    private final String path = "/images/portrait/";


    /**
     * 用户列表查询
     * @param userParam
     * @return
     */
//    @UserLoginToken
    @RequestMapping("/queryUserList")
    public JsonResult queryUserList(UserInfo userParam){
        JsonResult result = new JsonResult();
//        PageHelper.startPage(1, 10);
        List<UserInfo> userList = userService.getUserList(userParam);
//        PageInfo<UserInfo> userPageInfo = new PageInfo<>(userList);
//        result.setData(userPageInfo);
        result.setData(userList);
        return result;
    }

    /**
     * 查询用户
     * @param userParam
     * @return
     */
    @RequestMapping("/queryUser")
    public JsonResult queryUser(UserInfo userParam){
        String username = userParam.getUsername();
        if (StringUtils.isBlank(StringUtils.trim(username))) {
            throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, CommonConst.USERNAME_NOT_NULL);
        }
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
        // 产生10内随机数为头像
        int number = new Random().nextInt(10) + 1;
        String portrait = path + number + ".jpg";
        user.setPortrait(portrait);
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
        RecordLogger recordLogger = new RecordLogger();
        recordLogger.setUsername(username);
        recordLogger.setPassword(password);
//        logRecordService.saveRecordLogger(recordLogger);
        result.setErrMsg(CommonConst.LOGIN_SUCCESS);
        return result;
    }

    /**
     * 获取用户信息
     * @param headers
     * @return
     */
    @UserLoginToken
    @RequestMapping("/findUserInfo")
    public JsonResult findUserInfo (@RequestHeader HttpHeaders headers) {
    	JsonResult result = new JsonResult();
    	String shssToken = headers.getFirst(CommonConst.SHSS_TOKEN);
    	log.info("shssToken:" + shssToken);
        UserInfo userInfo = new UserInfo();
        userInfo.setShssToken(shssToken);
    	UserInfo user = userService.getUser(userInfo);
    	Map<String, Object> map = new HashMap<>();
    	map.put("username", user.getUsername());
    	map.put("portrait", user.getPortrait());
    	map.put("loginTime", user.getLoginTime());
    	map.put("userId", user.getId());
    	result.setState(true);
    	result.setErrMsg("成功");
    	result.setData(map);
    	result.setStateCode(StateCodeConstant.SUCCESS_CODE);
    	return result;
    }

    /**
     *  用户退出
     * @param headers
     * @return
     */
    @UserLoginToken
    @RequestMapping("/logoutUser")
    public JsonResult logoutUser(@RequestHeader HttpHeaders headers){
        String shssToken = headers.getFirst(CommonConst.SHSS_TOKEN);
        log.info("shssToken:" + shssToken);
        UserInfo userInfo = new UserInfo();
        userInfo.setShssToken(shssToken);
        UserInfo user = userService.getUser(userInfo);
        UserInfo currentUser = new UserInfo();
        currentUser.setId(user.getId());
        currentUser.setShssToken("");
        JsonResult result = new JsonResult();
        userService.updateUserInfo(currentUser);
        result.setErrMsg("退出成功");
        return result;
    }
    
    @RequestMapping("/getTest")
    public String getTest(){
        return "测试版本005";
    }
}

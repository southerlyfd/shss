package com.jianghongbo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianghongbo.common.JsonResult;
import com.jianghongbo.common.annotation.UserLoginToken;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.common.exception.ShssException;
import com.jianghongbo.entity.User;
import com.jianghongbo.service.api.RedisService;
import com.jianghongbo.service.api.UserService;
import com.jianghongbo.service.impl.TokenService;
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

    @Autowired
    TokenService tokenService;

    @Autowired
    RedisService redisService;


    /**
     * 用户列表查询
     * @param userParam
     * @return
     */
    @UserLoginToken
    @RequestMapping("/queryUserList")
    public JsonResult queryUserList(User userParam){
        JsonResult result = new JsonResult();
        PageHelper.startPage(1, 10);
        List<User> userList = userService.getUserList(userParam);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        result.setData(userPageInfo);
        return result;
    }

    /**
     * 查询用户
     * @param userParam
     * @return
     */
    @RequestMapping("/queryUser")
    public JsonResult queryUser(User userParam){
    	JsonResult result = new JsonResult();
        User user = userService.getUser(userParam);
        result.setData(user);
        return result;
    }

    /**
     *  用户注册
     * @param user
     * @return
     */
    @RequestMapping("/registerUser")
    public JsonResult registerUserInfo(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(StringUtils.trim(username))) {
            throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, "username 不能为空");
        }
        if (StringUtils.isBlank(StringUtils.trim(password))) {
            throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, "password 不能为空");
        }
        JsonResult result = new JsonResult();
        userService.registerUserInfo(user);
        return result;
    }

    /**
     *  用户登陆
     * @param userParam
     * @return
     */
    @RequestMapping("/login")
    //@Transactional  //事务回滚
    public JsonResult login(User userParam){
        JsonResult result = new JsonResult();
        User user = userService.getUser(userParam);
        if (user != null){
            if (!userParam.getPassword().equals(user.getPassword())) {
                result.setStateCode(StateCodeConstant.ERROR_PARAM_CODE);
                result.setErrMsg("登录失败,密码错误");
            } else {
                int id = user.getId();
                User login_user = new User();
                login_user.setId(id);
                login_user.setLogin_time("now");
                String token = tokenService.getToken(user);
                login_user.setShss_token(token);
                // 修改token
                userService.updateUserInfo(login_user);
                redisService.set("shss_token", token);
                Map<String, String> map = new HashMap<>();
                map.put("shssToken", token);
                result.setData(map);
            }
        } else {
            result.setStateCode(StateCodeConstant.ERROR_PARAM_CODE);
            result.setErrMsg("登录失败,用户名不存在");
        }

        return result;
    }


}

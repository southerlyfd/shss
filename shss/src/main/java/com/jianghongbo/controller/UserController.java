package com.jianghongbo.controller;

import com.jianghongbo.common.JsonResult;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.common.exception.ShssException;
import com.jianghongbo.entity.User;
import com.jianghongbo.service.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author ：taoyl
 * @date ：Created in 2019-03-16 21:36
 * @description：
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户
     * @param user
     * @return
     */
    @RequestMapping("/queryUser")
    public JsonResult queryUser(User user){
    	JsonResult result = new JsonResult();
        List<User> userList = userService.getUserList(user);
        result.setData(userList);
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
     *  用户注册
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public JsonResult login(User user){
        JsonResult result = new JsonResult();
        List<User> userList = userService.getUserList(user);
        if (userList != null && userList.size() > 0){
            int id = userList.get(0).getId();
            User login_user = new User();
            login_user.setId(id);
            login_user.setLogin_time("now");
            userService.updateUserInfo(login_user);
            result.setData(userList);
        } else {
            result.setStateCode(StateCodeConstant.ERROR_PARAM_CODE);
            result.setErrMsg("用户名或密码不正确");
        }
        return result;
    }
}

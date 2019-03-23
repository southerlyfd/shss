package com.jianghongbo.controller;

import com.jianghongbo.common.JsonResult;
import com.jianghongbo.entity.User;
import com.jianghongbo.service.api.UserService;
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
@RequestMapping("/test")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/firstView")
    public JsonResult firstView(String id){
    	JsonResult result = new JsonResult();
        User user = new User();
        user.setId(id);
        List<User> userList = userService.getUserList(user);
        result.setData(userList);
        return result;
    }

}

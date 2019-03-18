package com.jianghongbo.service.api;

import com.jianghongbo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-16 21:42
 * @description：
 */

public interface UserService {

    List<User> getUserList(User user);

}

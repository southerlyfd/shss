package com.jianghongbo.dao;

import com.jianghongbo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao {

    List<User> getUserList(User user);

}

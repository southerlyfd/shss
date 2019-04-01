package com.jianghongbo.service.impl;

import com.jianghongbo.common.util.StringUtil;
import com.jianghongbo.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-31 17:22
 * @description：
 */
@Service
public class TokenService {
    public String getToken(User user) {
        String token = UUID.randomUUID() + StringUtil.getCurrentDateTime() + "";
        return token;
    }
}

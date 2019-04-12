package com.jianghongbo.common.interceptor;

import com.jianghongbo.common.ServiceResult;
import com.jianghongbo.common.annotation.PassToken;
import com.jianghongbo.common.annotation.UserLoginToken;
import com.jianghongbo.common.consts.CommonConst;
import com.jianghongbo.common.exception.ShssException;
import com.jianghongbo.entity.UserInfo;
import com.jianghongbo.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
/**
 * @author ：taoyl
 * @date ：Created in 2019-03-31 18:03
 * @description：
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String shssToken = httpServletRequest.getHeader(CommonConst.SHSS_TOKEN);// 从 http 请求头中取出 token
        log.info("shssToken:" + shssToken);
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (shssToken == null) {
                    throw new ShssException(CommonConst.USER_INVALID);
                }
                ServiceResult<UserInfo> userList = userService.getLoginInfo(shssToken);
                // 判断登陆有效时间是否已过期
                if (userList.isOk() && userList.getData() != null) {
                    UserInfo user = userList.getData();
                    // 验证 token
                    if (user == null) {
                        throw new ShssException(CommonConst.USER_INVALID);
                    }
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}

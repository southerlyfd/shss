package com.jianghongbo.common.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jianghongbo.common.interceptor.AuthenticationInterceptor;
import com.jianghongbo.common.interceptor.MyLocaleResolver;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-31 17:26
 * @description：
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @UserLoginToken 注解 决定是否需要登录
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    /**
     * 配置自己的国际化语言解析器
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    @Bean
    public HttpMessageConverters useConverters() {

        return new HttpMessageConverters(new FastJsonHttpMessageConverter());
    }
}

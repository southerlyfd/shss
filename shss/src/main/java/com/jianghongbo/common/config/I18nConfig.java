package com.jianghongbo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author ：taoyl
 * @date ：Created in 2019-04-12 19:14
 * @description：国际化配置
 */
@Configuration
public class I18nConfig extends WebMvcConfigurationSupport {
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}

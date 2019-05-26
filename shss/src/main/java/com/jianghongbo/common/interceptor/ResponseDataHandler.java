package com.jianghongbo.common.interceptor;

import com.jianghongbo.common.JsonResult;
import com.jianghongbo.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Locale;

/**
 * @author ：taoyl
 * @date ：Created in 2019-04-21 15:10
 * @description：返回数据拦截
 */
@Slf4j
@ControllerAdvice
public class ResponseDataHandler implements ResponseBodyAdvice {

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof JsonResult) {
            String msg = ((JsonResult) body).getErrMsg();
            Locale locale = LocaleContextHolder.getLocale();
            try {
                if (!"".equals(StringUtil.trimNull(msg))) {
                    msg =  messageSource.getMessage(msg, null, locale);
                    ((JsonResult) body).setErrMsg(msg);
                }
            } catch (Exception e) {
                log.debug("国际化内容：" + msg);
            }
        }
        return body;
    }
}

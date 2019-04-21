package com.jianghongbo.common.exception;

import com.jianghongbo.common.JsonResult;
import com.jianghongbo.common.consts.StateCodeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Locale;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-24 17:40
 * @description：全局异常处理类
 */
@ControllerAdvice
public class ShssControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult errorHandler(Exception ex) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStateCode(StateCodeConstant.ERROR_CODE);
        jsonResult.setErrMsg(ex.getMessage());
        jsonResult.setState(false);
        return jsonResult;
    }

    /**
     * 拦截捕捉自定义异常 ShssException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ShssException.class)
    public JsonResult myErrorHandler(ShssException ex) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStateCode(ex.getCode());
        String msg = ex.getMsg();
        Locale locale = LocaleContextHolder.getLocale();
        msg =  messageSource.getMessage(msg, null, locale);
        jsonResult.setErrMsg(msg);
        jsonResult.setState(false);
        return jsonResult;
    }

}

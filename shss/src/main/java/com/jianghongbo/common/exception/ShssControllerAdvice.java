package com.jianghongbo.common.exception;

import com.jianghongbo.common.consts.StateCodeConstant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-24 17:40
 * @description：全局异常处理类
 */
@ControllerAdvice
public class ShssControllerAdvice {

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put("code", StateCodeConstant.ERROR_CODE);
        map.put("msg", ex.getMessage());
        return map;
    }

    /**
     * 拦截捕捉自定义异常 ShssException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ShssException.class)
    public Map myErrorHandler(ShssException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        return map;
    }

}

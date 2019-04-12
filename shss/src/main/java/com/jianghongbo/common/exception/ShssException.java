package com.jianghongbo.common.exception;

import com.jianghongbo.common.consts.CommonConst;
import com.jianghongbo.common.consts.StateCodeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-24 17:39
 * @description：自定义异常
 */
public class ShssException extends RuntimeException {

    public ShssException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ShssException(String msg) {
        this.msg = msg;
    }

    private String code = StateCodeConstant.ERROR_CODE;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

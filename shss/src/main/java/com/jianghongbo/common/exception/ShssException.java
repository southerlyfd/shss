package com.jianghongbo.common.exception;

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

    private String code;
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

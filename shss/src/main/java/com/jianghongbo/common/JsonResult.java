package com.jianghongbo.common;

import java.io.Serializable;

import com.jianghongbo.common.consts.StateCodeConstant;

/**
 * @ClassName: JsonResult.java
 * @Description: 类说明
 * @author: jianghb
 * @date: 2019年3月23日
 */
public class JsonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1598740556436002864L;
	/**
	 * 操作结果（成功或失败）
	 */
	boolean state = true;
	private boolean exist = false;
	/**
	 * 操作状态码
	 */
	String stateCode = StateCodeConstant.SUCCESS_CODE;
	/**
	 * 操作提示信息
	 */
	String errMsg;

	/**
	 * 数据
	 */
	Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}
}

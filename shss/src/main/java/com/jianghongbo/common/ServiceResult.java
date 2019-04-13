package com.jianghongbo.common;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName: ServiceResult.java
 * @Description: Service层返回值封装
 * @author: jianghb
 * @date: 2019年4月12日
 */
public class ServiceResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1040692116759624144L;

	private boolean isOk = false;

	private boolean exist = false;
	
	private boolean isEnd = false;
	
	/**
	 *  msg 状态0表示异常、1表示正确
	 */
	private int msgType = 0;

	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 消息code
	 */
	private String msgCode;

	/**
	 * 数据
	 */
	private T data;

	/**
	 * 消息内容
	 */
	private String errMsg;

	/**
	 * 数据Map
	 */
	private Map<String, Object> dataMap;

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public int getMsgType(){
		return this.msgType;
	}

	public String getMsgCode() {
		return msgCode;
	}

	/**
	 * 根据msg第二位字母取得msg类型
	 * @param msgCode
	 */
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
		try {
			char state = msgCode.charAt(1);
			switch (state) {
			case 'I':
				this.msgType = 1;
				break;
			case 'Q':
				this.msgType = 1;
				break;
			case 'E':
				this.msgType = 0;
				break;

			default:
				this.msgType = 0;
				break;
			}

		} catch (Exception e) {
			this.msgType = 0;
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
}

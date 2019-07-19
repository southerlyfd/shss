package com.jianghongbo.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.jianghongbo.common.MyStringTrimmerEditor;
import com.jianghongbo.common.util.StringArgumentUtil;

/**
 * @ClassName: Controller.java
 * @Description: 类说明
 * @author: jianghb
 * @date: 2019年4月12日
 */
public class Controller {

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(StringArgumentUtil.DATE_TIME_FORMAT);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(String.class, new MyStringTrimmerEditor(true));
	}

	/**
	 * 校验绑定对象
	 */
	protected BindException validateRequestBean(Validator validator, Object entity)
			throws Exception {
		Class<? extends Object> target = entity.getClass();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(entity,StringArgumentUtil.firstCharToLow(target.getSimpleName()));
		BindException errors = new BindException(binder.getBindingResult());
		validator.validate(entity, errors);
		return errors;
	}

	/**
	 * 初始化输出
	 * @param fileName
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected PrintWriter initPrintWriter(String fileName,HttpServletResponse response) throws Exception{
		   response.setContentType("application/octet-stream;charset=GBK");
		   response.setHeader("Content-Disposition","attachment;  filename="+fileName);
		   PrintWriter out = response.getWriter();//放在第一句是会出现乱码
		   return out;
	}

	/**
	 * 输出数据
	 * @param writer
	 * @param data
	 * @param isEnd
	 * @throws Exception
	 */
	protected void writeData(PrintWriter writer ,String data , boolean isEnd) throws Exception{
		writer.write(data);
		writer.flush();
		if(isEnd){
			writer.close();
		}
	}

	/**
	 * 根据request取得IP 
	 * @param request
	 * @return
	 */
	protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

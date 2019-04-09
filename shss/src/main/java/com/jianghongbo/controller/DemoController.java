package com.jianghongbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jianghongbo.common.JsonResult;
import com.jianghongbo.service.api.DemoServiceApi;

/**
 * @ClassName: DemoController.java
 * @Description: 类说明
 * @author: jianghb
 * @date: 2019年2月19日
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private DemoServiceApi DemoServiceApi;
	
	@RequestMapping(value = "/test")
	public JsonResult test(String token) {
		JsonResult result = new JsonResult();
		Integer i = DemoServiceApi.testServiceApi(2);
		result.setData("SUCCESS" + (token == null ? "" : token + i));
		return result;
	}
	
	@RequestMapping(value = "/test1")
	public JsonResult test() {
		JsonResult result = new JsonResult();
		result.setData("SUCCESS" + "0.0.2");
		return result;
	}
}

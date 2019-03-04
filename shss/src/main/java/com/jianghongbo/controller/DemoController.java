package com.jianghongbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public String test(String token) {
		
		return "SUCCESS" + (token == null ? "" : token);
	}
}

package com.jianghongbo.service.impl;

import com.jianghongbo.service.api.DemoServiceApi;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DemoServiceImpl.java
 * @Description: 类说明
 * @author: jianghb
 * @date: 2019年2月19日
 */
@Service
public class DemoServiceImpl implements DemoServiceApi {

	@Override
	public Integer testServiceApi(Integer flag) {
		Integer a = 1;
		Integer b = 2;
		
		return flag == null ? a + b : a+b+flag;
	}

}

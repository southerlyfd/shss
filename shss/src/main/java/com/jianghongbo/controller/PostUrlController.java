package com.jianghongbo.controller;

import com.jianghongbo.common.consts.CommonConst;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.common.exception.ShssException;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @ClassName: DemoController.java
 * @Description: 类说明
 * @author: jianghb
 * @date: 2019年2月19日
 */
@RestController
@RequestMapping("/sendUrl")
public class PostUrlController {

	private final String GET = "GET";

	@RequestMapping("/doPostForResult")
	public JSONObject doPostForResult(String method, String url, @RequestParam Map<String, String> requestParam) {
		if (StringUtils.isBlank(StringUtils.trim(method))) {
			throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, CommonConst.HTTPMETHOD_NOT_NULL);
		}
		if (StringUtils.isBlank(StringUtils.trim(url))) {
			throw new ShssException(StateCodeConstant.ERROR_NODATA_CODE, CommonConst.URL_NOT_NULL);
		}
		String sr = "";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		if (GET.equals(method.toUpperCase())){
			responseEntity = restTemplate.getForEntity(url, String.class);
		} else {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			if (requestParam != null && requestParam.size() > 0) {
				for (String key : requestParam.keySet()) {
					params.add(key, requestParam.get(key));
				}
			}
			responseEntity = restTemplate.postForEntity(url, params, String.class);
		}
		JSONObject jsonData =  null;
		if(responseEntity != null){
			sr = responseEntity.getBody();
			jsonData = JSONObject.fromObject(sr);
		}
//		System.out.println("返回参数：" + sr);
		return jsonData;
	}
}

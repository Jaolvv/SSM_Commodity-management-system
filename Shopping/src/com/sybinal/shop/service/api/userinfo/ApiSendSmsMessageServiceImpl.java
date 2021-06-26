package com.sybinal.shop.service.api.userinfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;
import com.sybinal.shop.model.User;
@Service
public class ApiSendSmsMessageServiceImpl implements ApiSendSmsMessageService {

	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;
	
	@Override
	public ApiResponseObject getSmsMessage(User user)
			throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.SEND_SMS, user);
	}

}

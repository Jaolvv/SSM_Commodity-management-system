package com.sybinal.shop.service.api.userinfo;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;
import com.sybinal.shop.model.User;
@Service
public class ApiUserInfoServiceImpl implements ApiUserInfoService {
	
	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;
	
	@Override
	public ApiResponseObject registerUserInfo(User user)
			throws ApiServiceException {
		int defaultValue = (int)httpClientTool.doPostJson(HttpClientTool.API_URL_EWALLET_DEFAULT,null).getData();
		System.out.println("获取的用户注册默认账户值为"+defaultValue);
		user.setAmount(defaultValue);
		return httpClientTool.doPostJson(HttpClientTool.REGISTER, user);
	}

	@Override
	public ApiResponseObject getUserInfo(User user) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CUSTOMER, user);
	}

	@Override
	public ApiResponseObject getUserBasic(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CUSTOMER_BASIC, reqMap);
	}
	
	@Override
	public ApiResponseObject getUserCartCount(User user) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CART_COUNT, user);
	}

	@Override
	public ApiResponseObject editUserBasic(User user) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_UPDATE_CUSTOMER, user);
	}

	@Override
	public ApiResponseObject editUserPwd(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CHANGE_PASSWORD, reqMap);
	}

	@Override
	public ApiResponseObject recoveryUserPwd(User user) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_USER_RECOVERY, user);
	}

}

package com.sybinal.shop.service.api.userinfo;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.model.User;

public interface ApiSendSmsMessageService {
	/**
	 * 获取用户心愿单
	 * @param conditionMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getSmsMessage(User user) throws ApiServiceException;
}

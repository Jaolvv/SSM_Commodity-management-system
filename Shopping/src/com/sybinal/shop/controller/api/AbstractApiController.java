package com.sybinal.shop.controller.api;

import com.sybinal.shop.common.ApiResponseObject;

public abstract class AbstractApiController {

	public ApiResponseObject reponseJSON(String errorCode, String errorMsg, Object resData) {
		ApiResponseObject apiResponseObject = new ApiResponseObject();
		apiResponseObject.setErrorCode(errorCode);
		apiResponseObject.setErrorMsg(errorMsg);
		apiResponseObject.setData(resData);
		return apiResponseObject;
	}
}

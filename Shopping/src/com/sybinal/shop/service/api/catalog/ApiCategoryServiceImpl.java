package com.sybinal.shop.service.api.catalog;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;

@Service
public class ApiCategoryServiceImpl implements ApiCategoryService {

	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;
	
	@Override
	public ApiResponseObject getCategoryById(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATEGORY, reqMap);
	}

	@Override
	public ApiResponseObject getAllCategory(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATEGORY_ALL, reqMap);
	}

	@Override
	public ApiResponseObject getCategoryByCondition(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATEGORY_CONDITION, reqMap);
	}
	
}

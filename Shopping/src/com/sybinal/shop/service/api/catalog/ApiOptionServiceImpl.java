package com.sybinal.shop.service.api.catalog;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;

@Service
public class ApiOptionServiceImpl implements ApiOptionService {


	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;
	
	@Override
	public ApiResponseObject getOptionDetailsByProductId(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_PRODUCT_OPTION, reqMap);
	}
	
	@Override
	public ApiResponseObject getSkuByProductId(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_PRODUCT_SKU, reqMap);
	}
	
}

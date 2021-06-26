package com.sybinal.shop.service.api.product;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;
import com.sybinal.shop.model.Comments;

@Service
public class ApiProductServiceImpl implements ApiProductService {

	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;

	@Override
	public ApiResponseObject getProductsByCategory(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_PRODUCT, reqMap);
	}

	@Override
	public ApiResponseObject getProductDetailsById(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_PRODUCT_DETAILS, reqMap);
	}

	@Override
	public ApiResponseObject getProductHot(Map<String, Object> reqMap) throws ApiServiceException {
		reqMap.put("pageSize", 3);
		reqMap.put("hot", 1);
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_PRODUCTS_HOT, reqMap);
	}

	@Override
	public ApiResponseObject getSearchProducts(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_SEARCH_PRODUCTS, reqMap);
	}

	@Override
	public ApiResponseObject getIndexProducts(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_INDEX_PRODUCTS, reqMap);
	}

	@Override
	public ApiResponseObject getReviews(Map<String, Object> reqMap) throws ApiServiceException {
		reqMap.put("order", "DESC");
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_SHOW_REVIEW, reqMap);
	}

	@Override
	public ApiResponseObject addReviews(Comments comments) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CATALOG_ADD_REVIEW, comments);
	}
}

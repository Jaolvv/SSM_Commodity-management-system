package com.sybinal.shop.service.api.userinfo;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;
import com.sybinal.shop.model.Wishlist;
@Service
public class ApiWishlistServiceImpl implements ApiWishlistService {

	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;
	
	@Override
	public ApiResponseObject getUserWishlist(Map<String, Object> reqMap)throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CUSTOMER_WISHLIST, reqMap);
	}
	
	@Override
	public ApiResponseObject removeWishlist(Map<String, Object> reqMap) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CUSTOMER_REMOVE_WISHLIST, reqMap);
	}

	@Override
	public ApiResponseObject addWishlistByUserId(Wishlist wishlist) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CUSTOMER_ADD_WISHLIST, wishlist);
	}

}

package com.sybinal.shop.service.api.cart;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;
import com.sybinal.shop.model.Order;
import com.sybinal.shop.model.User;

@Service
public class ApiCartServiceImpl  implements ApiCartService {
	
	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;

	@Override
	public ApiResponseObject modCart(Order order) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CART_ITEMS_UPDATE, order);
	}

	@Override
	public ApiResponseObject removeCart(Order order) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CART_ITEMS_REMOVEALL, order);
	}

	@Override
	public ApiResponseObject getCart(Order order) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CART, order);
	}

	@Override
	public ApiResponseObject getCartCount(User user) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CART_COUNT, user);
	}

	@Override
	public ApiResponseObject addCart(Order order) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CART_ADD, order);
	}

	@Override
	public ApiResponseObject removeCartById(Order order) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CART_ITEMS_REMOVE, order);
	}

}

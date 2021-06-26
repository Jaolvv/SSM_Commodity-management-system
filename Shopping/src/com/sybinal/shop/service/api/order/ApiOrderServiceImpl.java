package com.sybinal.shop.service.api.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;
import com.sybinal.shop.model.Order;

@Service
public class ApiOrderServiceImpl implements ApiOrderService {

	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;
	
	@Override
	public ApiResponseObject payOrder(Order order) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_ORDER_PAYMENT, order);
	}

	@Override
	public ApiResponseObject getOrder(Order order) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_ORDERS, order);
	}

	@Override
	public ApiResponseObject getOrderItem(Order order) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_ORDER_DETAIL, order);
	}

	@Override
	public ApiResponseObject modOrderItem(Order order) throws ApiServiceException { 
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CART_ITEMS_UPDATE, order);
	}

}

package com.sybinal.shop.service.api.order;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.model.Order;

public interface ApiOrderService {
	/**
	 * 支付订单 
	 * @param order 订单对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject payOrder(Order order) throws ApiServiceException;
	/**
	 * 获取顶单
	 * @param order 订单对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getOrder(Order order) throws ApiServiceException;
	/**
	 * 获取详细信息
	 * @param order 订单对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getOrderItem(Order order) throws ApiServiceException;
	/**
	 * 修改订单
	 * @param order 订单对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject modOrderItem(Order order) throws ApiServiceException;

}

package com.sybinal.shop.service.api.cart;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.model.Order;
import com.sybinal.shop.model.User;

public interface ApiCartService {
	/**
	 * 修改购物车
	 * @param order 订单对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject modCart(Order order) throws ApiServiceException;
	/**
	 * 删除全部购物车
	 * @param order 订单对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject removeCart(Order order) throws ApiServiceException;
	/**
	 * 根据ID删除购物车
	 * @param order 订单对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject removeCartById(Order order) throws ApiServiceException;
	/**
	 * 获取购物车
	 * @param order 订单对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getCart(Order order) throws ApiServiceException;
	/**
	 * 获取购物车数量
	 * @param user 用户对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getCartCount(User user) throws ApiServiceException;
	/**
	 * 添加购物车
	 * @param order
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject addCart(Order order) throws ApiServiceException;

}

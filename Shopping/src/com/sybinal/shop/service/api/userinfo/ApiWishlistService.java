package com.sybinal.shop.service.api.userinfo;

import java.util.Map;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.model.Wishlist;

public interface ApiWishlistService {
	/**
	 * 获取用户心愿单
	 * @param conditionMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getUserWishlist(Map<String, Object> conditionMap) throws ApiServiceException;

	/**
	 * 添加用户心愿单
	 * @param conditionMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject addWishlistByUserId(Wishlist wishlist) throws ApiServiceException;

	/**
	 * 删除用户心愿单
	 * @param conditionMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject removeWishlist(Map<String, Object> reqMap) throws ApiServiceException ;
}

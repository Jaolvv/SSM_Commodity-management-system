package com.sybinal.shop.service.api.userinfo;

import java.util.Map;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.model.User;

public interface ApiUserInfoService {
	/**
	 * 注册用户
	 * @param conditionMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject registerUserInfo(User user) throws ApiServiceException;
	
	/**
	 * 查询用户
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getUserInfo(User user) throws ApiServiceException;
	
	/**
	 * 查询购物车数量
	 * */
	public ApiResponseObject getUserCartCount(User user) throws ApiServiceException;

	/**
	 * 查询用户基本
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getUserBasic(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 修改用户基本
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject editUserBasic(User user) throws ApiServiceException;

	/**
	 * 修改用户密码
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject editUserPwd(Map<String, Object> reqMap) throws ApiServiceException;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject recoveryUserPwd(User user) throws ApiServiceException;
}

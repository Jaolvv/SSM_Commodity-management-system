package com.sybinal.shop.service.api.catalog;

import java.util.Map;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;

public interface ApiCategoryService {

	/**
	 * 按照分类ID获取分类信息
	 * 
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getCategoryById(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 获取全部商品分类数据
	 * 
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getAllCategory(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 根据条件获取全部商品分类数据
	 * 
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getCategoryByCondition(Map<String, Object> reqMap) throws ApiServiceException;
}

package com.sybinal.shop.service.api.product;

import java.util.Map;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.model.Comments;

public interface ApiProductService {

	/**
	 * 按商品分类获取商品列表
	 * 
	 * @param conditionMap
	 * @return
	 */
	public ApiResponseObject getProductsByCategory(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 查询商品详细根据id
	 * 
	 * @param conditionMap
	 * @return
	 */
	public ApiResponseObject getProductDetailsById(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 查询热销商品
	 * 
	 * @param conditionMap
	 * @return
	 */
	public ApiResponseObject getProductHot(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 条件查询商品
	 * 
	 * @param conditionMap
	 * @return
	 */
	public ApiResponseObject getSearchProducts(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 首页分类层级关系商品
	 * 
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getIndexProducts(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 获取商品评论
	 * 
	 * @param conditionMap
	 * @return
	 */
	public ApiResponseObject getReviews(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 添加商品评论
	 * 
	 * @param conditionMap
	 * @return
	 */
	public ApiResponseObject addReviews(Comments comments) throws ApiServiceException;
}

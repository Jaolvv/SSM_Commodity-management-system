package com.sybinal.shop.service.api.catalog;

import java.util.Map;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;

public interface ApiOptionService {

	/**
	 * 按照商品ID获取Option详情
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	ApiResponseObject getOptionDetailsByProductId(Map<String, Object> reqMap) throws ApiServiceException;

	/**
	 * 按照商品ID获取sku
	 * @param reqMap
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getSkuByProductId(Map<String, Object> reqMap) throws ApiServiceException;
}

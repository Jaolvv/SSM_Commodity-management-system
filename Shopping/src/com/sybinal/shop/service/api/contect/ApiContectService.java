package com.sybinal.shop.service.api.contect;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.model.Contect;

public interface ApiContectService {
	/**
	 * 获取联系人
	 * @param contect 联系人对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject getContect(Contect contect) throws ApiServiceException;
	/**
	 * 添加联系人
	 * @param contect 联系人对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject addContect(Contect contect) throws ApiServiceException;
	/**
	 * 修改联系人
	 * @param contect 联系人对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject modContect(Contect contect) throws ApiServiceException;
	/**
	 * 删除联系人
	 * @param contect 联系人对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject removeContect(Contect contect) throws ApiServiceException;
	/**
	 * 修改联系人
	 * @param contect 联系人对象
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject updateContect(Contect contect) throws ApiServiceException;

}

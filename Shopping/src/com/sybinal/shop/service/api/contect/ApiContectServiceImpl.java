package com.sybinal.shop.service.api.contect;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.common.ApiServiceException;
import com.sybinal.shop.common.HttpClientTool;
import com.sybinal.shop.model.Contect;

@Service
public class ApiContectServiceImpl implements ApiContectService {

	@Resource(name = "httpClientTool")
	private HttpClientTool httpClientTool;
	
	@Override
	public ApiResponseObject getContect(Contect contect) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CONTECT, contect);
	}

	@Override
	public ApiResponseObject addContect(Contect contect) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CONTECT_ADD, contect);
	}

	@Override
	public ApiResponseObject modContect(Contect contect) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CONTECT_UPDATE, contect);
	}

	@Override
	public ApiResponseObject removeContect(Contect contect) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CONTECT_REMOVE, contect);
	}
	

	@Override
	public ApiResponseObject updateContect(Contect contect) throws ApiServiceException {
		return httpClientTool.doPostJson(HttpClientTool.API_URL_CONTECT_DEFAULT, contect);
	}

}

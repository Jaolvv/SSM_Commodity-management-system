package com.sybinal.shop.common;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component(value = "httpClientTool")
public class HttpClientToolImpl implements HttpClientTool {

	private static final Log log = LogFactory.getLog(HttpClientToolImpl.class);

	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;

	@Value("${api.url}")
	private String apiURL;

	@Override
	public ApiResponseObject doPostJson(String url, Object obj)
			throws ApiServiceException {

		log.info("api请求url :" + apiURL + url);
		HttpPost httpPost = new HttpPost(apiURL + url);
		httpPost.setConfig(this.requestConfig);
		ObjectMapper objMapper = new ObjectMapper();
		CloseableHttpResponse response = null;
		try {
			String json = objMapper.writeValueAsString(obj);
			log.info("请求报文JSON:" + json);
			if (json != null) {
				// 构造一个form表单式的实体
				StringEntity stringEntity = new StringEntity(json,
						ContentType.APPLICATION_JSON);
				// 将请求实体设置到httpPost对象中
				httpPost.setEntity(stringEntity);
			}
			// 执行请求
			response = this.httpClient.execute(httpPost);
			String responseStr = EntityUtils.toString(response.getEntity(),
					"UTF-8");

			log.info("响应报文JSON" + responseStr);

			return objMapper.readValue(responseStr, ApiResponseObject.class);
		} catch (Exception e) {
			throw new ApiServiceException(e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

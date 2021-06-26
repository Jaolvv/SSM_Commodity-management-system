package com.sybinal.shop.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sybinal.shop.common.ApiResponseEnum;
import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.service.option.OptionService;

@RestController
@RequestMapping("api/v1")
public class OptionApiController extends AbstractApiController {

	@Autowired
	OptionService optionService;

	@RequestMapping(value = "catalog/product/option/details", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getOptionDetails(@RequestBody Map<String, Object> reqMap) {
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), optionService.getOptionRelation(reqMap));
	}

}

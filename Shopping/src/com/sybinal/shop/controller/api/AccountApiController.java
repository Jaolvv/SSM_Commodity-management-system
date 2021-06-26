package com.sybinal.shop.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sybinal.shop.common.ApiResponseEnum;
import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.model.Account;
import com.sybinal.shop.model.User;
import com.sybinal.shop.service.account.AccountService;
import com.sybinal.shop.service.user.UserService;

@RestController
@RequestMapping("api/v1")
public class AccountApiController extends AbstractApiController {

	@Autowired
	private AccountService accountService;
	
	@Autowired 
	private UserService userService;

	@RequestMapping(value = "/account/userAccount", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getUserBasic(
			@RequestBody Map<String, Object> reqMap) {
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(),
				ApiResponseEnum.SUCCESS.getName(),
				accountService.getAccountByUser(reqMap));

	}

	@RequestMapping(value = "/account/recharge", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject rechargeAccount(@RequestBody Account account) {
		User u = userService.getUserById(account.getUserId());
		account.setUsername(u.getUserName());
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(),
				ApiResponseEnum.SUCCESS.getName(),
				accountService.rechargeAccount(account));
	}

	@RequestMapping(value = "/account/payOrder", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject payOrderByEwallet(@RequestBody Account account) {
		int result = accountService.payOrderByEwallet(account);
		if (result != -1) {
			return reponseJSON(ApiResponseEnum.SUCCESS.getCode(),
					ApiResponseEnum.SUCCESS.getName(), result);
		} else {
			return reponseJSON(ApiResponseEnum.FAIL.getCode(),
					ApiResponseEnum.FAIL.getName(), result);
		}
	}
}

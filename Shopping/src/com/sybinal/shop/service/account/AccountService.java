package com.sybinal.shop.service.account;

import java.util.Map;

import com.sybinal.shop.model.Account;

public interface AccountService {

	public Account getAccountByUser(Map<String, Object> reqMap);
	
	public int rechargeAccount(Account account);
	
	public int payOrderByEwallet(Account account);
}

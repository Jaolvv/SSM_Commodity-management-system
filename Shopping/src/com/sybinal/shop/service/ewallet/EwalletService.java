package com.sybinal.shop.service.ewallet;

import com.sybinal.shop.model.EWallet;

public interface EwalletService {

	/*
	 * 设置新注册用户电子钱包金额默认值*/
	public int saveEwallet(EWallet eWallet);
	
	/*
	 * 获取管理员设置的新用户注册电子钱包默认值*/
	public int getDefalutValueOfAccount();
}

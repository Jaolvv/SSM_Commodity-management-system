package com.sybinal.shop.service.ewallet;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.sybinal.shop.mapper.EwalletMapper;
import com.sybinal.shop.model.EWallet;

@Service
public class EwalletServiceImpl implements EwalletService{

	@Autowired
	private EwalletMapper ewalletMapper;  
	
	@Override
	public int saveEwallet(EWallet eWallet) {
		// TODO Auto-generated method stub
		int result = ewalletMapper.saveEwallet(eWallet);
		return result;
	}

	@Override
	public int getDefalutValueOfAccount() {
		// TODO Auto-generated method stub
		int defaultValue = ewalletMapper.getDefalutValueOfAccount();
		return defaultValue;
	}

}

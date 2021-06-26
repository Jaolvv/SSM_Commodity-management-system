package com.sybinal.shop.mapper;

import com.sybinal.shop.model.Account;

public interface AccountMapper {

    /*
     * 为新注册用户的电子钱包冲入管理设置的默认金额*/
    public int saveAccount(Account account);

    /*
     * 根据用户id获取当前该用户电子钱包账户信息*/
    public Account getAccountByUser(int userId);

    /*
     * 用户充值*/
    public int rechargeAccount(Account account);

    /*
     * 利用电子钱包支付订单
     */
    public int payOrderByEwallet(Account account);
}

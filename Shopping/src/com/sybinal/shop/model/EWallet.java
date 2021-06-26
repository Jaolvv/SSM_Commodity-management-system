package com.sybinal.shop.model;

public class EWallet {

	/*
	 * id
	 * */
	private int id;
	/*
	 * 电子钱包金额默认值
	 * */
	private int amount;
	/*
	 * 设置/更新时间
	 * */
	private String updtime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getUpdtime() {
		return updtime;
	}
	public void setUpdtime(String updtime) {
		this.updtime = updtime;
	}
	
	
	
}

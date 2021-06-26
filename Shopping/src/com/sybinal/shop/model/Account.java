package com.sybinal.shop.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/*
 * 用户账户表*
 */
public class Account {
	
	/*
	 * id
	 */
	private int id;
	
	/*
	 * 账户所属用户id
	 */
	private int userId;
	
	/*
	 * 账户所属用户
	 */
	private String username;
	
	/*
	 * 账户余额
	 */
	private double amount;
	
	/*
	 * 余额来源
	 */
	private String source;
	
	/*
	 * 账户更新时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	private Date updTime;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	
	
}

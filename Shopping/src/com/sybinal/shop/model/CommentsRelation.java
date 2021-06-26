package com.sybinal.shop.model;

public class CommentsRelation extends Comments {

	/* 一对一关系的用户 */
	private User user;

	/**
	 * 一对一关系的用户
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 一对一关系的用户
	 * 
	 * @return
	 */
	public void setUser(User user) {
		this.user = user;
	}

}

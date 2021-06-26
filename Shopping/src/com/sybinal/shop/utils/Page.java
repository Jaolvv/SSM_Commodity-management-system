package com.sybinal.shop.utils;

public class Page {
	// 当前页
	private int nowPage = 1;

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public static long confirmPage(long count, int pageNumber) {
		if (count % pageNumber == 0) {
			return count / pageNumber;
		} else {
			return count / pageNumber + 1;
		}
	}
}

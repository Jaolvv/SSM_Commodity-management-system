package com.sybinal.shop.model;

public class WishlistRelation extends Wishlist {

	/* 一对一关系的的商品 */
	private Product product;

	/**
	 * 一对一关系的的商品
	 * 
	 * @return
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * 一对一关系的的商品
	 * 
	 * @return
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

}

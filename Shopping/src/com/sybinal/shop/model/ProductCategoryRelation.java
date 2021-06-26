package com.sybinal.shop.model;

import java.util.List;

public class ProductCategoryRelation extends ProductCategory {

	/* 一对多关系的商品 */
	private List<ProductRelation> productRelationList;

	/**
	 * 一对多关系的商品
	 */
	public List<ProductRelation> getProductRelationList() {
		return productRelationList;
	}

	/**
	 * 一对多关系的商品
	 */
	public void setProductRelationList(List<ProductRelation> productRelationList) {
		this.productRelationList = productRelationList;
	}

}

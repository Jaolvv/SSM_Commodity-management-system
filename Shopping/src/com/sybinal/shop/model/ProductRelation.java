package com.sybinal.shop.model;

import java.util.List;

public class ProductRelation extends Product {

	/* 一对多关系的Product Image */
	private List<ProductImage> productImageList;

	/* 一对多关系的Sku */
	private List<Sku> skuList;

	/* 一对多关系的ProductOptionInfo */
	private List<ProductOptionInfo> productOptionInfoList;

	/**
	 * 一对多关系的Sku
	 * 
	 * @return
	 */
	public List<Sku> getSkuList() {
		return skuList;
	}

	/**
	 * 一对多关系的Sku
	 * 
	 * @return
	 */
	public void setSkuList(List<Sku> skuList) {
		this.skuList = skuList;
	}

	/**
	 * 一对多关系的ProductOptionInfo
	 * 
	 * @return
	 */
	public List<ProductOptionInfo> getProductOptionInfoList() {
		return productOptionInfoList;
	}

	/**
	 * 一对多关系的ProductOptionInfo
	 * 
	 * @return
	 */
	public void setProductOptionInfoList(List<ProductOptionInfo> productOptionInfoList) {
		this.productOptionInfoList = productOptionInfoList;
	}

	/**
	 * 一对多关系的Product Image
	 * 
	 * @return
	 */
	public List<ProductImage> getProductImageList() {
		return productImageList;
	}

	/**
	 * 一对多关系的Product Image
	 * 
	 * @return
	 */
	public void setProductImageList(List<ProductImage> productImageList) {
		this.productImageList = productImageList;
	}

}

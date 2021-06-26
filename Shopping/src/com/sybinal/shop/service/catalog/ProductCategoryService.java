package com.sybinal.shop.service.catalog;

import java.util.List;
import java.util.Map;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.ProductCategory;

/**
 * 商品类别
 * */
public interface ProductCategoryService {
	public Map<String,Object> saveProductCategory(Map<String,Object> data);
	
	public Map<String,Object> modProductCategory(Map<String,Object> data);
	
	public Map<String,Object> delProductCategory(Map<String,Object> data);
	
	public List<ProductCategory> getProductCategory();
	
	public Map<String, Object> getProductCategoryAll(PageInformation pageInfo,ProductCategory category);
	
	public int addProductCategory(ProductCategory category);
	
	public int updateProductCategory(ProductCategory category);
	
	public ProductCategory getProductCategoryContent(Integer id);
	
	int getProductCount(String id);
	
	int delteProductCategory(String ids);

	int getProductNameCount(String name);
	
	int getProductCategoryName(ProductCategory category);
}

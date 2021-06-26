package com.sybinal.shop.service.catalog;

import java.util.List;
import java.util.Map;

import com.sybinal.shop.model.ProductCategory;
import com.sybinal.shop.model.ProductCategoryRelation;

public interface CategoryService {

	public List<ProductCategory> getAllCategory();
	
	public List<ProductCategory> getCategoryByCondition(Map<String, Object> reqMap);

	public ProductCategory getCategoryById(Map<String, Object> reqMap);

	public List<ProductCategoryRelation> getCategoryRelation(Map<String, Object> reqMap);
	
}

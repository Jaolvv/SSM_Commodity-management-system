package com.sybinal.shop.service.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sybinal.shop.mapper.ProductCategoryMapper;
import com.sybinal.shop.mapper.ProductMapper;
import com.sybinal.shop.model.Product;
import com.sybinal.shop.model.ProductCategory;
import com.sybinal.shop.model.ProductCategoryExample;
import com.sybinal.shop.model.ProductCategoryRelation;
import com.sybinal.shop.model.ProductExample;
import com.sybinal.shop.utils.PagingTool;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	ProductCategoryMapper productCategoryMapper;

	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	PagingTool pagingTool;

	@Override
	public List<ProductCategory> getAllCategory() {
		return productCategoryMapper.selectByExample(null);
	}
	
	@Override
	public List<ProductCategory> getCategoryByCondition(Map<String, Object> reqMap) {
		ProductCategoryExample example = new ProductCategoryExample();
		example.setOrderByClause("sort_order DESC");
		return productCategoryMapper.selectByExample(example);
	}

	@Override
	public ProductCategory getCategoryById(Map<String, Object> reqMap) {
		ProductCategoryExample productCategoryExample = new ProductCategoryExample();
		ProductCategoryExample.Criteria criteria = productCategoryExample.createCriteria();
		criteria.andIdEqualTo((int) reqMap.get("categoryId"));
		List<ProductCategory> list = productCategoryMapper.selectByExample(productCategoryExample);
		if (list.size() == 1)
			return productCategoryMapper.selectByExample(productCategoryExample).get(0);
		return null;
	}

	@Override
	public List<ProductCategoryRelation> getCategoryRelation(Map<String, Object> reqMap) {
		Object condition = null;
		int pageSize = 3;
		if ((condition = reqMap.get("pageSize")) != null)
			pageSize = (int) condition;
		ProductCategoryExample exampleCategory = new ProductCategoryExample();
		List<ProductCategoryRelation> categoryRelations = new ArrayList<>();
		List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(null);
		for (ProductCategory productCategory : productCategoryList) {
			ProductExample exampleProduct = new ProductExample();
			ProductExample.Criteria criteriaProduct = exampleProduct.createCriteria();
			criteriaProduct.andCategoryIdEqualTo(productCategory.getId());
			List<Product> listProduct = pagingTool.PagingData("com.sybinal.shop.mapper.ProductMapper.selectByExample", exampleProduct, 1, pageSize);
			if(listProduct.size() == 0)
				continue;
			exampleCategory.setOrderByClause("`pc`.`sort_order` ASC, `p`.`hot` DESC, `p`.`id` DESC");
			ProductCategoryExample.Criteria criteriaCategory = exampleCategory.createCriteria();
			criteriaCategory.andProductStatusEqualTo(0);
			List<Integer> productIds = new ArrayList<>();
			for (Product product : listProduct)
				productIds.add(product.getId());
			criteriaCategory.andProductIdIn(productIds);
			List<ProductCategoryRelation> productCategoryRelation = productCategoryMapper.selectCategoryRelation(exampleCategory);
			categoryRelations.addAll(productCategoryRelation);
			exampleCategory.clear();
		}
		return categoryRelations;
	}
	
}

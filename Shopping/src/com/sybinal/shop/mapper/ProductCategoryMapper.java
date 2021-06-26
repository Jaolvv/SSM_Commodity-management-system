package com.sybinal.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sybinal.shop.model.ProductCategory;
import com.sybinal.shop.model.ProductCategoryExample;
import com.sybinal.shop.model.ProductCategoryRelation;

public interface ProductCategoryMapper {


	long countByExample(ProductCategoryExample example);


	int deleteByExample(ProductCategoryExample example);


	int deleteByPrimaryKey(Integer id);


	int insert(ProductCategory record);


	int insertSelective(ProductCategory record);


	List<ProductCategory> selectByExampleWithBLOBs(ProductCategoryExample example);


	List<ProductCategory> selectByExample(ProductCategoryExample example);


	ProductCategory selectByPrimaryKey(Integer id);


	int updateByExampleSelective(@Param("record") ProductCategory record,
			@Param("example") ProductCategoryExample example);


	int updateByExampleWithBLOBs(@Param("record") ProductCategory record,
			@Param("example") ProductCategoryExample example);


	int updateByExample(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);


	int updateByPrimaryKeySelective(ProductCategory record);


	int updateByPrimaryKeyWithBLOBs(ProductCategory record);


	int updateByPrimaryKey(ProductCategory record);

	List<ProductCategory> selectAll();
	
	List<ProductCategory> selectProductCategoryAll(ProductCategory record);
	
	int selectProductCategoryCount(ProductCategory record);
	
	ProductCategory selectByUpdateUserId(String updateUserName);
	
	ProductCategory selectProductCategoryContent(Integer id);
	
	int selectProductCount(Integer categoryId);
	
	int deleteProductCategoryList(List<Integer> id);
	
	int selectProductName(String name);
	
	int selectProductCategoryName(ProductCategory record);
	
	List<ProductCategoryRelation> selectCategoryRelation(ProductCategoryExample example);
}
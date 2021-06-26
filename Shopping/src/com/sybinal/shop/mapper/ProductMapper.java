package com.sybinal.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sybinal.shop.model.Product;
import com.sybinal.shop.model.ProductExample;
import com.sybinal.shop.model.ProductRelation;

public interface ProductMapper {


    long countByExample(ProductExample example);


    int deleteByExample(ProductExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(Product record);


    int insertSelective(Product record);


    List<Product> selectByExampleWithBLOBs(ProductExample example);


    List<Product> selectByExample(ProductExample example);


    Product selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);


    int updateByExampleWithBLOBs(@Param("record") Product record, @Param("example") ProductExample example);


    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);


    int updateByPrimaryKeySelective(Product record);


    int updateByPrimaryKeyWithBLOBs(Product record);


    int updateByPrimaryKey(Product record);

    List<ProductRelation> selectProductRelationByExample(ProductExample productExample);

    List<ProductRelation> selectProductAndImgByExample(ProductExample productExample);

    int selectCountByProductId(@Param("productId") int productId);

    List<Product> selectProductByCondition(Product record);

    int selectCountByCondition(Product record);

    int updateQuantityByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateQuantityCalculate(@Param("id") Integer id);
}
package com.sybinal.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sybinal.shop.model.ProductOptionInfo;
import com.sybinal.shop.model.ProductOptionInfoExample;
import com.sybinal.shop.model.ProductOptionRelation;

public interface ProductOptionInfoMapper {

    long countByExample(ProductOptionInfoExample example);


    int deleteByExample(ProductOptionInfoExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(ProductOptionInfo record);


    int insertSelective(ProductOptionInfo record);


    List<ProductOptionInfo> selectByExample(ProductOptionInfoExample example);


    ProductOptionInfo selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") ProductOptionInfo record, @Param("example") ProductOptionInfoExample example);


    int updateByExample(@Param("record") ProductOptionInfo record, @Param("example") ProductOptionInfoExample example);


    int updateByPrimaryKeySelective(ProductOptionInfo record);


    int updateByPrimaryKey(ProductOptionInfo record);

    /**
     * 根据商品ID查询Oping Value Sku信息
     *
     * @param productId
     * @return
     */
    List<ProductOptionRelation> selectProductOptionInfoByProductId(Integer productId);

}
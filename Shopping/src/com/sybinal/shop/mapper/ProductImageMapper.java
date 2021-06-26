package com.sybinal.shop.mapper;

import com.sybinal.shop.model.ProductImage;
import com.sybinal.shop.model.ProductImageExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProductImageMapper {


    int countByExample(ProductImageExample example);


    int deleteByExample(ProductImageExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(ProductImage record);


    int insertSelective(ProductImage record);


    List<ProductImage> selectByExampleWithBLOBs(ProductImageExample example);


    List<ProductImage> selectByExample(ProductImageExample example);


    ProductImage selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") ProductImage record,
                                 @Param("example") ProductImageExample example);


    int updateByExampleWithBLOBs(@Param("record") ProductImage record,
                                 @Param("example") ProductImageExample example);


    int updateByExample(@Param("record") ProductImage record,
                        @Param("example") ProductImageExample example);


    int updateByPrimaryKeySelective(ProductImage record);


    int updateByPrimaryKeyWithBLOBs(ProductImage record);


    int updateByPrimaryKey(ProductImage record);
}
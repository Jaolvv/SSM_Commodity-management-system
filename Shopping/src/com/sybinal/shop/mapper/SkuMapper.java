package com.sybinal.shop.mapper;

import com.sybinal.shop.model.Sku;
import com.sybinal.shop.model.SkuExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SkuMapper {

    long countByExample(SkuExample example);

    int deleteByExample(SkuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    List<Sku> selectByExampleWithBLOBs(SkuExample example);

    List<Sku> selectByExample(SkuExample example);

    Sku selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sku record, @Param("example") SkuExample example);

    int updateByExampleWithBLOBs(@Param("record") Sku record, @Param("example") SkuExample example);

    int updateByExample(@Param("record") Sku record, @Param("example") SkuExample example);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKeyWithBLOBs(Sku record);

    int updateByPrimaryKey(Sku record);

    int updateQuantityByExample(@Param("record") Sku record, @Param("example") SkuExample example);
}
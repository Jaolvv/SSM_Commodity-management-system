package com.sybinal.shop.mapper;

import com.sybinal.shop.model.Logistics;
import com.sybinal.shop.model.LogisticsExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LogisticsMapper {
    int countByExample(LogisticsExample example);


    int deleteByExample(LogisticsExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(Logistics record);


    int insertSelective(Logistics record);


    List<Logistics> selectByExample(LogisticsExample example);


    Logistics selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") Logistics record, @Param("example") LogisticsExample example);


    int updateByExample(@Param("record") Logistics record, @Param("example") LogisticsExample example);


    int updateByPrimaryKeySelective(Logistics record);


    int updateByPrimaryKey(Logistics record);

    int updateByPrimaryOrderNum(Logistics record);
}
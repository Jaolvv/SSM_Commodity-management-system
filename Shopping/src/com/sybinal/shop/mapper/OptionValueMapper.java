package com.sybinal.shop.mapper;

import com.sybinal.shop.model.OptionValue;
import com.sybinal.shop.model.OptionValueExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OptionValueMapper {

    long countByExample(OptionValueExample example);

    int deleteByExample(OptionValueExample example);

    int deleteByPrimaryKey(Integer id);

    int deleteByOptionId(@Param("id") Integer id);

    List<OptionValue> selectByOptionId(@Param("id") Integer id);

    int insert(OptionValue record);

    int insertSelective(OptionValue record);

    List<OptionValue> selectByExample(OptionValueExample example);

    OptionValue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OptionValue record, @Param("example") OptionValueExample example);

    int updateByExample(@Param("record") OptionValue record, @Param("example") OptionValueExample example);

    int updateByPrimaryKeySelective(OptionValue record);

    int updateByPrimaryKey(OptionValue record);

    int deleteOptionValueListByOptionId(List<Integer> id);
}
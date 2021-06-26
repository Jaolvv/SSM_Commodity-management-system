package com.sybinal.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sybinal.shop.model.Option;
import com.sybinal.shop.model.OptionExample;
import com.sybinal.shop.model.OptionRelation;

public interface OptionMapper {

    int countByExample(OptionExample example);

    int deleteByExample(OptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Option record);

    int insertSelective(Option record);

    List<Option> selectByExample(OptionExample example);

    Option selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Option record,
                                 @Param("example") OptionExample example);

    int updateByExample(@Param("record") Option record,
                        @Param("example") OptionExample example);

    int updateByPrimaryKeySelective(Option record);

    int updateByPrimaryKey(Option record);

    List<OptionRelation> selectOptionRelationByProductId(OptionExample example);

    int selectOptionCount(Option option);

    List<Option> selectOptionAll(Option option);

    int deleteOptionList(List<Integer> id);

    int selectOptionNameCount(String name);

    int selectOptionNameRows(Option option);

    int selectOptionValueCountByOptionId(@Param("id") Integer id);

    int selectProductCountByOptionId(@Param("id") Integer id);

    List<OptionRelation> selectOptionRelationById(OptionExample example);

    int selectProductOptionCountByOptionId(@Param("id") Integer id);

}
package com.sybinal.shop.mapper;

import com.sybinal.shop.model.Contect;
import com.sybinal.shop.model.ContectExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ContectMapper {

    int countByExample(ContectExample example);

    int deleteByExample(ContectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Contect record);

    int insertSelective(Contect record);

    List<Contect> selectByExample(ContectExample example);

    Contect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Contect record,
                                 @Param("example") ContectExample example);

    int updateByExample(@Param("record") Contect record,
                        @Param("example") ContectExample example);

    int updateByPrimaryKeySelective(Contect record);

    int updateByUserId(Contect record);

    int updateByPrimaryKey(Contect record);
}
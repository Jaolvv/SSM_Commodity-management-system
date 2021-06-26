package com.sybinal.shop.mapper;

import com.sybinal.shop.model.Sequence;
import com.sybinal.shop.model.SequenceExample;
import com.sybinal.shop.model.SequenceKey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SequenceMapper {


    int countByExample(SequenceExample example);


    int deleteByExample(SequenceExample example);


    int deleteByPrimaryKey(SequenceKey key);


    int insert(Sequence record);


    int insertSelective(Sequence record);


    List<Sequence> selectByExample(SequenceExample example);


    Sequence selectByPrimaryKey(SequenceKey key);


    int updateByExampleSelective(@Param("record") Sequence record,
                                 @Param("example") SequenceExample example);


    int updateByExample(@Param("record") Sequence record,
                        @Param("example") SequenceExample example);


    int updateByPrimaryKeySelective(Sequence record);


    int updateByPrimaryKey(Sequence record);
}
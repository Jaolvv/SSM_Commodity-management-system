package com.sybinal.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sybinal.shop.model.Comments;
import com.sybinal.shop.model.CommentsExample;
import com.sybinal.shop.model.CommentsRelation;

public interface CommentsMapper {


    long countByExample(CommentsExample example);


    int deleteByExample(CommentsExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(Comments record);


    int insertSelective(Comments record);


    List<Comments> selectByExampleWithBLOBs(CommentsExample example);


    List<Comments> selectByExample(CommentsExample example);

    Comments selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") Comments record, @Param("example") CommentsExample example);


    int updateByExampleWithBLOBs(@Param("record") Comments record, @Param("example") CommentsExample example);


    int updateByExample(@Param("record") Comments record, @Param("example") CommentsExample example);


    int updateByPrimaryKeySelective(Comments record);


    int updateByPrimaryKeyWithBLOBs(Comments record);


    int updateByPrimaryKey(Comments record);

    List<CommentsRelation> selectCommentsRelationByExample(CommentsExample example);


    List<Comments> selectCommentsByCondition(Comments record);

    int selectCountByCondition(Comments record);
}
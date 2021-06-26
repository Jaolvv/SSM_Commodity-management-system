package com.sybinal.shop.mapper;

import com.sybinal.shop.model.Wishlist;
import com.sybinal.shop.model.WishlistExample;
import com.sybinal.shop.model.WishlistRelation;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WishlistMapper {

    long countByExample(WishlistExample example);


    int deleteByExample(WishlistExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(Wishlist record);


    int insertSelective(Wishlist record);


    List<Wishlist> selectByExample(WishlistExample example);


    Wishlist selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") Wishlist record, @Param("example") WishlistExample example);


    int updateByExample(@Param("record") Wishlist record, @Param("example") WishlistExample example);


    int updateByPrimaryKeySelective(Wishlist record);


    int updateByPrimaryKey(Wishlist record);

    List<WishlistRelation> selectRelationByPrimaryKey(@Param("userId") int userId);

    int selectRelationCountByPrimaryKey(@Param("userId") int userId);
}
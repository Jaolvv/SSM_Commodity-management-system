package com.sybinal.shop.mapper;

import com.sybinal.shop.model.ArticleCategory;
import com.sybinal.shop.model.ArticleCategoryExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ArticleCategoryMapper {

    int countByExample(ArticleCategoryExample example);

    int deleteByExample(ArticleCategoryExample example);

    int deleteByPrimaryKey(Integer categoryId);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    List<ArticleCategory> selectByExample(ArticleCategory example);

    int selectCountByExample(ArticleCategory example);

    ArticleCategory selectByPrimaryKey(Integer categoryId);

    int updateByExampleSelective(@Param("record") ArticleCategory record,
                                 @Param("example") ArticleCategoryExample example);

    int updateByExample(@Param("record") ArticleCategory record, @Param("example") ArticleCategoryExample example);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);

    int countByName(ArticleCategory record);

    int selectArticleCountByCategory(ArticleCategory record);
}
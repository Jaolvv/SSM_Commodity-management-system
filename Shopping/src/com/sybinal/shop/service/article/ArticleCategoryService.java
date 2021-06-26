package com.sybinal.shop.service.article;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.ArticleCategory;

/**
 * 文章
 * */
public interface ArticleCategoryService {
	/**
	 * 增加文章分类
	 * @param user
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public Map<String, String> saveArticleCategory(ArticleCategory articleCategory) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 修改文章分类
	 * @param user
	 * @return
	 */
	public Map<String, String> modArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 删除文章分类
	 * @param data
	 * @return
	 */
	public Map<String, String> delArticleCategory(ArticleCategory searchVo);
	
	/**
	 * 查询所有文章分类
	 * @return
	 */
	public Map<String, Object> getAllArticleCategory(PageInformation pageInfo,ArticleCategory searchVo);
	
	public ArticleCategory getArticleCategoryById(int id);
	
	public  Map<String,String> checkRepeatArticleCategory(ArticleCategory vo,Map<String, String> message);
	
	public Map<String, String> checkContentIsNull(ArticleCategory vo,Map<String, String> message);
	
	public ArticleCategory getUserName(ArticleCategory vo);
	public Map<String, String> checkMessage(ArticleCategory vo, Map<String, String> message);

	public List<ArticleCategory> getAllArticleCategory();
}

package com.sybinal.shop.service.article;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.Article;

/**
 * 文章
 * */
public interface ArticleService {
	/**
	 * 增加文章
	 * @param user
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public int saveArticle(Article article) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 修改文章
	 * @param user
	 * @return
	 */
	public int modArticle(Article article);
	
	/**
	 * 删除文章
	 * @param data
	 * @return
	 */
	public int delArticle(Integer id);
	
	/**
	 * 查询所有文章
	 * @return
	 */
	public Map<String, Object> getAllArticleByModel(PageInformation pageInfo,Article searchVo);
	
	public List<Article> getAllArticleByCategory(int id);
	
	public Article getArticleById(Integer id);

}

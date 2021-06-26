package com.sybinal.shop.service.article;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.ArticleMapper;
import com.sybinal.shop.model.Article;
import com.sybinal.shop.model.ArticleCategory;
import com.sybinal.shop.utils.PagingTool;
import com.sybinal.shop.utils.UserUtils;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	PagingTool pagingTool;
	@Autowired
	ArticleMapper articleMapper;
	
	
	@Override
	@Transactional
	public int saveArticle(Article article) throws IllegalAccessException, InvocationTargetException {
		article.setUpdateUserId(UserUtils.getUserName());
		return articleMapper.insert(article);
	}

	@Override
	@Transactional
	public int modArticle(Article article) {
		article.setUpdateUserId(UserUtils.getUserName());
		return articleMapper.updateByPrimaryKeySelective(article);
	}

	@Override
	@Transactional
	public int delArticle(Integer id) {
		return articleMapper.deleteByPrimaryKey(id);
	}

	public Article getArticleById(Integer id){
		return articleMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Map<String, Object> getAllArticleByModel(PageInformation pageInfo,Article searchVo) {
        Map<String, Object> data = new HashMap<String, Object>();
		try {
			int pageCount = articleMapper.selectCountByArticle(searchVo);
			List<ArticleCategory> list = pagingTool.PagingData("com.sybinal.shop.mapper.ArticleMapper.selectByArticle", searchVo, (Integer.parseInt(pageInfo.getiDisplayStart())/Integer.parseInt(pageInfo.getiDisplayLength()))+1,Integer.parseInt(pageInfo.getiDisplayLength()));
			data.put("sEcho", pageInfo.getsEcho());
			data.put("iTotalRecords", pageCount);
			data.put("iTotalDisplayRecords", pageCount);
			data.put("aaData", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List<Article> getAllArticleByCategory(int id) {
		return articleMapper.selectAllByCategoryId(id);
	}



	

}

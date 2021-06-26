package com.sybinal.shop.service.article;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.ArticleCategoryMapper;
import com.sybinal.shop.model.ArticleCategory;
import com.sybinal.shop.utils.PagingTool;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

	@Autowired
	PagingTool pagingTool;
	@Autowired
	ArticleCategoryMapper articleCategoryMapper;
	
	
	@Override
	@Transactional
	public Map<String, String> saveArticleCategory(ArticleCategory articleCategory) throws IllegalAccessException, InvocationTargetException {
		articleCategory=getUserName(articleCategory);
		Map<String, String> message=new HashMap<String, String>();
	    message= checkRepeatArticleCategory(articleCategory, message);
		if (message.size()==0) {
			articleCategoryMapper.insert(articleCategory);
			message.put("type", "success");
			message.put("title", "1");//成功
		}else {
	    	message.put("type", "error");
			message.put("title", "2");//失败
		}
		return message;
	}

	@Override
	@Transactional
	public Map<String, String> modArticleCategory(ArticleCategory articleCategory) {
		articleCategory=getUserName(articleCategory);
		Map<String, String> message=new HashMap<String, String>();
		message=checkRepeatArticleCategory(articleCategory,message);
	    if(message.size()==0){
	    	articleCategoryMapper.updateByPrimaryKeySelective(articleCategory);
	    	message.put("type", "success");
			message.put("title", "1");//成功
	    }else {
	    	message.put("type", "error");
			message.put("title", "2");//失败
		}
		return message;
	}

	@Override
	@Transactional
	public Map<String, String> delArticleCategory(ArticleCategory delVo) {
		Map<String, String> message=new HashMap<String, String>();
		message=checkContentIsNull(delVo,message);
		if (message.size()==0) {
	    	 articleCategoryMapper.deleteByPrimaryKey(delVo.getCategoryId());
	    	 message.put("type", "success");
			 message.put("title", "1");//成功
		}else {
	    	message.put("type", "error");
			message.put("title", "2");//失败
		}
		return message;
	}

	@Override
	public Map<String, Object> getAllArticleCategory(PageInformation pageInfo,ArticleCategory searchVo) {
				Map<String, Object> data = new HashMap<String, Object>();
				try {
					int pageCount = articleCategoryMapper.selectCountByExample(searchVo);
					List<ArticleCategory> list = pagingTool.PagingData("com.sybinal.shop.mapper.ArticleCategoryMapper.selectByExample", searchVo, 
							Integer.parseInt(pageInfo.getiDisplayStart())/Integer.parseInt(pageInfo.getiDisplayLength())+1,Integer.parseInt(pageInfo.getiDisplayLength()));
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
	public ArticleCategory getArticleCategoryById(int id) {
		// TODO Auto-generated method stub
		return articleCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map<String, String> checkRepeatArticleCategory(ArticleCategory vo,Map<String, String> message) {
		int count=articleCategoryMapper.countByName(vo);
		if (count>0) {
			message.put("nameError", "1");//分类名重复
		}
		return message;
	}

	@Override
	public Map<String, String> checkContentIsNull(ArticleCategory vo,Map<String, String> message) {
		int count=articleCategoryMapper.selectArticleCountByCategory(vo);
		if (count>0) {
			message.put("contentError", "1");//分类下有内容
		}
		return message;
	}
	@Override
	public Map<String, String> checkMessage(ArticleCategory vo,Map<String, String> message) {
		if (message.size()==0) {
			message.put("type", "success");
			 message.put("title","1");//成功
		}else {
			message.put("type", "error");
			message.put("title","2");//失败
		}
		return message;
	}
	@Override
	public ArticleCategory getUserName(ArticleCategory vo) {
		UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		vo.setUpdateUserId(userDetails.getUsername());
		return vo;
	}

	@Override
	public List<ArticleCategory> getAllArticleCategory() {
		return articleCategoryMapper.selectByExample(new ArticleCategory());
	}	

}

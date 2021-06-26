package com.sybinal.shop.utils;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component("pagingTool")
public class PagingTool {
	
	@Resource(name = "sqlSession")
	SqlSession sqlSession;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T PagingData(String mapperStr, Object obj, int currentPage) {
		List list = PagingData(mapperStr, obj, currentPage, Constants.PAGE_NUMBER);
		return (T) list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T PagingData(String mapperStr, Object obj, int currentPage, int pageSize) {
		List list = sqlSession.selectList(mapperStr, obj, new RowBounds((currentPage - 1) * pageSize, pageSize));
		return (T) list;
	}
	
}

package com.sybinal.shop.service.comments;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.Comments;

public interface AdminCommentsService {
	public Map<String,Object> getComments(PageInformation pageInfo, Comments comments) throws ParseException;
	
	int examineComments(List<Comments> commentsList) throws ParseException;
	
	int deleteComments(List<Comments> commentsList) throws ParseException;
}

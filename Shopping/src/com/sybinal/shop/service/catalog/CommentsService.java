package com.sybinal.shop.service.catalog;

import java.util.List;
import java.util.Map;

import com.sybinal.shop.model.Comments;
import com.sybinal.shop.model.CommentsRelation;

public interface CommentsService {

	boolean saveValidation(Comments comments);

	Comments saveComments(Comments comments);

	List<CommentsRelation> getCommentsByProductId(Map<String, Object> reqMap);

}

package com.sybinal.shop.service.catalog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.mapper.CommentsMapper;
import com.sybinal.shop.model.Comments;
import com.sybinal.shop.model.CommentsExample;
import com.sybinal.shop.model.CommentsRelation;


@Service
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	CommentsMapper commentsMapper;

	@Override
	@Transactional
	public boolean saveValidation(Comments comments) {
		return comments != null && comments.getUserId() != null && comments.getProductId() != null
				&& comments.getContent() != null && comments.getStars() != null && comments.getProductId() > 0
				&& comments.getUserId() > 0 && comments.getContent().length() > 0 && comments.getStars() >= 0;
	}

	@Override
	@Transactional
	public Comments saveComments(Comments comments) {
		comments.setId(null);
		comments.setAudit(0);
		comments.setCreateTime(new Date());
		comments.setStars(comments.getStars() > 5 ? 5 : comments.getStars());
		commentsMapper.insertSelective(comments);
		return comments;
	}

	@Override
	public List<CommentsRelation> getCommentsByProductId(Map<String, Object> reqMap) {
		Object condition = null;
		if ((condition = reqMap.get("productId")) == null)
			return null;
		CommentsExample example = new CommentsExample();
		CommentsExample.Criteria criteria = example.createCriteria();
		/* 指定查询的商品编号 */
		criteria.andRelationProductIdEqualTo((int) condition);
		/* 指定查询的已审批的商品 */
		criteria.andAuditEqualTo(1);
		/* 指定排序条件 */
		if ((condition = reqMap.get("order")) != null)
			switch ((String) condition) {
			case "ASC":
				example.setOrderByClause("`c`.`create_time` ASC");
				break;
			case "DESC":
				example.setOrderByClause("`c`.`create_time` DESC");
				break;
			default:
				condition = "";
				break;
			}
		return commentsMapper.selectCommentsRelationByExample(example);
	}

}

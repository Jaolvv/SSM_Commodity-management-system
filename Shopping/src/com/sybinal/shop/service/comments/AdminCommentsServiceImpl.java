package com.sybinal.shop.service.comments;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.CommentsMapper;
import com.sybinal.shop.model.Comments;
import com.sybinal.shop.model.User;
import com.sybinal.shop.utils.Constants;
import com.sybinal.shop.utils.PagingTool;

@Service
public class AdminCommentsServiceImpl implements AdminCommentsService {

	@Autowired
	CommentsMapper commentsMapper;
	
	@Autowired
	PagingTool pagingTool;

	@Override
	public Map<String, Object> getComments(PageInformation pageInfo, Comments comments) throws ParseException {
		int pageCount = commentsMapper.selectCountByCondition(comments);

		List<User> listUser = pagingTool.PagingData("com.sybinal.shop.mapper.CommentsMapper.selectCommentsByCondition", comments,
				(Integer.parseInt(pageInfo.getiDisplayStart()) / Constants.PAGE_NUMBER) + 1);

		HashMap<String, Object> datas = new HashMap<String, Object>();
		datas.put("sEcho", pageInfo.getsEcho());
		datas.put("iTotalRecords", pageCount);
		datas.put("iTotalDisplayRecords", pageCount);
		datas.put("aaData", listUser);

		return datas;
	}

	@Override
	public int examineComments(List<Comments> commentsList) throws ParseException {
		int cnt=0;
		if(commentsList!=null && commentsList.size()>0){
			for(Comments comments:commentsList){
				cnt+=commentsMapper.updateByPrimaryKeySelective(comments);
			}
		}
		return cnt;
	}

	@Override
	@Transactional
	public int deleteComments(List<Comments> commentsList) throws ParseException {
		int cnt=0;
		if(commentsList!=null && commentsList.size()>0){
			for(Comments comments:commentsList){
				cnt+=commentsMapper.deleteByPrimaryKey(comments.getId());
			}
		}
		return cnt;
	}

}

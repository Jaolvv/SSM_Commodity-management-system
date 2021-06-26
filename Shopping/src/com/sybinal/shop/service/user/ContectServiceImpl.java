package com.sybinal.shop.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.ContectMapper;
import com.sybinal.shop.model.Contect;
import com.sybinal.shop.model.ContectExample;
import com.sybinal.shop.model.User;
import com.sybinal.shop.model.ContectExample.Criteria;
import com.sybinal.shop.utils.Page;
import com.sybinal.shop.utils.PagingTool;

@Service
public class ContectServiceImpl implements ContectService {

	@Autowired
	ContectMapper contectMapper;
	
	@Autowired
	PagingTool pagingTool;

	@Override
	public Map<String, Object> getContectByUserId(PageInformation pageInfo,Integer userId) {
		ContectExample example =new ContectExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		int pageCount = contectMapper.countByExample(example);
		List<User> listUser = pagingTool.PagingData("com.sybinal.shop.mapper.ContectMapper.selectByExample", example,
				pageInfo.getStartRow());
		HashMap<String, Object> datas = new HashMap<String, Object>();
		datas.put("sEcho", pageInfo.getsEcho());
		datas.put("iTotalRecords", pageCount);
		datas.put("iTotalDisplayRecords", pageCount);
		datas.put("aaData", listUser);
		return datas;
	}

	@Override
	@Transactional
	public boolean savaContect(Contect contect) {
		ContectExample example =new ContectExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserIdEqualTo(contect.getUserId());
		int pageCount = contectMapper.countByExample(example);
		if(pageCount<5){
			contectMapper.insert(contect);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public void delContect(Contect contect) {
		contectMapper.deleteByPrimaryKey(contect.getId());
	}

	@Override
	@Transactional
	public void modContect(Contect contect) {
		contectMapper.updateByPrimaryKey(contect);
	}
	
	@Override
	@Transactional
	public void updateContect(Contect contect) {
		contectMapper.updateByUserId(contect);
		contectMapper.updateByPrimaryKeySelective(contect);
	}

	@Override
	public Map<String,Object> getContect(Contect contect) {
		ContectExample example = new ContectExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(contect.getUserId());
		Map<String,Object> map = new HashMap<String,Object>();
		List<Contect> list = pagingTool.PagingData("com.sybinal.shop.mapper.ContectMapper.selectByExample",
				example, contect.getNowPage());
		int count = contectMapper.countByExample(example);
		long pageNow = Page.confirmPage(count, 10);
		map.put("data", list);
		map.put("countPage", pageNow);
		return map;
		
	}

}

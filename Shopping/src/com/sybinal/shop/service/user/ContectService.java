package com.sybinal.shop.service.user;

import java.util.Map;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.Contect;

/**
 * 用户
 * */
public interface ContectService {
	/**
	 * 根据用户名查询用户信息
	 * */
	public Map<String, Object> getContectByUserId(PageInformation pageInfo,Integer userId);
	
	public boolean savaContect(Contect contect);
	
	public Map<String,Object> getContect(Contect contect);
	
	public void delContect(Contect contect);
	
	public void modContect(Contect contect);
	
	public void updateContect(Contect contect);

}

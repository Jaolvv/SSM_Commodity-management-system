package com.sybinal.shop.service.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.ProductCategoryMapper;
import com.sybinal.shop.model.ProductCategory;
import com.sybinal.shop.utils.PagingTool;
import com.sybinal.shop.utils.UserUtils;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	ProductCategoryMapper productCategoryMapper;
	
	@Autowired
	PagingTool pagingTool;

	@Override
	@Transactional
	public Map<String, Object> saveProductCategory(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Map<String, Object> modProductCategory(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Map<String, Object> delProductCategory(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductCategory> getProductCategory() {
		return productCategoryMapper.selectAll();
	}

	@Override
	public Map<String, Object> getProductCategoryAll(PageInformation pageInfo, ProductCategory category) {
		HashMap<String, Object> datas = new HashMap<String, Object>();
		int pageCount = productCategoryMapper.selectProductCategoryCount(category);
		List<ProductCategory> list = pagingTool.PagingData("com.sybinal.shop.mapper.ProductCategoryMapper.selectProductCategoryAll", category,
				(Integer.parseInt(pageInfo.getiDisplayStart())/Integer.parseInt(pageInfo.getiDisplayLength()))+1,Integer.parseInt(pageInfo.getiDisplayLength()));
		
		datas.put("sEcho", pageInfo.getsEcho());
		datas.put("iTotalRecords", pageCount);
		datas.put("iTotalDisplayRecords", pageCount);
		datas.put("aaData", list);
		
		return datas;
	}

	@Override
	@Transactional
	public int addProductCategory(ProductCategory category) {
		int i = 0;
		ProductCategory pc = productCategoryMapper.selectByUpdateUserId(UserUtils.getUserName());
		category.setUpdateUserId(pc.getUpdateUserId());
		i = productCategoryMapper.insertSelective(category);
		return i;
	}

	@Override
	@Transactional
	public int updateProductCategory(ProductCategory category) {
		int i = 0;
		ProductCategory pc = productCategoryMapper.selectByUpdateUserId(UserUtils.getUserName());
		category.setUpdateUserId(pc.getUpdateUserId());
		i = productCategoryMapper.updateByPrimaryKeySelective(category);
		return i;
	}

	@Override
	public ProductCategory getProductCategoryContent(Integer id) {
		return productCategoryMapper.selectProductCategoryContent(id);
	}

	@Override
	public int getProductCount(String id) {
		int i = 0;
		String[] ids = id.split("-");
		if(ids != null && ids.length != 0){
			for(String categoryId : ids){
				int a = productCategoryMapper.selectProductCount(Integer.parseInt(categoryId));
				if(a > 0){
					i++;
				}
			}
		}
		return i;
	}

	@Override
	@Transactional
	public int delteProductCategory(String id) {
		int i = 0;
		List<Integer> ids = new ArrayList<Integer>();
		String[] categoryIds = id.split("-");
		if(categoryIds != null && categoryIds.length != 0){
			for(String categoryId : categoryIds){
				Integer idTemp = Integer.parseInt(categoryId);
				ids.add(idTemp);
			}
		}
		i = productCategoryMapper.deleteProductCategoryList(ids);
		return i;
	}

	@Override
	public int getProductNameCount(String name) {
		return productCategoryMapper.selectProductName(name);
	}

	@Override
	public int getProductCategoryName(ProductCategory category) {
		return productCategoryMapper.selectProductCategoryName(category);
	}

}

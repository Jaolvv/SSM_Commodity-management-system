package com.sybinal.shop.service.user;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.mapper.ProductMapper;
import com.sybinal.shop.mapper.WishlistMapper;
import com.sybinal.shop.model.Wishlist;
import com.sybinal.shop.model.WishlistExample;
import com.sybinal.shop.utils.Page;
import com.sybinal.shop.utils.PagingTool;

@Service
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	WishlistMapper wishlistMapper;
	
	@Autowired
	ProductMapper productMapper;
	

	@Autowired
	PagingTool pagingTool;

	@Override
	public Map<String, Object> getUserWishlist(Map<String, Object> data) {
		Object condition = null;
		long totalCount = wishlistMapper.selectRelationCountByPrimaryKey((int) data.get("userId"));
		/* 当前页 */
		int currentPage = 1;
		if ((condition = data.get("currentPage")) != null)
			currentPage = (int) condition < 1 ? 1 : (int) condition;
		else
			data.put("currentPage", currentPage);
		/* 分页大小 */
		int pageSize = 8;
		if ((condition = data.get("pageSize")) != null)
			pageSize = (int) condition;
		else
			data.put("pageSize", pageSize);
		long totalPage = Page.confirmPage(totalCount, pageSize);
		if (currentPage > totalPage) {
			currentPage = (int) totalPage;
			data.put("currentPage", currentPage);
		}
		data.put("totalPage", totalPage);
		data.put("totalCount", totalCount);
		data.put("wishlist", pagingTool.PagingData("com.sybinal.shop.mapper.WishlistMapper.selectRelationByPrimaryKey", (int) data.get("userId"), currentPage, pageSize));
		return data;
	}
	
	@Override
	@Transactional
	public void saveUserWishlist(Wishlist wishlist) {
		wishlist.setId(null);
		wishlist.setMarkTime(new Date());
		wishlistMapper.insertSelective(wishlist);
	}

	@Override
	@Transactional
	public boolean delWishlistById(Map<String, Object> reqMap) {
		return this.delUserWishlist(reqMap);
	}

	@Override
	@Transactional
	public boolean delUserWishlist(Map<String, Object> data) {
		Object condition = null;
		if ((condition = data.get("userId")) == null)
			return false;
		WishlistExample wishlistExample = new WishlistExample();
		WishlistExample.Criteria criteria = wishlistExample.createCriteria();
		/* 指定 User id*/
		criteria.andUserIdEqualTo((int) condition);
		/* 指定 Wishlis id*/
		if ((condition = data.get("wishlisId")) != null)
			criteria.andIdEqualTo((int) condition);
		return wishlistMapper.deleteByExample(wishlistExample) != 0;
	}

	@Override
	public boolean selectIsWishlist(Wishlist wishlist) {
		if (wishlist.getUserId() == null || wishlist.getProductId() == null)
			return false;
		WishlistExample example = new WishlistExample();
		WishlistExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(wishlist.getUserId());
		criteria.andProductIdEqualTo(wishlist.getProductId());
		long count = wishlistMapper.countByExample(example);
		return count == 0;
	}

}

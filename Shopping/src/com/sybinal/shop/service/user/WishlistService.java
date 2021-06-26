package com.sybinal.shop.service.user;

import java.util.Map;

import com.sybinal.shop.model.Wishlist;

public interface WishlistService {

	public void saveUserWishlist(Wishlist wishlist);

	public boolean delUserWishlist(Map<String, Object> data);

	public boolean delWishlistById(Map<String, Object> reqMap);
	
	public Map<String, Object> getUserWishlist(Map<String, Object> data);

	public boolean selectIsWishlist(Wishlist wishlist);

}

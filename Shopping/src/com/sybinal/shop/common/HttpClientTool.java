package com.sybinal.shop.common;


public interface HttpClientTool {
	
	/* 注册 */ 
	public static final String REGISTER = "register";
	/* 忘记密码 */
	public static final String API_URL_FORGET_PASSWORD = "forgetPassword";
	/* 登录url */
	public static final String API_URL_LOGIN = "login";
	/* 发送短信码 */
	public static final String SEND_SMS = "send/sms/msg";
	/* 获取用户信息 */
	public static final String API_URL_CUSTOMER = "get/user/info";
	/* 获取用户基本信息 */
	public static final String API_URL_CUSTOMER_BASIC = "get/user/basic";
	/* 更新用户信息 */
	public static final String API_URL_UPDATE_CUSTOMER = "user/update/customer";
	/* 更新用户密码 */
	public static final String API_URL_CHANGE_PASSWORD = "user/change/password";
	/* 获取用户心愿单信息 */
	public static final String API_URL_CUSTOMER_WISHLIST = "user/wishlist";
	/* 删除用户心愿单 */
	public static final String API_URL_CUSTOMER_REMOVE_WISHLIST = "user/remove/wishlist";
	/* 新增用户心愿单信息 */
	public static final String API_URL_CUSTOMER_ADD_WISHLIST = "user/add/wishlist";
	/* 获取用户订单列表 */
	public static final String API_URL_ORDERS = "user/orders/list";
	/* 获取用户订单详细 */
	public static final String API_URL_ORDER_DETAIL = "user/orders/detail";
	/* 商品分类数据 */
	public static final String API_URL_CATEGORY = "catalog/category";
	/* 商品分类数据 */
	public static final String API_URL_CATEGORY_CONDITION = "catalog/category/condition";
	/* 商品分类数据 */
	public static final String API_URL_CATEGORY_ALL = "catalog/all";
	/* 添加商品评论 */
	public static final String API_URL_CATALOG_ADD_REVIEW = "catalog/product/add/review";
	/* 获取商品评论 */
	public static final String API_URL_CATALOG_SHOW_REVIEW = "catalog/product/reviews";
	/* 查询商品 */
	public static final String API_URL_CATALOG_INDEX_PRODUCTS = "catalog/category/product";
	/* 获取分类商品 */
	public static final String API_URL_CATALOG_PRODUCT = "catalog/product";
	/* 查询商品 */
	public static final String API_URL_CATALOG_SEARCH_PRODUCTS = "catalog/search/product";
	/* 查询热销商品 */
	public static final String API_URL_CATALOG_PRODUCTS_HOT = "catalog/product/hot";
	/* 查询商品详情 */
	public static final String API_URL_CATALOG_PRODUCT_DETAILS = "catalog/product/details";
	/* 查询商品Option sku */
	public static final String API_URL_CATALOG_PRODUCT_SKU = "catalog/product/sku/list";
	/* 查询商品Option详情 */
	public static final String API_URL_CATALOG_PRODUCT_OPTION = "catalog/product/option/details";
	/* 获取购物车 */
	public static final String API_URL_CART = "cart";
	/* 新增购物车 */
	public static final String API_URL_CART_ADD = "cart/add";
	/* 移除购物车 */
	public static final String API_URL_CART_ITEMS_REMOVE = "cart/items/remove";
	/* 更新购物车 */
	public static final String API_URL_CART_ITEMS_UPDATE = "cart/items/update";
	/* 移除全部购物车 */
	public static final String API_URL_CART_ITEMS_REMOVEALL = "cart/items/removeall";
	/* 签出购物车准备支付 */
	public static final String API_URL_PAY_ADDPAYMENT = "/cart/checkout/addPayMent";
	/* 购物车数量 */
	public static final String API_URL_CART_COUNT = "cart/count";
	/* 添加常用联系人 */
	public static final String API_URL_CONTECT_ADD = "user/add/contect";
	/* 删除常用联系人 */
	public static final String API_URL_CONTECT_REMOVE = "user/remove/contect";
	/* 修改常用联系人 */
	public static final String API_URL_CONTECT_UPDATE = "user/update/contect";
	/* 查询常用联系人 */
	public static final String API_URL_CONTECT = "contect";
	/* 查询常用联系人 */
	public static final String API_URL_CONTECT_DEFAULT = "user/contect/default";
	/* 支付订单*/
	public static final String API_URL_ORDER_PAYMENT = "order/PayMent";
	/* 密码找回*/
	public static final String API_URL_USER_RECOVERY = "/user/recovery/password";
	/* 获取电子钱包金额初始值*/
	public static final String API_URL_EWALLET_DEFAULT = "/ewallet/defalutValue";
	/* 获取用户电子钱包余额*/
	public static final String API_URL_CUSTOMER_Account ="/account/userAccount";
	/* 用户充值到电子钱包中*/
	public static final String API_URL_CUSTOMER_Recharge_Account="/account/recharge";
	/* 用户利用电子钱包进行支付*/
	public static final String API_URL_CUSTOMER_Pay_Order ="/account/payOrder";
	/**
	 * 请求后台API核心方法
	 * @param url
	 * @param obj
	 * @return
	 * @throws ApiServiceException
	 */
	public ApiResponseObject doPostJson(String url, Object obj) throws ApiServiceException;

}

package com.sybinal.shop.controller.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sybinal.shop.common.AjaxResult;
import com.sybinal.shop.common.ApiResponseEnum;
import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.model.Contect;
import com.sybinal.shop.model.User;
import com.sybinal.shop.model.Wishlist;
import com.sybinal.shop.service.user.ContectService;
import com.sybinal.shop.service.user.UserService;
import com.sybinal.shop.service.user.WishlistService;

@RestController
@RequestMapping("api/v1")
public class UserApiController extends AbstractApiController {

	@Autowired
	WishlistService wishlistService;

	@Autowired
	UserService userService;
	
	@Autowired
	ContectService contectService;

	/**
	 * 获取用户心愿单信息
	 * 
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value = "user/wishlist", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject saveOption(@RequestBody Map<String, Object> reqMap) {
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), wishlistService.getUserWishlist(reqMap));
	}

	/**
	 * 删除用户心愿单
	 * 
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value = "user/remove/wishlist", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject delWishlist(@RequestBody Map<String, Object> reqMap) {
		if (wishlistService.delWishlistById(reqMap))
			return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), null);
		else
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
	}

	/**
	 * 新增用户心愿单信息
	 * 
	 * @param wishlist
	 * @return
	 */
	@RequestMapping(value = "user/add/wishlist", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject saveWishlist(@RequestBody Wishlist wishlist) {
		if (!wishlistService.selectIsWishlist(wishlist))
			return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), new AjaxResult(false, "商品已被收藏里了!"));
		wishlistService.saveUserWishlist(wishlist);
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), new AjaxResult(true, "商品已被成功收藏了!"));
	}

	/**
	 * 注册用户
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject saveUser(@RequestBody User user) throws IllegalAccessException, InvocationTargetException {
		int cnt = userService.saveUser(user);
		if (cnt <= 0) {
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		} else {
			return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), user);
		}
	}

	/**
	 * 登录
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject login(@RequestBody User user) throws IllegalAccessException, InvocationTargetException {

		User newUser = userService.getUser(user);
		if (newUser == null) {
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		} else {
			return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), newUser);
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/get/user/info", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getUserInfo(@RequestBody User user)
			throws IllegalAccessException, InvocationTargetException {
		User newUser = userService.getUser(user);
		if (newUser == null) {
			return reponseJSON(ApiResponseEnum.ERROR_DATA_EMPTY.getCode(), ApiResponseEnum.ERROR_DATA_EMPTY.getName(), 1);
		} else {
			return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), newUser);
		}
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/get/user/basic", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getUserBasic(@RequestBody Map<String,Object> reqMap) {
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), userService.getUser(reqMap));
	}
	
	/**
	 * 修改用户信息
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/user/update/customer", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject updateUser(@RequestBody User user)
			throws IllegalAccessException, InvocationTargetException {
		int cnt = userService.modUser(user);
		if (cnt <= 0) {
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), new AjaxResult(false, "提示 保存失败!"));
		} else {
			return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), new AjaxResult(true, "提示 保存成功!"));
		}
	}

	/**
	 * 更新用户密码
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/user/change/password", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject updatePassword(@RequestBody Map<String, Object> reqMap)
			throws IllegalAccessException, InvocationTargetException {
		int cnt = userService.modUserPassword(reqMap);
		if (cnt <= 0) {
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), new AjaxResult(false, "提示 当前密码不正确!"));
		} else {
			return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), new AjaxResult(true, "提示 密码修改成功,请重新登录!"));
		}
	}
	
	/**
	 * 密码找回
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/user/recovery/password", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject recoveryPassword(@RequestBody User user)
			throws IllegalAccessException, InvocationTargetException {
		int cnt = userService.recoveryUserPassword(user);
		if (cnt <= 0) {
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), new AjaxResult(false, "提示 当前密码不正确!"));
		} else {
			return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), new AjaxResult(true, "提示 密码修改成功,请重新登录!"));
		}
	}

	/**
	 * 发送短信码
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/send/sms/msg", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject sendMessage(@RequestBody Map<String, Object> reqMap)
			throws IllegalAccessException, InvocationTargetException {
		String messageCode = String.valueOf(Math.ceil(Math.random() * 500000 + 500000));
		messageCode = messageCode.substring(0, messageCode.indexOf('.'));
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), messageCode);
	}
	
	/**
	 * 新增联系人
	 * 
	 * @param wishlist
	 * @return
	 */
	@RequestMapping(value = "/user/add/contect", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject saveContect(@RequestBody Contect contect) {

		if(StringUtils.isEmpty(contect.getUserId())){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), contectService.savaContect(contect));
		
	}
	
	/**
	 * 查询联系人
	 * */
	@RequestMapping(value = "/contect", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getContect(@RequestBody Contect contect) {
		if(StringUtils.isEmpty(contect.getUserId())){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		Map<String,Object> map = contectService.getContect(contect);
		if(map.get("data")==null){
			return this.reponseJSON(ApiResponseEnum.ERROR_DATA_EMPTY.getCode(), ApiResponseEnum.ERROR_DATA_EMPTY.getName(), null);
		}
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), map);
	}
	
	/**
	 * 删除联系人
	 * */
	@RequestMapping(value = "/user/remove/contect", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject removeContect(@RequestBody Contect contect) {
		if(StringUtils.isEmpty(contect.getUserId()) || StringUtils.isEmpty(contect.getId())){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		contectService.delContect(contect);

		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), null);
	}
	
	/**
	 * 修改联系人
	 * */
	@RequestMapping(value = "/user/update/contect", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject updateContect(@RequestBody Contect contect) {
		if(StringUtils.isEmpty(contect.getUserId()) || StringUtils.isEmpty(contect.getId())){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		contectService.modContect(contect);

		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), true);
	}
	
	/**
	 * 默认联系人
	 * */
	@RequestMapping(value = "user/contect/default", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject defaultContect(@RequestBody Contect contect) {
		if(StringUtils.isEmpty(contect.getUserId()) || StringUtils.isEmpty(contect.getId())){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		contectService.updateContect(contect);

		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), null);
	}

}

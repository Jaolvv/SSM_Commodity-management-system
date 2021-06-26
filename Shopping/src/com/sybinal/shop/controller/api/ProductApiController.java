package com.sybinal.shop.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sybinal.shop.common.AjaxResult;
import com.sybinal.shop.common.ApiResponseEnum;
import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.model.Comments;
import com.sybinal.shop.service.catalog.CommentsService;
import com.sybinal.shop.service.catalog.ProductService;

@RestController
@RequestMapping("api/v1")
public class ProductApiController extends AbstractApiController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CommentsService commentsService;

	/**
	 * 获取分类商品
	 * 
	 * @return
	 */
	@RequestMapping(value = "catalog/product", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject searchProductByCategory(@RequestBody Map<String,Object> reqMap) {
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), productService.getProductByCategory(reqMap));
	}

	/**
	 * 查询商品
	 * 
	 * @return
	 */
	@RequestMapping(value = "catalog/search/product", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject searchProduct(@RequestBody Map<String,Object> reqMap) {
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), productService.getProductRelation(reqMap));
	}
	
	/**
	 * 查询商品
	 * 
	 * @return
	 */
	@RequestMapping(value = "catalog/product/hot", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject searchProductByHot(@RequestBody Map<String,Object> reqMap) {
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), productService.getProduct(reqMap));
	}
	
	/**
	 * 商品详情页
	 * 
	 * @return
	 */
	@RequestMapping(value = "catalog/product/details", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject productDetails(@RequestBody Map<String,Object> reqMap) {
		Object re = null;
		if ((re = productService.getProductDetailsById(reqMap)) != null)
			return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), re);
		return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), re);
	}

	/**
	 * 添加商品评论
	 * 
	 * @return
	 */
	@RequestMapping(value = "catalog/product/add/review", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject addComments(@RequestBody Comments comments) {
		if (!commentsService.saveValidation(comments)) {
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), new AjaxResult(false));
		}
		commentsService.saveComments(comments);
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), new AjaxResult(true));
	}

	/**
	 * 获取商品评论
	 * 
	 * @return
	 */
	@RequestMapping(value = "catalog/product/reviews", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getComments(@RequestBody Map<String,Object> reqMap) {
		return this.reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), commentsService.getCommentsByProductId(reqMap));
	}
}
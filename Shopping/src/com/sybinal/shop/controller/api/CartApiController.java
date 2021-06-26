package com.sybinal.shop.controller.api;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sybinal.shop.common.ApiResponseEnum;
import com.sybinal.shop.common.ApiResponseObject;
import com.sybinal.shop.model.Order;
import com.sybinal.shop.model.OrderItem;
import com.sybinal.shop.model.Product;
import com.sybinal.shop.model.Sku;
import com.sybinal.shop.model.User;
import com.sybinal.shop.service.catalog.ProductService;
import com.sybinal.shop.service.catalog.SkuService;
import com.sybinal.shop.service.order.OrderService;

@RestController
@RequestMapping("api/v1")
public class CartApiController extends AbstractApiController {
	
	@Autowired
	OrderService  orderService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	SkuService skuService;
	
	/**
	 * 新增购物车 
	 * */
	@RequestMapping(value = "/cart/add", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject saveOrder(@RequestBody Order order) throws IllegalAccessException, InvocationTargetException{
		if(order.getUserId()==null){
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		Product product = productService.getProductByid(order.getOrderItem().getProductId());
		if(product == null) {
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		//需要计算库存
		if(product.getInventoryFlag() == 0) {
			if (!StringUtils.isEmpty(order.getOrderItem().getOptionValueKeyGroup())) {
				Sku sku = skuService.getSkyById(order.getOrderItem().getSkuId());
				if (sku != null) {
					if (order.getOrderItem().getQuantity() > sku.getQuantity()) {
						return reponseJSON(ApiResponseEnum.FAIL.getCode(), "库存不足", sku);
					}
				}
			} else {
				if (order.getOrderItem().getQuantity() > product.getQuantity()) {
					return reponseJSON(ApiResponseEnum.FAIL.getCode(), "库存不足", product);
				}
			}
		}
			
		orderService.saveOrder(order);
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), 1);
	}
	
	/**
	 * 移除购物车
	 * */
	@RequestMapping(value = "/cart/items/remove", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject removeOrder(@RequestBody Order order) throws IllegalAccessException, InvocationTargetException{
		if (order.getUserId()==null) {
			return this.reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}else if(order.getOrderItem()==null){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		order.setType(1);
		orderService.delOrder(order);
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), 1);
	}
	/**
	 * 移除全部购物车
	 * */
	@RequestMapping(value = "/cart/items/removeall", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject removeAllOrder(@RequestBody Order order) throws IllegalAccessException, InvocationTargetException{
		if (order.getUserId() == null) {
			return this.reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		orderService.delAllOrder(order);
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), null);
	}
	/**
	 * 查询购物车详细
	 * */
	@RequestMapping(value = "/cart", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getOrderItem(@RequestBody Order order) throws IllegalAccessException, InvocationTargetException{
		if (order.getUserId() == null) {
			return this.reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		Order orderData = orderService.getOrderItem(order);
		if(orderData==null){
			return this.reponseJSON(ApiResponseEnum.ERROR_DATA_EMPTY.getCode(), ApiResponseEnum.ERROR_DATA_EMPTY.getName(), null);
		}
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), orderData);
	}
	
	/**
	 * 修改购物车
	 * */
	@RequestMapping(value = "cart/items/update", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject modOrder(@RequestBody Order order) throws IllegalAccessException, InvocationTargetException{
		if(order.getUserId()==null){
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		for (OrderItem orderItem : order.getOrderItemList()) {
			Product product = productService.getProductByid(orderItem.getProductId());
			if(product == null) {
				return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
			}
			//需要计算库存
			if(product.getInventoryFlag() == 0) {
				if (!StringUtils.isEmpty(orderItem.getOptionValueKeyGroup())) {
					Sku sku = skuService.getSkyById(orderItem.getSkuId());
					if (sku != null) {
						if (orderItem.getQuantity() > sku.getQuantity()) {
							return reponseJSON(ApiResponseEnum.FAIL.getCode(), "库存不足", sku);
						}
					}
				} else {
					if (orderItem.getQuantity() > product.getQuantity()) {
						return reponseJSON(ApiResponseEnum.FAIL.getCode(), "库存不足", product);
					}
				}
			}
			
		}
		orderService.modOrder(order);
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), 1);
	}
	/**
	 * 查询购物车数量
	 * */
	@RequestMapping(value = "/cart/count", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getOrderCount(@RequestBody User user) throws IllegalAccessException, InvocationTargetException{
		if (user.getId() == null) {
			return this.reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), orderService.getOrderCount(user));
	}

}

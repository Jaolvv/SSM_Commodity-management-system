package com.sybinal.shop.controller.api;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

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
import com.sybinal.shop.service.catalog.ProductService;
import com.sybinal.shop.service.catalog.SkuService;
import com.sybinal.shop.service.option.OptionService;
import com.sybinal.shop.service.order.OrderService;

@RestController
@RequestMapping("api/v1")
public class OrderApiController extends AbstractApiController {
	
	@Autowired
	OrderService  orderService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	SkuService skuService;
	
	@Autowired
	OptionService optionService;
	/**
	 * 添加订单
	 * */
	@RequestMapping(value = "/cart/checkout/addPayMent", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject changeOrder(@RequestBody Order order) throws IllegalAccessException, InvocationTargetException{
		if (order.getUserId()==null) {
			return this.reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}else if(order.getOrderItemList()==null && order.getOrderItemList().size()==0){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		String orderNum = orderService.changeOrder(order);
		if(orderNum==null){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), orderNum);
	}
	/**
	 * 修改订单
	 * */
	@RequestMapping(value = "/order/items/update", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject modOrder(@RequestBody Order order) throws IllegalAccessException, InvocationTargetException{
		if(StringUtils.isEmpty(order.getUserId())){
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		//orderService.modOrder(reqMap);
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), 1);
	}
	
	/**
	 * 获取订单详细列表
	 * */
	@RequestMapping(value = "/user/orders/detail", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getOrderItemList(@RequestBody Order order) throws IllegalAccessException, InvocationTargetException{
		if (StringUtils.isEmpty(order.getUserId())) {
			return this.reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), orderService.getOrderItemData(order));
	}
	
	/**
	 * 获取订单列表
	 * @throws ParseException 
	 * */
	@RequestMapping(value = "user/orders/list", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject getOrder(@RequestBody Order order) throws ParseException  {
		if (order.getUserId()==null) {
			return this.reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		Map<String,Object> map = orderService.getOrder(order);
		if(map==null){
			return this.reponseJSON(ApiResponseEnum.ERROR_PARAM.getCode(), ApiResponseEnum.ERROR_PARAM.getName(), null);
		}
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), map);
	}
	
	/**
	 * 支付订单
	 * @throws Exception 
	 * */
	@RequestMapping(value = "/order/PayMent", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApiResponseObject addPayMent(@RequestBody Order order) throws Exception{
		//使用order的userid 获取用户的购物车
		Order orderData = orderService.getOrderByUserId(order.getUserId());
		if(orderData==null){
			return reponseJSON(ApiResponseEnum.FAIL.getCode(), ApiResponseEnum.FAIL.getName(), null);
		}
		for (OrderItem orderItem : orderData.getOrderItemList()) {
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
		order.setOrderNum(orderData.getOrderItemList().get(0).getOrderNum());
		orderService.savePaymentHistory(order);
		optionService.setQuantity(orderData.getOrderItemList());
		return reponseJSON(ApiResponseEnum.SUCCESS.getCode(), ApiResponseEnum.SUCCESS.getName(), 1);
	}
	
}

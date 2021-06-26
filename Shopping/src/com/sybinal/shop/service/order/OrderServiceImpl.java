package com.sybinal.shop.service.order;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.ContectMapper;
import com.sybinal.shop.mapper.LogisticsMapper;
import com.sybinal.shop.mapper.OrderHistoryMapper;
import com.sybinal.shop.mapper.OrderItemMapper;
import com.sybinal.shop.mapper.OrderMapper;
import com.sybinal.shop.mapper.PaymentHistoryMapper;
import com.sybinal.shop.mapper.ProductMapper;
import com.sybinal.shop.mapper.SkuMapper;
import com.sybinal.shop.mapper.UserMapper;
import com.sybinal.shop.model.Contect;
import com.sybinal.shop.model.Logistics;
import com.sybinal.shop.model.LogisticsExample;
import com.sybinal.shop.model.Order;
import com.sybinal.shop.model.OrderExample;
import com.sybinal.shop.model.OrderExample.Criteria;
import com.sybinal.shop.model.OrderHistory;
import com.sybinal.shop.model.OrderHistoryExample;
import com.sybinal.shop.model.OrderItem;
import com.sybinal.shop.model.OrderItemExample;
import com.sybinal.shop.model.PaymentHistory;
import com.sybinal.shop.model.PaymentHistoryExample;
import com.sybinal.shop.model.Product;
import com.sybinal.shop.model.ProductExample;
import com.sybinal.shop.model.Sku;
import com.sybinal.shop.model.SkuExample;
import com.sybinal.shop.model.User;
import com.sybinal.shop.model.UserExample;
import com.sybinal.shop.service.sequence.SequenceService;
import com.sybinal.shop.service.user.ContectService;
import com.sybinal.shop.utils.Constants;
import com.sybinal.shop.utils.DateUtils;
import com.sybinal.shop.utils.Page;
import com.sybinal.shop.utils.PagingTool;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderMapper orderMapper;

	@Autowired
	OrderItemMapper orderItemMapper;

	@Autowired
	SequenceService sequenceService;

	@Autowired
	PaymentHistoryMapper paymentHistoryMapper;

	@Autowired
	OrderHistoryMapper orderHistoryMapper;

	@Autowired
	LogisticsMapper logisticsMapper;

	@Autowired
	PagingTool pagingTool;

	@Autowired
	ContectService contectService;

	@Autowired
	ContectMapper contectMapper;

	@Autowired
	SkuMapper skuMapper;

	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	UserMapper userMapper;

	// 新增购物车
	@Override
	@Transactional
	public void saveOrder(Order order) throws IllegalAccessException, InvocationTargetException {
		// 查询购物车 orderOptionMapper
		OrderItem orderItem = null;
		Date orderCreateTime = new Date();
		String orderNum = "";
		Order orderW = orderMapper.selectByUserId(order.getUserId());
		if (orderW == null) {
			orderNum = sequenceService.getSequence(Constants.SEQUENCE_TYPE_O,
					Constants.SEQUENCE_TYPE_O
							+ DateUtils.getFormatDate(orderCreateTime, Constants.SYSTEM_DATE_FOMART_1),
					Constants.SEQUENCE_COUNTER);
			order.setOrderNum(orderNum);
			// 未付款
			order.setPaymentFlag(0);
			// 购物车类型
			order.setType(1);
			orderMapper.insert(order);
			orderItem = order.getOrderItem();
		} else {
			OrderItemExample orderItemExample = new OrderItemExample();
			com.sybinal.shop.model.OrderItemExample.Criteria orderItemCriteria = orderItemExample.createCriteria();
			orderItemCriteria.andOrderNumT4EqualTo(orderW.getOrderNum());
			orderItemCriteria.andProductIdEqualTo(order.getOrderItem().getProductId());
			if (!StringUtils.isEmpty(order.getOrderItem().getOptionValueKeyGroup())) {
				orderItemCriteria.andoptionValueKeyGroupEqualTo(order.getOrderItem().getOptionValueKeyGroup());
			}
			List<OrderItem> list = orderItemMapper.selectByExample(orderItemExample);
			if (list != null && list.size() > 0) {
				OrderItem orderItemQ = new OrderItem();
				orderItemQ.setQuantity(list.get(0).getQuantity() + order.getOrderItem().getQuantity());
				orderItemQ.setId(list.get(0).getId());
				orderItemMapper.updateByQuantity(orderItemQ);

				Integer orderPrice = orderMapper.selectByPriceSum(orderW.getOrderNum());
				orderW.setPrice(orderPrice);
				orderMapper.updateByPrice(orderW);
			} else {
				orderItem = new OrderItem();
				orderItem = order.getOrderItem();
				orderNum = orderW.getOrderNum();
			}
		}
		// 购物车详细信息
		if (orderItem != null) {
			String orderItemNum = sequenceService.getSequence(Constants.SEQUENCE_TYPE_I,
					Constants.SEQUENCE_TYPE_I
							+ DateUtils.getFormatDate(orderCreateTime, Constants.SYSTEM_DATE_FOMART_1),
					Constants.SEQUENCE_COUNTER);
			orderItem.setOrderNum(orderNum);
			orderItem.setId(orderItemNum);
			if (!StringUtils.isEmpty(order.getOrderItem().getOptionValueKeyGroup())) {
				// 去sku表查询价格 并把sku表里的价格放到 orderItem表price中
				SkuExample exampleSku = new SkuExample();
				com.sybinal.shop.model.SkuExample.Criteria criteriaSku = exampleSku.createCriteria();
				criteriaSku.andOptionValueKeyGroupEqualTo(order.getOrderItem().getOptionValueKeyGroup());
				criteriaSku.andProductIdEqualTo(order.getOrderItem().getProductId());
				List<Sku> listSku = skuMapper.selectByExample(exampleSku);
				orderItem.setPrice(listSku.get(0).getPrice());
			} else {
				// 去商品表把 价格拿出来 放到orderItem的price中
				ProductExample productExample = new ProductExample();
				com.sybinal.shop.model.ProductExample.Criteria productCriteria = productExample.createCriteria();
				productCriteria.andIdEqualTo(orderItem.getProductId());
				List<Product> productList = productMapper.selectByExample(productExample);
				orderItem.setPrice(productList.get(0).getPrice());
			}
			orderItemMapper.insert(orderItem);
			// 更新购物车总钱数
			Integer orderPrice = orderMapper.selectByPriceSum(orderNum);
			order.setOrderNum(orderNum);
			order.setPrice(orderPrice);
			orderMapper.updateByPrice(order);
		}

	}

	/**
	 * 修改订单和购物车
	 */
	@Override
	@Transactional
	public void modOrder(Order order) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < order.getOrderItemList().size(); i++) {
			orderItemMapper.updateByQuantity(order.getOrderItemList().get(i));
		}
		// 查询购物车并更新总价 selectByUserId type=1存在固定死的条件
		Order orderID = orderMapper.selectByUserId(order.getUserId());
		Integer orderPrice = orderMapper.selectByPriceSum(orderID.getOrderNum());
		orderID.setPrice(orderPrice);
		orderMapper.updateByPrice(orderID);
	}

	/**
	 * 订单购物删除
	 */
	@Override
	@Transactional
	public void delOrder(Order order) throws IllegalAccessException, InvocationTargetException {
		OrderHistory orderHistory = new OrderHistory();
		OrderItemExample example = new OrderItemExample();
		com.sybinal.shop.model.OrderItemExample.Criteria criteria = example.createCriteria();
		// //订单删除 1为购物车 0为订单
		if (order.getType() == 0) {
			// 订单详细
			criteria.andOrderNumEqualTo(order.getOrderNum());

			PaymentHistoryExample exampleHistory = new PaymentHistoryExample();
			com.sybinal.shop.model.PaymentHistoryExample.Criteria criteria1 = exampleHistory.createCriteria();
			criteria1.andOrderNumEqualTo(order.getOrderNum());
			// 支付状态查询 如果支付状态存在则不允许删除
			int count = paymentHistoryMapper.countByExample(exampleHistory);
			if (count == 0) {
				orderHistory.setStatus(4);
				orderHistory.setNote(4);
				orderHistory.setOrderNum(order.getOrderNum());
				orderHistory.setUpdateUserId(order.getUserId());
				orderHistoryMapper.insert(orderHistory);
				orderMapper.deleteByPrimaryKey(order.getOrderNum());
				orderItemMapper.deleteByExample(example);
			}
		} else {
			criteria.andIdEqualTo(order.getOrderItem().getId());
			// 购物车删除
			orderItemMapper.deleteByExample(example);
			order = orderMapper.selectByUserId(order.getUserId());
			Integer orderPrice = orderMapper.selectByPriceSum(order.getOrderNum());
			order.setOrderNum(order.getOrderNum());
			order.setPrice(orderPrice);
			orderMapper.updateByPrice(order);
		}
	}

	/**
	 * 查询订单
	 * 
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> getOrder(Order order) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();

		// price >=
		if (!StringUtils.isEmpty(order.getStartPrice())) {
			criteria.andPriceGreaterThanOrEqualTo(Integer.parseInt(order.getStartPrice()));
		}
		// price <=
		if (!StringUtils.isEmpty(order.getEndPrice())) {
			criteria.andPriceLessThanOrEqualTo(Integer.parseInt(order.getEndPrice()));
		}

		// 根据订单状态
		if (order.getStatus() != null) {
			criteria.andStatusEqualTo(order.getStatus());
		}
		// 根据单号查询
		if (!StringUtils.isEmpty(order.getOrderNum())) {
			criteria.andOrderNumEqualTo(order.getOrderNum());
		}
		// 根据付款状态查询
		if (!StringUtils.isEmpty(order.getPaymentFlag())) {
			criteria.andPaymentFlagEqualTo(order.getPaymentFlag());
		}
		// 根据开始时间查询
		if (!StringUtils.isEmpty(order.getStartDate())) {
			criteria.andCreateTimeGreaterThanOrEqualTo(sdf.parse(order.getStartDate() + " 00:00:00"));
		}
		// 根据结束时间查询
		if (!StringUtils.isEmpty(order.getEndDate())) {
			criteria.andCreateTimeLessThanOrEqualTo(sdf.parse(order.getEndDate() + " 59:59:59"));
		}
		criteria.andTypeEqualTo(order.getType());
		Map mapData = null;
		// 根据用户id查询
		if (!StringUtils.isEmpty(order.getUserId())) {
			criteria.andUserIdEqualTo(order.getUserId());
			int pageCount = orderMapper.countByExample(example);
			List<Order> listOrder = pagingTool.PagingData("com.sybinal.shop.mapper.OrderMapper.selectByExample",
					example, order.getNowPage());

			mapData = new HashMap<>();
			mapData.put("orderListData", listOrder);
			// 获取总页数
			mapData.put("pageCount", Page.confirmPage(pageCount, Constants.PAGE_NUMBER));
			mapData.put("nowPage", order.getNowPage());
		}
		return mapData;
	}

	// 支付
	@Override
	@Transactional
	public void savePaymentHistory(Order order) throws IllegalAccessException, InvocationTargetException {
		PaymentHistory paymentHistory = new PaymentHistory();
		// 获取数据库联系人地址
		Contect contect = contectMapper.selectByPrimaryKey(order.getContect().getId());
		OrderHistory orderHistory = new OrderHistory();
		order.setOrderNum(order.getOrderNum());
		// 已下单
		order.setStatus(1);
		// 已付款
		order.setPaymentFlag(1);
		// 生成订单
		order.setType(0);
		// 赋值
		if (contect != null) {
			order.setContactAddress(contect.getAddress());
			order.setContactMobile(contect.getMobile());
			order.setContactName(contect.getName());
		}
		orderHistory.setOrderNum(order.getOrderNum());
		orderHistory.setUpdateUserId(order.getUserId());
		// 订单历史 已下单
		orderHistory.setStatus(1);
		orderHistory.setNote(1);
		paymentHistory.setPaymentType(order.getContect().getPaymentType());
		Integer orderPrice = orderMapper.selectByPriceSum(order.getOrderNum());
		paymentHistory.setProductPrice(orderPrice);
		paymentHistory.setOrderNum(order.getOrderNum());
		paymentHistory.setUserId(order.getUserId());
		order.setPrice(orderPrice);
		// 购物车区分
		orderHistoryMapper.insert(orderHistory);
		paymentHistoryMapper.insert(paymentHistory);
		orderMapper.updateByPrimaryKeySelective(order);
	}

	// 删除全部购物车
	@Override
	@Transactional
	public void delAllOrder(Order order) throws IllegalAccessException, InvocationTargetException {
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(order.getUserId())) {
			criteria.andUserIdEqualToRemove(order.getUserId());
			criteria.andTypeEqualTo(1);
			orderItemMapper.deleteByPrimaryKey(order.getUserId());
			orderMapper.deleteByExample(example);
		}
	}

	// 查询购物车详细
	@SuppressWarnings("unchecked")
	@Override
	public Order getOrderItem(Order order) throws IllegalAccessException, InvocationTargetException {
		List<OrderItem> listOrderItem = null;
		OrderItemExample example = new OrderItemExample();
		com.sybinal.shop.model.OrderItemExample.Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(order.getUserId())) {
			order = orderMapper.selectByUserId(order.getUserId());
			if (order == null) {
				return order;
			}
			criteria.andUserI1dTEqualTo(order.getUserId());
			criteria.andTypeEqualTo(order.getType());
			listOrderItem = new ArrayList<>();
			listOrderItem = orderItemMapper.selectByExample(example);
			order.setOrderItemList(listOrderItem);

			Contect contect = new Contect();
			contect.setUserId(order.getUserId());
			Map<String, Object> map = contectService.getContect(contect);
			if (map != null && map.get("data") != null) {
				List<Contect> contectList = (List<Contect>) map.get("data");
				order.setContectList(contectList);
			}

			if (order.getType() == 0) {
				LogisticsExample exampleL = new LogisticsExample();
				com.sybinal.shop.model.LogisticsExample.Criteria criteriaL = exampleL.createCriteria();
				criteriaL.andOrderNumEqualTo(order.getOrderNum());
				List<Logistics> logisticsList = logisticsMapper.selectByExample(exampleL);
				order.setLogisticsList(logisticsList);
			}
		}
		return order;
	}

	// 查询购物车数量
	@Override
	public Integer getOrderCount(User user) throws IllegalAccessException, InvocationTargetException {
		Integer count = null;
		if (!StringUtils.isEmpty(user.getId())) {
			count = orderItemMapper.countByExample(user.getId());
		}
		return count;
	}

	/**
	 * 改变购物车状态 //返回改变的orderNum
	 */
	@Override
	@Transactional
	public String changeOrder(Order order) throws IllegalAccessException, InvocationTargetException {
		OrderHistory orderHistory = new OrderHistory();
		// 改变购物车数量
		for (int i = 0; i < order.getOrderItemList().size(); i++) {
			orderItemMapper.updateByQuantity(order.getOrderItemList().get(i));
		}
		// 查询购物车并更新总价 selectByUserId type=1存在固定死的条件
		Order orderID = orderMapper.selectByUserId(order.getUserId());
		Integer orderPrice = orderMapper.selectByPriceSum(orderID.getOrderNum());
		orderID.setPrice(orderPrice);
		orderMapper.updateByPrice(orderID);
		// 改变购物车为订单状态 ，与orderMapper.updateByPrice(orderID);可合并
		orderMapper.updateByExampleOrder(orderID.getOrderNum());

		orderHistory.setOrderNum(orderID.getOrderNum());
		orderHistory.setUpdateUserId(order.getUserId());
		// 订单历史 配送中
		orderHistory.setStatus(1);
		orderHistory.setNote(1);
		orderHistoryMapper.insert(orderHistory);
		return orderID.getOrderNum();
	}

	/**
	 * 后台查询订单历史列表
	 * 
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getOrderHistoryManage(PageInformation pageInfo) throws Exception {
		OrderHistory example = new OrderHistory();
		int pageCount = orderHistoryMapper.selectByHistoryCount();
		List<Order> listOrder = pagingTool.PagingData("com.sybinal.shop.mapper.OrderHistoryMapper.selectByHistoryList",
				example,
				(Integer.parseInt(pageInfo.getiDisplayStart()) / Integer.parseInt(pageInfo.getiDisplayLength()) + 1),
				Integer.parseInt(pageInfo.getiDisplayLength()));
		HashMap<String, Object> datas = new HashMap<String, Object>();
		datas.put("sEcho", pageInfo.getsEcho());
		datas.put("iTotalRecords", pageCount);
		datas.put("iTotalDisplayRecords", pageCount);
		datas.put("aaData", listOrder);
		return datas;
	}
	
	/**
	 * 后台查询订单历史列表
	 * 
	 * @throws Exception
	 */
	@Override
	public List<OrderHistory> getOrderHistoryManage(Order order){
		OrderHistoryExample example = new OrderHistoryExample();
		com.sybinal.shop.model.OrderHistoryExample.Criteria criteria = example.createCriteria();
		criteria.andOrderNumEqualTo(order.getOrderNum());
		return orderHistoryMapper.selectByExample(example);
	}

	/**
	 * 后台查询订单列表
	 * 
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getOrderManage(PageInformation pageInfo, Order order) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();

		// price >=
		if (!StringUtils.isEmpty(order.getStartPrice())) {
			criteria.andPriceGreaterThanOrEqualTo(Integer.parseInt(order.getStartPrice()));
		}
		// price <=
		if (!StringUtils.isEmpty(order.getEndPrice())) {
			criteria.andPriceLessThanOrEqualTo(Integer.parseInt(order.getEndPrice()));
		}

		// 根据订单状态
		if (order.getStatus() != null) {
			criteria.andStatusEqualTo(order.getStatus());
		}
		// 根据单号查询
		if (!StringUtils.isEmpty(order.getOrderNum())) {
			criteria.andOrderNumEqualTo(order.getOrderNum());
		}
		// 根据付款状态查询
		if (!StringUtils.isEmpty(order.getPaymentFlag())) {
			criteria.andPaymentFlagEqualTo(order.getPaymentFlag());
		}
		// 根据开始时间查询
		if (!StringUtils.isEmpty(order.getStartDate())) {
			criteria.andCreateTimeGreaterThanOrEqualTo(sdf.parse(order.getStartDate() + " 00:00:00"));
		}
		// 根据结束时间查询
		if (!StringUtils.isEmpty(order.getEndDate())) {
			criteria.andCreateTimeLessThanOrEqualTo(sdf.parse(order.getEndDate() + " 23:59:59"));
		}
		// 根据用户id查询
		// if (!StringUtils.isEmpty(order.getUserId()) &&
		// !StringUtils.isEmpty(order.getType())) {
		criteria.andTypeEqualTo(0);
		example.setDistinct(true);
		int pageCount = orderMapper.countByExample(example);
		List<Order> listOrder = pagingTool.PagingData("com.sybinal.shop.mapper.OrderMapper.selectByExample", example,
				(Integer.parseInt(pageInfo.getiDisplayStart()) / Integer.parseInt(pageInfo.getiDisplayLength()) + 1),
				Integer.parseInt(pageInfo.getiDisplayLength()));

		HashMap<String, Object> datas = new HashMap<String, Object>();
		datas.put("sEcho", pageInfo.getsEcho());
		datas.put("iTotalRecords", pageCount);
		datas.put("iTotalDisplayRecords", pageCount);
		datas.put("aaData", listOrder);

		return datas;
	}

	@Override
	public Order getOrderByUserId(Integer userId) throws Exception {
		OrderItemExample example = new OrderItemExample();
		Order order = new Order();
		com.sybinal.shop.model.OrderItemExample.Criteria criteria = example.createCriteria();
		criteria.andUserI1dTEqualTo(userId);
		criteria.andTypeEqualTo(1);
		List<OrderItem> listOrderItem = orderItemMapper.selectByExample(example);
		order.setOrderItemList(listOrderItem);
		return order;
	}

	// 查询购物车详细
	@Override
	public Order getOrderItemData(Order order) {
		List<OrderItem> listOrderItem = null;
		OrderItemExample example = new OrderItemExample();
		com.sybinal.shop.model.OrderItemExample.Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(order.getUserId())) {
			order = orderMapper.selectByPrimaryKey(order.getOrderNum());
			if (order == null) {
				return order;
			}
			criteria.andUserI1dTEqualTo(order.getUserId());
			criteria.andOrderNumT1EqualTo(order.getOrderNum());
			criteria.andTypeEqualTo(order.getType());
			listOrderItem = new ArrayList<>();
			listOrderItem = orderItemMapper.selectByExample(example);
			order.setOrderItemList(listOrderItem);
			LogisticsExample exampleL = new LogisticsExample();
			com.sybinal.shop.model.LogisticsExample.Criteria criteriaL = exampleL.createCriteria();
			criteriaL.andOrderNumEqualTo(order.getOrderNum());
			List<Logistics> logisticsList = logisticsMapper.selectByExample(exampleL);
			order.setLogisticsList(logisticsList);

		}
		return order;
	}

	@Override
	@Transactional
	public void saveLogistics(Logistics logistics) {
		OrderHistory orderHistory = new OrderHistory();
		LogisticsExample example = new LogisticsExample();
		com.sybinal.shop.model.LogisticsExample.Criteria criteriaL = example.createCriteria();
		criteriaL.andOrderNumEqualTo(logistics.getOrderNum());
		Order order = orderMapper.selectByPrimaryKey(logistics.getOrderNum());
		//订单历史赋值
		orderHistory.setOrderNum(order.getOrderNum());
		orderHistory.setStatus(logistics.getState());
		orderHistory.setNote(logistics.getState());
		//查询userid
		UserExample userExample = new UserExample();
		com.sybinal.shop.model.UserExample.Criteria criteriaUser = userExample.createCriteria();
		criteriaUser.andUserNameEqualTo(logistics.getUserName());
		List<User> userList = userMapper.selectByExample(userExample);
		orderHistory.setUpdateUserId(userList.get(0).getId());
		//改变订单状态 
		order.setStatus(logistics.getState());
		// 配送单联系人地址电话
		logistics.setContactAddress(order.getContactAddress());
		logistics.setContactMobile(order.getContactMobile());
		logistics.setContactName(order.getContactName());
		logistics.setLogisticsNum(UUID.randomUUID().toString().replace("-", ""));
		logistics.setState(logistics.getState());
		if(logistics.getState()==2){
			logisticsMapper.insert(logistics);
		}else{
			logisticsMapper.updateByPrimaryOrderNum(logistics);
		}
		orderMapper.updateByPrimaryKeySelective(order);
		orderHistoryMapper.insert(orderHistory);
	}

}
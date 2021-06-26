package com.sybinal.shop.mapper;

import com.sybinal.shop.model.Order;
import com.sybinal.shop.model.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {


	int countByExample(OrderExample example);


	int deleteByExample(OrderExample example);


	int deleteByPrimaryKey(String orderNum);


	int insert(Order record);


	int insertSelective(Order record);


	List<Order> selectByExample(OrderExample example);


	Order selectByPrimaryKey(String orderNum);
	
	
	Integer selectByPriceSum(String orderNum);
	
	
	Order selectByUserId(Integer userId);
	
	
	Order selectByOrderUserId(Integer userId);


	int updateByExampleSelective(@Param("record") Order record);


	int updateByExample(@Param("record") Order record,
			@Param("example") OrderExample example);

	
	int updateByExampleOrder(String orderNum);
	

	int updateByPrimaryKeySelective(Order record);


	int updateByPrimaryKey(Order record);
	
	int updateByPrice(Order record);
}
package com.sybinal.shop.mapper;

import com.sybinal.shop.model.OrderItem;
import com.sybinal.shop.model.OrderItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderItemMapper {


	int countByExample(Integer userId);


	int deleteByExample(OrderItemExample example);


	int deleteByPrimaryKey(Integer userId);


	int insert(OrderItem record);


	int insertSelective(OrderItem record);


	List<OrderItem> selectByExample(OrderItemExample example);


	OrderItem selectByPrimaryKey(Integer id);


	int updateByExampleSelective(@Param("record") OrderItem record,
			@Param("example") OrderItemExample example);


	int updateByExample(@Param("record") OrderItem record,
			@Param("example") OrderItemExample example);


	int updateByPrimaryKeySelective(OrderItem record);


	int updateByPrimaryKey(OrderItem record);
	
	int updateByQuantity(OrderItem record);
}
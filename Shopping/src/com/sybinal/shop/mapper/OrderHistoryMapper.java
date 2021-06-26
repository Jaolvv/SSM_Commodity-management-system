package com.sybinal.shop.mapper;

import com.sybinal.shop.model.OrderHistory;
import com.sybinal.shop.model.OrderHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderHistoryMapper {


	int countByExample(OrderHistoryExample example);


	int deleteByExample(OrderHistoryExample example);


	int deleteByPrimaryKey(Integer id);


	int insert(OrderHistory record);


	int insertSelective(OrderHistory record);


	List<OrderHistory> selectByExample(OrderHistoryExample example);


	OrderHistory selectByPrimaryKey(Integer id);


	int updateByExampleSelective(@Param("record") OrderHistory record,
			@Param("example") OrderHistoryExample example);


	int updateByExample(@Param("record") OrderHistory record,
			@Param("example") OrderHistoryExample example);


	int updateByPrimaryKeySelective(OrderHistory record);


	int updateByPrimaryKey(OrderHistory record);
	

	int selectByHistoryCount();
}
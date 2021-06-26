package com.sybinal.shop.mapper;

import com.sybinal.shop.model.PaymentHistory;
import com.sybinal.shop.model.PaymentHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaymentHistoryMapper {


	int countByExample(PaymentHistoryExample example);


	int deleteByExample(PaymentHistoryExample example);


	int deleteByPrimaryKey(Integer id);


	int insert(PaymentHistory record);


	int insertSelective(PaymentHistory record);


	List<PaymentHistory> selectByExample(PaymentHistoryExample example);


	PaymentHistory selectByPrimaryKey(Integer id);


	int updateByExampleSelective(@Param("record") PaymentHistory record,
			@Param("example") PaymentHistoryExample example);


	int updateByExample(@Param("record") PaymentHistory record,
			@Param("example") PaymentHistoryExample example);


	int updateByPrimaryKeySelective(PaymentHistory record);


	int updateByPrimaryKey(PaymentHistory record);
}
package com.sybinal.shop.service.option;

import java.util.List;
import java.util.Map;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.Option;
import com.sybinal.shop.model.OptionRelation;
import com.sybinal.shop.model.OrderItem;
import com.sybinal.shop.model.Sku;
import com.sybinal.shop.model.SkuVOInfo;

/**
 * Option service
 * 
 * @author erase_leo
 *
 */
public interface OptionService {
	
	int saveOption(Option option);

	int modOption(Option option);

	int delOption(int optionId);

	List<Option> getOption();

	Option getOption(int optionId);

	List<OptionRelation> getOptionRelation(Map<String, Object> reqMap);
	
	Map<String, Object>  getOptionList(PageInformation pageInfo, Option option);
	 
	int addOptionInfo(Option option);
	
	int updateOptionInfo(Option option);
	
	Option getOptionContent(Integer id);
	
	int delteOption(String id);
	
	List<Sku> getQuantity(List<Integer> skuList);

	void setQuantity(List<OrderItem> Order);
	
	int getOptionNameCount(String name);
	
	int getOptionNameRows(Option option);
	
	List<SkuVOInfo> getOptionByOptionId(List<Integer> idList);
}

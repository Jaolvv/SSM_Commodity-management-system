package com.sybinal.shop.model;

import java.util.List;

public class ProductOptionRelation extends ProductOptionInfo {
	
	/* 一对一的 Option */
	private Option option;
	
	/* 一对多的Option value */
	private List<OptionValue> optionValuesList;
		
	
	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	/**
	 * 一对多的Option value
	 * 
	 * @return
	 */
	public List<OptionValue> getOptionValuesList() {
		return optionValuesList;
	}

	/**
	 * 一对多的Option value
	 * 
	 * @param optionValuesList
	 */
	public void setOptionValuesList(List<OptionValue> optionValuesList) {
		this.optionValuesList = optionValuesList;
	}
	
}

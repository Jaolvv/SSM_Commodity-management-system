package com.sybinal.shop.model;

import java.util.List;

public class OptionRelation extends Option {
	
	/* 一对多的Option value */
	private List<OptionValue> optionValuesList;

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

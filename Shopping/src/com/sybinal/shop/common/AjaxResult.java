package com.sybinal.shop.common;

public class AjaxResult {

	private boolean success;
	private String message;
	private Object dataSet;

	public AjaxResult() {
		super();
	}
	
	public AjaxResult(boolean success) {
		super();
		this.success = success;
	}

	public AjaxResult(boolean success, String message) {
		this(success);
		this.message = message;
	}

	public AjaxResult(boolean success, String message, Object dataSet) {
		this(success, message);
		this.dataSet = dataSet;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getDataSet() {
		return dataSet;
	}

	public void setDataSet(Object dataSet) {
		this.dataSet = dataSet;
	}

}

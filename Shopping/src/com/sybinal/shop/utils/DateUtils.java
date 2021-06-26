package com.sybinal.shop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 根据指定格式，将日期转换成字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatDate(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String returnDate = "";
		try {
			returnDate = df.format(date);
		} catch (Exception e) {
			returnDate = "";
		}
		return returnDate;
	}
	/**
	 * 根据指定格式，将字符串转换成日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date getStringToDate(String date,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);  
		Date returnDate=null;
		try {
			returnDate=sdf.parse(date);
		} catch (ParseException e) {
			returnDate=null;
		}  
		return returnDate;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

};

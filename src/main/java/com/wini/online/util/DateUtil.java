package com.wini.online.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static Date str2Date(String date,String pattern) throws ParseException{
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(date);
	}
}

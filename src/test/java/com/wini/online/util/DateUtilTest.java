package com.wini.online.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateUtilTest {
	
	@Test
	public void testDate(){
		String formatStr = "yyyy-MM-dd HH:mm:ss";
		DateFormat dd=new SimpleDateFormat(formatStr);
		String s = " 2013-08-03 06:11:00";
		
		try {
			Date date = dd.parse(s);
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

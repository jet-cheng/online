package com.wini.online.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WiniUtil {
	private static final Logger LOGGER = Logger.getLogger(WiniUtil.class.getName());
	
	private static Properties pro = new Properties();
	
	static{
		try {
			InputStream inputStream = WiniUtil.class.getClassLoader().getResourceAsStream("wini.properties");
			pro.load(inputStream);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"加载wini.properties失败", e);
		}
	}
	
	public static String get(String key){
		return pro.getProperty(key);
	}
	
	public static String get(String key, String[] params){
		try {
			String s = pro.getProperty(key);
			MessageFormat format = new MessageFormat(s);
			return format.format(params);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "字符串合成失败", e);
		}
		return null;
	}
	
	private WiniUtil(){}
}

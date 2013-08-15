package com.wini.online.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;



public class CrawlUtil {
		
	private static final Logger LOGGER = Logger.getLogger(CrawlUtil.class.getName());
	public static JSONArray jsonArray;
	
	static{
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = CrawlUtil.class.getClassLoader().getResourceAsStream("crawl.json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String line = reader.readLine();
			while(line != null){
				sb.append(line);
				line = reader.readLine();
			}
			jsonArray = new JSONArray(sb.toString());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"解析json文件出错", e);
		}
		
	}
	
	public static JSONArray getJsonArray() throws Exception{
		return jsonArray;
	}
	
	private CrawlUtil(){}
}

package com.wini.online.util;

import java.util.List;

import com.baidu.bae.api.factory.BaeFactory;
import com.baidu.bae.api.fetchurl.BaeFetchurl;
import com.baidu.bae.api.fetchurl.NameValuePair;

public class FetchUtil {
	
	private static BaeFetchurl fetch = BaeFactory.getBaeFetchurl();

	static{
		// 设置自定义头部
		fetch.setHeader("Expect", "");
		// 打开允许重定向开关,默认
		fetch.setAllowRedirect(true);
		// 设置最大重定向次数
		fetch.setRedirectNum(2);
		// 设置cookie
		fetch.setCookie("timestamp", "1335583487854");
	}

	/**
	 * 发起get请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		String content = fetch.get(url);
		return content;
	}

	/**
	 * 发起post请求
	 * @param params
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String post(List<NameValuePair> params, String url) throws Exception {
		// 设置post参数，实际post的内容
		fetch.setPostData(params);
		// 发起一次posx`t请求
		String content = fetch.post(url);
		return content;
	}
	
	private FetchUtil() {}
}

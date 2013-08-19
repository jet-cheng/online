package com.wini.online.processor;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;

import com.wini.online.util.Sha1Util;

@RequestProcessor
public class WeixinProcessor  {
	
	private static final Logger LOGGER = Logger.getLogger(WeixinProcessor.class.getName());
	
	@RequestProcessing(value = "/weixin.wini", method = HTTPRequestMethod.GET)
	public void get(final HTTPRequestContext context) throws Exception {
		LOGGER.log(Level.INFO,"正在签名公众平台");
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		LOGGER.info("微信公众平台相关参数[signature:"+signature+";timestamp:"+timestamp+";nonce:"+nonce+";echostr:"+echostr+"]");
		String[] params = new String[]{timestamp,nonce,echostr};
		Arrays.sort(params);
		String sign = "";
		for(String param : params){
			sign = sign + param;
		}
		sign = Sha1Util.hex_sha1(sign);
		LOGGER.info("签名密码:" + sign);
		if(sign.equals(signature)){
			response.getWriter().write(echostr);
			response.getWriter().close();
		}
	}
	
	@RequestProcessing(value = "/weixin.wini", method = HTTPRequestMethod.POST)
	public void post(final HTTPRequestContext context) throws Exception{
		LOGGER.log(Level.INFO,"处理公众平台信息");
	}
	
	
}

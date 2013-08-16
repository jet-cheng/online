package com.wini.online;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;

import org.b3log.latke.ioc.LatkeBeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.servlet.AbstractServletListener;

import com.wini.online.service.IInitService;
import com.wini.online.service.impl.InitServiceImpl;


public class WinionlineListener extends AbstractServletListener {
	
	private static final Logger LOGGER = Logger.getLogger(WinionlineListener.class.getName());
	
	 private LatkeBeanManager beanManager;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		beanManager = Lifecycle.getBeanManager();
		try {
			IInitService initService = (IInitService) beanManager.getReference(InitServiceImpl.class);
			initService.initWinionlie();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"初始化wininonline用户失败",e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		super.contextDestroyed(servletContextEvent);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
		super.requestDestroyed(servletRequestEvent);
	}

	@Override
	public void requestInitialized(ServletRequestEvent servletRequestEvent) {
		
	}

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		
	}
}

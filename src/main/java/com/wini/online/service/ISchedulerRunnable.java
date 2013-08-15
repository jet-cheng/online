package com.wini.online.service;

import java.util.Map;

public interface ISchedulerRunnable {
	public void crawl(String url, String crawlEnd) throws Exception;

	public Map<String, Object> crawlContent(String url, String crawlEnd) throws Exception;
}

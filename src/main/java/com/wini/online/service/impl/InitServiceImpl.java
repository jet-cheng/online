package com.wini.online.service.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.b3log.latke.Keys;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.UserRepository;
import org.json.JSONObject;

import com.wini.online.constant.Constant;
import com.wini.online.service.IInitService;

@Service
public class InitServiceImpl implements IInitService {
	
	private static final Logger LOGGER = Logger.getLogger(IInitService.class.getName());
	
	@Inject
	private UserRepository userRepository;
	
	
	public void initWinionlie() throws Exception{
		final Transaction transaction = userRepository.beginTransaction();
		JSONObject user = userRepository.getByEmail(Constant.WINI_ONLINE_USER_EMAIL);
		if(user == null){
			user = new JSONObject();
			user.put(User.USER_EMAIL, Constant.WINI_ONLINE_USER_EMAIL);
			user.put(User.USER_NAME, Constant.WINI_ONLINE_USER_NAME);
			user.put(User.USER_URL, Constant.WINI_ONLINE_USER_URL);
			user.put(User.USER_PASSWORD, Constant.WINI_ONLINE_USER_PASSWORD);
			user.put(User.USER_ROLE, Constant.WINI_ONLINE_USER_ROLE);
			user.put(UserExt.USER_ARTICLE_COUNT, Constant.WINI_ONLINE_USER_ARTICLE_COUNT);
			user.put(UserExt.USER_PUBLISHED_ARTICLE_COUNT, Constant.WINI_ONLINE_USER_PUBLISHED_ARTICLE_COUNT);
			user.put(User.USER_ROLE, Constant.WINI_ONLINE_USER_ROLE);
			transaction.clearQueryCache(false);
			String userId = userRepository.add(user);
			LOGGER.info("winionline 用户 添加成功,id=[" + userId + "]");
			transaction.commit();
		}else{
			LOGGER.info("winionline 用户已经存在,id=[" + user.getString(Keys.OBJECT_ID) + "]");
		}
	}
	
	
}
package com.wini.online.constant;

import org.b3log.latke.model.Role;
import org.b3log.latke.util.MD5;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wini.online.util.FetchUtil;
import com.wini.online.util.WiniUtil;

public class Constant {

	public static final String WINI_MONGODB_NOBAE_SERVER = "wini.mongodb.nobae.server";
	public static final String WINI_MONGODB_NOBAE_PORT = "wini.mongodb.nobae.port";
	public static final String WINI_MONGODB_NOBAE_USER = "wini.mongodb.nobae.user";
	public static final String WINI_MONGODB_NOBAE_PASSWORD = "wini.mongodb.nobae.password";
	public static final String WINI_MONGODB_DB = "wini.mongodb.db";
	public static final String WINI_MONGODB_COL_HUXIU = "wini.mongodb.col.huxiu";

	public static final String WINI_MYSQL_URL = "wini.mysql.url";
	public static final String WINI_MYSQL_USER = "wini.mysql.user";
	public static final String WINI_MYSQL_PASSWORD = "wini.mysql.password";
	public static final String WINI_MYSQL_DRIVE = "wini.mysql.drive";

	public static final String WINI_ONLINE_USER_NAME = WiniUtil.get("wini.online.user.name");
	public static final String WINI_ONLINE_USER_EMAIL = WiniUtil.get("wini.online.user.email");
	public static final String WINI_ONLINE_USER_URL = WiniUtil.get("wini.online.user.url");
	public static final String WINI_ONLINE_USER_PASSWORD = MD5.hash("admin");
	public static final String WINI_ONLINE_USER_ROLE = Role.ADMIN_ROLE;
	public static final int WINI_ONLINE_USER_ARTICLE_COUNT = 0;
	public static final int WINI_ONLINE_USER_PUBLISHED_ARTICLE_COUNT = 0;
	
	
	public static final String WINI_ONLINE_ARTICLE_ABSTRACT = "wini.online.article.abstract";

	public static void main(String[] args) {
		
		try {
			String content = FetchUtil.get("http://www.huxiu.com/tags/374/3.html");
			Document doc = Jsoup.parse(content);
			Elements dd = doc.select("div[class~=tags-nr-box] div[class~=tags-nr-dl] dl>dd");
			for(Element d : dd){
				Element abstractP = d.select("p").get(1);
				abstractP.select("a").remove();
				System.out.println(abstractP);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

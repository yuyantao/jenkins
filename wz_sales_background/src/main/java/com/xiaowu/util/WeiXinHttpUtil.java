package com.xiaowu.util;

import com.jfinal.kit.PropKit;
import com.xiaoleilu.hutool.http.HttpUtil;

public class WeiXinHttpUtil {

	
	static String appid = PropKit.get("appId");
	
	static String secret = PropKit.get("appSecret");
	
	//code 换取 session_key
	static String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
	
	/**
	 * 获取OpenId
	 * @param code
	 * @return
	 */
	public static String getOpenId(String code){
		
		url = url.replace("APPID", appid).replace("SECRET", secret).replace("JSCODE", code);
		
		return HttpUtil.get(url);
	}
	
}

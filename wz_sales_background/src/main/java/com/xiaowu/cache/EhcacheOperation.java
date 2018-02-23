package com.xiaowu.cache;

import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Ehcache操作
 * @author WH
 *
 */
public class EhcacheOperation {

	public static String ehcacheOperationType = "sampleCache1";
	
	
	public static void put(String key,Object content){
		CacheKit.put(ehcacheOperationType, key, content);
	}
	
	public static <T> T get(String key){
		return CacheKit.get(ehcacheOperationType, key);
	}
	
}

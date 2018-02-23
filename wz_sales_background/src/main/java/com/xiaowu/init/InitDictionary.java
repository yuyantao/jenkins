package com.xiaowu.init;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xiaowu.cache.EhcacheOperation;

/***
 * 初始化字典
 * @author WH
 *
 */
public class InitDictionary {

	public void initData(){
		List<Record> list = Db.find("select GROUP_CONCAT(dict_name,'-',dict_value) content,dict_type from dictionary group by dict_type ");
		for (Record record : list) {
			String[] content = record.getStr("content").split(",");
			String type = record.getStr("dict_type");
			if(content.length==1){
				
				String[] singleContent = content[0].split("-");
				EhcacheOperation.put(type, singleContent[1]);
				
			}else{
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < content.length; i++) {
					String[] conStr = content[i].split("-");
					JSONObject json = new JSONObject();
					json.put(conStr[0],conStr[1]);
					jsonArray.add(json);
				}
				EhcacheOperation.put(type, jsonArray);
			}
		}
	}
	
}

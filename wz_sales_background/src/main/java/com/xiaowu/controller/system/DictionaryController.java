package com.xiaowu.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.cache.EhcacheOperation;
import com.xiaowu.common.config.StaticParameters;
import com.xiaowu.common.model.Dictionary;
import com.xiaowu.common.model.Product;
import com.xiaowu.init.InitDictionary;

/**
 * 字典管理
 * @author WH
 *
 */
public class DictionaryController extends Controller{
	
	public void index(){
		render("dictionary.jsp");
	}
	
	public void list(){
		Integer pageNumber = getParaToInt("page");
		if(pageNumber==null){
			pageNumber = 1;
		}
		String where = " 1 = 1 ";
		String dictName = getPara("dictName");
		if(StrUtil.isNotBlank(dictName)){
			where += " and dict_name like '%"+dictName+"%' ";
		}
		String dictType = getPara("dictType");
		if(StrUtil.isNotBlank(dictType)){
			where += " and dict_type like '%"+dictType+"%' ";
		}
		
		Page<Dictionary> page= Dictionary.dao.paginate(pageNumber, StaticParameters.pageSize, "select * ", " from dictionary where "+where+"  ");
		setAttr("pageInfo", page);
		render("dictionary.jsp");
	}
	
	
	/**
	 * 刷新字典缓存
	 */
	public void refreshEcache(){
		new InitDictionary().initData();
		renderText("ok");
	}
	
	/**
	 * 查询缓存信息
	 */
	public void queryEcacheInfo(){
		String dictType = getPara("dictType");
		Object object = EhcacheOperation.get(dictType);
		renderText(JSONObject.toJSONString(object));
	}
	
}

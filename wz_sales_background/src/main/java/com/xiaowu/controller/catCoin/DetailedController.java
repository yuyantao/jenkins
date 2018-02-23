package com.xiaowu.controller.catCoin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.ext.render.csv.CsvRender;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;

/**
 * 猫币详细统计 - 根据13项猫币统计各猫币的情况
 * @author WH
 *
 */
public class DetailedController extends Controller{

	public void index(){
		//今天
		String toDay = DateUtil.today();
		setAttr("toDay", toDay+" 17:30:00");
		//昨天
		String yesterDay = DateUtil.formatDate(DateUtil.yesterday());
		setAttr("yesterday", yesterDay+" 17:30:00");
		
		List<Record> lists = Db.find("select name from product where name like 'PCGS满分熊猫%'");
		setAttr("menu", lists);
		render("detailed.jsp");
	}
	
	public void list(){
		Integer pageNumber = getParaToInt("page");

		if(pageNumber==null){
			pageNumber = 1;
		}
		String where = " 1 = 1 ";
		String startTime = getPara("startTime");
		if(StrUtil.isNotBlank(startTime)){
			where += " and create_time > '"+startTime+"' ";
		}
		String endTime = getPara("endTime");
		if(StrUtil.isNotBlank(endTime)){
			where += " and create_time <= '"+endTime+"' ";
		}
		//查询头部
		List<Record> lists = Db.find("select name from product where name like 'PCGS满分熊猫%'");
		//查询内容
		Page<Record> page = Db.paginate(pageNumber, StaticParameters.pageSize,"select * " ," from (select area,team,name,GROUP_CONCAT(common_product,'-',common_count) jg from (select area,team,name,common_product,sum(common_count) common_count from cat_coin_data where "+where+" group by area,team,name,common_product ) a group by area,team,name ) b ");
		
		List<Record> listDetailed = page.getList();
		
		//固定的位数
		int fixedDigit = 3;
		
		List<String[]> list = new ArrayList<String[]>();
		String[] str = new String[lists.size()+fixedDigit];
		Map<String,String> map = new HashMap<String,String>();
		for (int i = 0; i < listDetailed.size(); i++) {
			map = new HashMap<String,String>();
			str = new String[lists.size()+fixedDigit];
			str[0] = listDetailed.get(i).getStr("area");
			str[1] = listDetailed.get(i).getStr("team");
			str[2] = listDetailed.get(i).getStr("name");
			
			String jg = listDetailed.get(i).getStr("jg");
			String[] jgSplit = jg.split(",");
			for (int j = 0; j < jgSplit.length; j++) {
				String keyValue = jgSplit[j];
				map.put(keyValue.split("-")[0], keyValue.split("-")[1]);
			}
			
			for (int j = 0; j < lists.size(); j++) {
				if(map.get(lists.get(j).getStr("name"))!= null){
					str[fixedDigit+j] = map.get(lists.get(j).getStr("name"));
				}else{
					str[fixedDigit+j] = "0";
				}
			}
			list.add(str);
		}
		setAttr("pageInfo", page);
		setAttr("list", list);
		render("detailed.jsp");
	}

	
	/**
	 * 导出CSV
	 */
	public void downlodCSV(){
		String where = " 1 = 1 ";
		String startTime = getPara("startTime");
		if(StrUtil.isNotBlank(startTime)){
			where += " and create_time > '"+startTime+"' ";
		}
		String endTime = getPara("endTime");
		if(StrUtil.isNotBlank(endTime)){
			where += " and create_time <= '"+endTime+"' ";
		}
		//查询头部
		List<Record> lists = Db.find("select name from product where name like 'PCGS满分熊猫%'");
		//查询内容
		List<Record> listDetailed = Db.find("select *  from (select area,team,name,GROUP_CONCAT(common_product,'-',common_count) jg from (select area,team,name,common_product,sum(common_count) common_count from cat_coin_data where "+where+" group by area,team,name,common_product ) a group by area,team,name ) b ");
		
		//固定的位数
		int fixedDigit = 3;
		
		List<String[]> list = new ArrayList<String[]>();
		String[] str = new String[lists.size()+fixedDigit];
		Map<String,String> map = new HashMap<String,String>();
		for (int i = 0; i < listDetailed.size(); i++) {
			map = new HashMap<String,String>();
			str = new String[lists.size()+fixedDigit];
			str[0] = listDetailed.get(i).getStr("area");
			str[1] = listDetailed.get(i).getStr("team");
			str[2] = listDetailed.get(i).getStr("name");
			
			String jg = listDetailed.get(i).getStr("jg");
			String[] jgSplit = jg.split(",");
			for (int j = 0; j < jgSplit.length; j++) {
				String keyValue = jgSplit[j];
				map.put(keyValue.split("-")[0], keyValue.split("-")[1]);
			}
			
			for (int j = 0; j < lists.size(); j++) {
				if(map.get(lists.get(j).getStr("name"))!= null){
					str[fixedDigit+j] = map.get(lists.get(j).getStr("name"));
				}else{
					str[fixedDigit+j] = "0";
				}
			}
			list.add(str);
		}
		
		
		List<String> headers = new ArrayList<String>();
		headers.add("战区");
		headers.add("战队");
		headers.add("姓名");
		for (int i = 0; i < lists.size(); i++) {
			headers.add(lists.get(i).getStr("name"));
		}

		//CsvRender csvRender = CsvRender.me(headers, data);
		List<String> columns = new ArrayList<String>();
       /* columns.add("area");
        columns.add("team");
        columns.add("leader");
        columns.add("commonProduct");
        columns.add("commonCount");*/
        
		render(CsvRender.me(headers, list).fileName("teamRanking"+System.currentTimeMillis()+".csv"));
	}
	
}

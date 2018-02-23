package com.xiaowu.controller.catCoin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
 * 区域排行
 * @author WH
 *
 */
public class AreaRankingController extends Controller{


	public void index(){
		//今天
		String toDay = DateUtil.today();
		setAttr("toDay", toDay+" 17:30:00");
		//昨天
		String yesterDay = DateUtil.formatDate(DateUtil.yesterday());
		setAttr("yesterday", yesterDay+" 17:30:00");
		render("areaRanking.jsp");
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
		Page<Record> page = Db.paginate(pageNumber, StaticParameters.pageSize,"select a.name area,IFNULL(b.commonCount,0) commonCount  " ," from team a left join (select area,sum(pandaNumber) commonCount  from cat_coin_data where "+where+" group by area ) b on a.name=b.area where a.level = 1 order by b.commonCount desc ");
		List<Record> list = page.getList();
		BigDecimal bigDecimal = BigDecimal.ZERO;
		for (int i = 0; i < list.size(); i++) {
			bigDecimal = bigDecimal.add(new BigDecimal(list.get(i).get("commonCount")+""));
		}
		Record r = new Record();
		r.set("area", "合计");
		r.set("commonCount", bigDecimal);
		list.add(r);
		setAttr("lists", list);
		setAttr("pageInfo", page);
		
		render("areaRanking.jsp");
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
		List<Record> data = Db.find("select a.name area,IFNULL(b.commonCount,0) commonCount from team a left join (select area,sum(pandaNumber) commonCount from cat_coin_data where "+where+" group by area ) b on a.name=b.area where a.level = 1 order by b.commonCount desc");
		BigDecimal bigDecimal = BigDecimal.ZERO;
		for (int i = 0; i < data.size(); i++) {
			bigDecimal = bigDecimal.add(new BigDecimal(data.get(i).get("commonCount")+""));
		}
		Record r = new Record();
		r.set("area", "合计");
		r.set("commonCount", bigDecimal);
		data.add(r);
		
		List<String> headers = new ArrayList<String>();
		headers.add("战区");
		headers.add("件数");

		//CsvRender csvRender = CsvRender.me(headers, data);
		List<String> columns = new ArrayList<String>();
        columns.add("area");
        columns.add("commonCount");
        
		render(CsvRender.me(headers, data).fileName("areaRanking"+System.currentTimeMillis()+".csv").clomuns(columns));
	}
	
	/**
	 * 获取图表数据
	 */
	public void getChartData(){
		List<Record> data = Db.find("select a.name area,IFNULL(b.commonCount,0) commonCount from team a left join (select area,sum(pandaNumber) commonCount from cat_coin_data  group by area ) b on a.name=b.area where a.level = 1 ");
		String[] title = new String[data.size()];
		Double[] commonCount = new Double[data.size()];
		for (int i = 0; i < data.size(); i++) {
			title[i] = data.get(i).getStr("area");
			commonCount[i] = data.get(i).getDouble("commonCount");
		}
		JSONObject json = new JSONObject();
		json.put("title",title);
		json.put("commonCount", commonCount);
		renderJson(json);
	}
	
}

package com.xiaowu.controller.salesStatistics;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.ext.render.csv.CsvRender;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;

public class BankRankingController extends Controller{



	public void index(){
		//今天
		String toDay = DateUtil.today();
		setAttr("toDay", toDay+" 17:30:00");
		//昨天
		String yesterDay = DateUtil.formatDate(DateUtil.yesterday());
		setAttr("yesterday", yesterDay+" 17:30:00");
		render("bankRanking.jsp");
	}
	
	public void list(){
		Integer pageNumber = getParaToInt("page");
		if(pageNumber==null){
			pageNumber = 1;
		}
		String where = " status = 1 ";
		String startTime = getPara("startTime");
		if(StrUtil.isNotBlank(startTime)){
			where += " and create_time > '"+startTime+"' ";
		}
		String endTime = getPara("endTime");
		if(StrUtil.isNotBlank(endTime)){
			where += " and create_time <= '"+endTime+"' ";
		}
		Page<Record> page = Db.paginate(pageNumber, StaticParameters.pageSize,"select area,bank,sum(common_sales_volume)count " ," from common_info where "+where+" group by area,bank order by sum(common_sales_volume) desc");
		setAttr("pageInfo", page);
		
		render("bankRanking.jsp");
	}
	
	/**
	 * 导出CSV
	 */
	public void downlodCSV(){
		String where = "status = 1  ";
		String startTime = getPara("startTime");
		if(StrUtil.isNotBlank(startTime)){
			where += " and create_time > '"+startTime+"' ";
		}
		String endTime = getPara("endTime");
		if(StrUtil.isNotBlank(endTime)){
			where += " and create_time <= '"+endTime+"' ";
		}
		List<Record> data = Db.find("select area,bank,sum(common_sales_volume)count from common_info where "+where+" group by area,bank order by sum(common_sales_volume) desc " );
		
		List<String> headers = new ArrayList<String>();
		headers.add("战区");
		headers.add("银行");
		headers.add("销量");

		//CsvRender csvRender = CsvRender.me(headers, data);
		List<String> columns = new ArrayList<String>();
        columns.add("area");
        columns.add("bank");
        columns.add("count");
        
		render(CsvRender.me(headers, data).fileName("bankRanking"+System.currentTimeMillis()+".csv").clomuns(columns));
	}
	
}

package com.xiaowu.controller.catCoin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.ext.render.csv.CsvRender;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.xiaoleilu.hutool.date.DateTime;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;

/**
 * 个人排名
 * @author WH
 *
 */
public class IndividualRankingController extends Controller{

	public void index(){
		//今天
		String toDay = DateUtil.today();
		setAttr("toDay", toDay+" 17:30:00");
		//昨天
		String yesterDay = DateUtil.formatDate(DateUtil.yesterday());
		setAttr("yesterday", yesterDay+" 17:30:00");
		render("individualRanking.jsp");
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
		Page<Record> page = Db.paginate(pageNumber, StaticParameters.pageSize,"select area,team,name,bank,'2017PCGS满分熊猫金币' commonProduct ,sum(number*common_count) commonCount  " ," from cat_coin_data where "+where+" group by area,team,name,bank order by sum(number*common_count) desc ");
		setAttr("pageInfo", page);
		
		render("individualRanking.jsp");
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
		System.out.println(where);
		List<Record> data = Db.find("select area,team,name,bank,'2017PCGS满分熊猫金币' commonProduct ,sum(number*common_count) commonCount  from cat_coin_data where "+where+" group by area,team,name,bank order by sum(number*common_count) desc " );
		
		List<String> headers = new ArrayList<String>();
		headers.add("战区");
		headers.add("战队");
		headers.add("姓名");
		headers.add("银行");
		headers.add("产品");
		headers.add("件数");

		//CsvRender csvRender = CsvRender.me(headers, data);
		List<String> columns = new ArrayList<String>();
        columns.add("area");
        columns.add("team");
        columns.add("name");
        columns.add("bank");
        columns.add("commonProduct");
        columns.add("commonCount");
        
		render(CsvRender.me(headers, data).fileName("individualRanking"+System.currentTimeMillis()+".csv").clomuns(columns));
	}
	
}

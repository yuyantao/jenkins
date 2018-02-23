package com.xiaowu.controller.catCoin;

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

/**
 * 战队排名
 * @author WH
 *
 */
public class TeamRankingController extends Controller{


	public void index(){
		//今天
		String toDay = DateUtil.today();
		setAttr("toDay", toDay+" 17:30:00");
		//昨天
		String yesterDay = DateUtil.formatDate(DateUtil.yesterday());
		setAttr("yesterday", yesterDay+" 17:30:00");
		render("teamRanking.jsp");
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
		Page<Record> page = Db.paginate(pageNumber, StaticParameters.pageSize,"select a.warZone area,a.name team,a.leader,'2017PCGS满分熊猫金币' commonProduct,IFNULL(b.pandaNumber,0) commonCount  " ," from team_all_data a left join (select area,team,sum(pandaNumber) pandaNumber from cat_coin_data where "+where+" group by area,team) b on a.name=b.team order by b.pandaNumber desc ");
		setAttr("pageInfo", page);
		
		render("teamRanking.jsp");
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
		List<Record> data = Db.find("select a.warZone area,a.name team,a.leader,'2017PCGS满分熊猫金币' commonProduct,IFNULL(b.pandaNumber,0) commonCount from team_all_data a left join (select area,team,sum(pandaNumber) pandaNumber from cat_coin_data where "+where+" group by area,team) b on a.name=b.team order by b.pandaNumber desc ");
		
		List<String> headers = new ArrayList<String>();
		headers.add("战区");
		headers.add("战队");
		headers.add("队长名");
		headers.add("产品");
		headers.add("件数");

		//CsvRender csvRender = CsvRender.me(headers, data);
		List<String> columns = new ArrayList<String>();
        columns.add("area");
        columns.add("team");
        columns.add("leader");
        columns.add("commonProduct");
        columns.add("commonCount");
        
		render(CsvRender.me(headers, data).fileName("teamRanking"+System.currentTimeMillis()+".csv").clomuns(columns));
	}
	
}

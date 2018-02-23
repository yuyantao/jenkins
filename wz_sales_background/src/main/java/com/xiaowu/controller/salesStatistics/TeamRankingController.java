package com.xiaowu.controller.salesStatistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.nio.cs.ext.Big5;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.ext.render.csv.CsvRender;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xiaoleilu.hutool.date.DateTime;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.model.Team;

/**
 * 战队排名
 * @author WH
 *
 */
public class TeamRankingController extends Controller{


	public static void main(String[] args) {
		//今天
		String toDay = DateUtil.today();
		//上月最后一天
		String lastMonthLastDay = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.beginOfMonth(DateUtil.date()),-1));
		
		System.out.println(lastMonthLastDay);
	}
	
	public void index(){
		//今天
		String toDay = DateUtil.today();
		setAttr("toDay", toDay+" 17:30:00");
		
		//昨天
		String yesterday = DateUtil.formatDate(DateUtil.yesterday());
		//本月 第一天
		setAttr("yesterday", yesterday+" 17:30:00");
		
		//上月最后一天
		String lastMonthLastDay = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.beginOfMonth(DateUtil.date()),-1));
		setAttr("lastMonthLastDay", lastMonthLastDay+" 18:20:00");
		
		

		List<Team> teamList  = Team.dao.find("select * from team where level = 1 and is_del = 1");
		setAttr("teamList", teamList);
		
		render("teamRanking.jsp");
	}
	
	public void list(){
		
		String where = " status = 1  ";
		String startTime = getPara("startTime");
		if(StrUtil.isNotBlank(startTime)){
			where += " and create_time >= '"+startTime+"' ";
		}
		String endTime = getPara("endTime");
		if(StrUtil.isNotBlank(endTime)){
			where += " and create_time < '"+endTime+"' ";
		}
		String yesterdayWhere = " status = 1 ";
		String yesterdaytartTime = getPara("yesterdaytartTime");
		if(StrUtil.isNotBlank(yesterdaytartTime)){
			yesterdayWhere += " and create_time >= '"+yesterdaytartTime+"' ";
		}
		String yesterdayEndTime = getPara("yesterdayndTime");
		if(StrUtil.isNotBlank(yesterdayEndTime)){
			yesterdayWhere += " and create_time < '"+yesterdayEndTime+"' ";
		}
		if(StrUtil.isBlank(startTime)||StrUtil.isBlank(endTime)||StrUtil.isBlank(yesterdaytartTime)||StrUtil.isBlank(yesterdayEndTime)){
			render("teamRanking.jsp");
			return;
		}
		
		List<Object[]> l = new ArrayList<Object[]>();
		if(StrUtil.isNotBlank(endTime)){
			//where += " and create_time <= '"+endTime+"' ";
			DateTime dateTime = DateUtil.parseDate(endTime);
			//昨天
			String yesterday = DateUtil.formatDate(DateUtil.offsetDay(dateTime, -1));
			
			//今天的数据是  截止到今天17:30
			//昨天的数据是  截至到昨天17:30
		
			//昨天SQL
			String yesterdaySql = "select a.warZone area,a.name team,a.leader,IFNULL(b.total,0) total from team_all_data a left join (select area,team,sum(common_sales_volume) total from common_info where "+yesterdayWhere+"  group by area,team  ) b on a.warZone = b.area and a.name = b.team  where a.is_del = 1 order by b.total desc ";
			List<Record> yesterdayList = Db.find(yesterdaySql);
			Map<String,Integer> map = new HashMap<String,Integer>();
			for (int i = 0; i < yesterdayList.size(); i++) {
				Record record =  yesterdayList.get(i);
				map.put(record.getStr("area")+record.getStr("team"), i+1);
			}
			
			//今天SQL
			String todaySql = "select a.warZone area,a.name team,a.leader,IFNULL(b.total,0) total from team_all_data a left join (select area,team,sum(common_sales_volume) total from common_info where "+where+" group by area,team  ) b on a.warZone = b.area and a.name = b.team  where a.is_del = 1 order by b.total desc ";
			
			List<Team> teamList  = Team.dao.find("select name from team where level = 1 and is_del = 1");

			List<Record> list = Db.find(todaySql);
			//合计
			Object[] hj = new Object[6+teamList.size()];
			for (int i = 0; i < list.size(); i++) {
				
				Object[] s = new Object[6+teamList.size()];
				s[0] = i+1 ;
				
				Record record = list.get(i);
				record.set("ranking", i+1);
				Integer r = map.get(record.getStr("area")+record.getStr("team"));
				int bd = (i+1) - r;
				
				String changeCondition;
				if(bd==0){
					changeCondition =  "不变";
				}else if(bd>0){
					changeCondition =  "↑"+bd;
				}else{
					changeCondition =  "↓"+Math.abs(bd);
				}
				s[1] = changeCondition;
				s[2] = record.get("area");
				s[3] = record.get("team");
				s[4] = record.get("leader");
				
				int pm = 5;
				hj[pm-1] = "合计";
				for (int j = 0; j < teamList.size(); j++) {
					if(teamList.get(j).getStr("name").equals(record.get("area"))){
						s[pm+j] = record.getBigDecimal("total");
					}else{
						s[pm+j] = "";
					}
					//System.out.println(s[pm+j]);
					if(!"".equals(s[pm+j])){
						if(null==hj[pm+j]){
							hj[pm+j] = 0;
						}
						hj[pm+j] = new BigDecimal(s[pm+j]+"").add(new BigDecimal(hj[pm+j]+"")) ; 
					}
					
				}
				s[pm+teamList.size()] = record.getBigDecimal("total");
				if(null==hj[pm+teamList.size()]){
					hj[pm+teamList.size()] = 0;
				}
				hj[pm+teamList.size()] = new BigDecimal(hj[pm+teamList.size()]+"").add(new BigDecimal(s[pm+teamList.size()]+"")) ; 
				l.add(s);
			}
			l.add(hj);
		}
		setAttr("list",l);
		render("teamRanking.jsp");
	}
	
	/**
	 * 导出CSV
	 */
	public void downlodCSV(){
		String where = " status = 1  ";
		String startTime = getPara("startTime");
		if(StrUtil.isNotBlank(startTime)){
			where += " and create_time >= '"+startTime+"' ";
		}
		String endTime = getPara("endTime");
		if(StrUtil.isNotBlank(endTime)){
			where += " and create_time < '"+endTime+"' ";
		}
		String yesterdayWhere = " status = 1 ";
		String yesterdaytartTime = getPara("yesterdaytartTime");
		if(StrUtil.isNotBlank(yesterdaytartTime)){
			yesterdayWhere += " and create_time >= '"+yesterdaytartTime+"' ";
		}
		String yesterdayEndTime = getPara("yesterdayndTime");
		if(StrUtil.isNotBlank(yesterdayEndTime)){
			yesterdayWhere += " and create_time < '"+yesterdayEndTime+"' ";
		}
		if(StrUtil.isBlank(startTime)||StrUtil.isBlank(endTime)||StrUtil.isBlank(yesterdaytartTime)||StrUtil.isBlank(yesterdayEndTime)){
			return;
		}
		
		List<Object[]> l = new ArrayList<Object[]>();
		
		List<Team> teamList  = Team.dao.find("select name from team where level = 1 and is_del = 1");

		if(StrUtil.isNotBlank(endTime)){
			//where += " and create_time <= '"+endTime+"' ";
			DateTime dateTime = DateUtil.parseDate(endTime);
			//昨天
			String yesterday = DateUtil.formatDate(DateUtil.offsetDay(dateTime, -1));
			
			//今天的数据是  截止到今天17:30
			//昨天的数据是  截至到昨天17:30
		
			//昨天SQL
			String yesterdaySql = "select a.warZone area,a.name team,a.leader,IFNULL(b.total,0) total from team_all_data a left join (select area,team,sum(common_sales_volume) total from common_info where "+yesterdayWhere+"  group by area,team  ) b on a.warZone = b.area and a.name = b.team  where a.is_del = 1 order by b.total desc ";
			List<Record> yesterdayList = Db.find(yesterdaySql);
			Map<String,Integer> map = new HashMap<String,Integer>();
			for (int i = 0; i < yesterdayList.size(); i++) {
				Record record =  yesterdayList.get(i);
				map.put(record.getStr("area")+record.getStr("team"), i+1);
			}
			
			//今天SQL
			String todaySql = "select a.warZone area,a.name team,a.leader,IFNULL(b.total,0) total from team_all_data a left join (select area,team,sum(common_sales_volume) total from common_info where "+where+"  group by area,team  ) b on a.warZone = b.area and a.name = b.team  where a.is_del = 1 order by b.total desc ";
		
			
			List<Record> list = Db.find(todaySql);
			//合计
			Object[] hj = new Object[6+teamList.size()];
			for (int i = 0; i < list.size(); i++) {
				
				Object[] s = new Object[6+teamList.size()];
				s[0] = i+1 ;
				
				Record record = list.get(i);
				record.set("ranking", i+1);
				Integer r = map.get(record.getStr("area")+record.getStr("team"));
				int bd = (i+1) - r;
				
				String changeCondition;
				if(bd==0){
					changeCondition =  "不变";
				}else if(bd>0){
					changeCondition =  "↑"+bd;
				}else{
					changeCondition =  "↓"+Math.abs(bd);
				}
				s[1] = changeCondition;
				s[2] = record.get("area");
				s[3] = record.get("team");
				s[4] = record.get("leader");
				
				int pm = 5;
				hj[pm-1] = "合计";
				for (int j = 0; j < teamList.size(); j++) {
					if(teamList.get(j).getStr("name").equals(record.get("area"))){
						s[pm+j] = record.getBigDecimal("total");
					}else{
						s[pm+j] = "";
					}
					//System.out.println(s[pm+j]);
					if(!"".equals(s[pm+j])){
						if(null==hj[pm+j]){
							hj[pm+j] = 0;
						}
						hj[pm+j] = new BigDecimal(s[pm+j]+"").add(new BigDecimal(hj[pm+j]+"")) ; 
					}
					
				}
				s[pm+teamList.size()] = record.getBigDecimal("total");
				if(null==hj[pm+teamList.size()]){
					hj[pm+teamList.size()] = 0;
				}
				hj[pm+teamList.size()] = new BigDecimal(hj[pm+teamList.size()]+"").add(new BigDecimal(s[pm+teamList.size()]+"")) ; 
				l.add(s);
			}
			l.add(hj);
		}
		List<String> headers = new ArrayList<String>();
		headers.add("排名");
		headers.add("排名变动情况");
		headers.add("战区");
		headers.add("战队");
		headers.add("队长");
		for (int j = 0; j < teamList.size(); j++) {
			headers.add(teamList.get(j).getStr("name")+"");
		}
		headers.add("总计");
        
		render(CsvRender.me(headers, l).fileName("teamRanking"+System.currentTimeMillis()+".csv"));
	}
	
}

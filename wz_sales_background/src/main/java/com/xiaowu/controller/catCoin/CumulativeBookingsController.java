package com.xiaowu.controller.catCoin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.ext.render.csv.CsvRender;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;
import com.xiaowu.common.model.CumulativeBookings;

/**
 * 累计预定量
 * @author WH
 *
 */
public class CumulativeBookingsController extends Controller{

	
	public void index(){
		render("cumulativeBookings.jsp");
	}
	
	
	public void list(){
	
		String where = " 1 = 1 ";
		String team = getPara("team");
		if(StrUtil.isNotBlank(team)){
			where += " and team = '"+team+"' ";
		}
		
		List<CumulativeBookings> list = CumulativeBookings.dao.find("select * from cumulative_bookings where "+where+" ");
		
		//查询所有大于2017-07-17 17:30:00 的所有数据
		List<Record> listRecord = Db.find("select team,common_product,sum(common_count) count from cat_coin_data where create_time > '2017-07-17 17:30:00' group by team,common_product");
		Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
		for (int i = 0; i < listRecord.size(); i++) {
			Record record = listRecord.get(i);
			map.put(record.getStr("team")+record.getStr("common_product"), record.getBigDecimal("count"));
		}
		
		CumulativeBookings cumulative = new CumulativeBookings();
		cumulative.setYqnymzh(BigDecimal.ZERO);
		cumulative.setYqnjmslhjyth(BigDecimal.ZERO);
		cumulative.setYmslwmdt(BigDecimal.ZERO);
		cumulative.setYmslxxz(BigDecimal.ZERO);
		cumulative.setYmsldxz(BigDecimal.ZERO);
		cumulative.setYqnjmsl(BigDecimal.ZERO);
		cumulative.setYjydjmsljyth(BigDecimal.ZERO);
		cumulative.setYlnjmsl(BigDecimal.ZERO);
		for (CumulativeBookings cumulativeBookings : list) {
			cumulativeBookings = calculateTotal(cumulativeBookings, map);
			cumulative.setYqnymzh(cumulative.getYqnymzh().add(cumulativeBookings.getYqnymzh()));
			cumulative.setYqnjmslhjyth(cumulative.getYqnjmslhjyth().add(cumulativeBookings.getYqnjmslhjyth()));
			cumulative.setYmslwmdt(cumulative.getYmslwmdt().add(cumulativeBookings.getYmslwmdt()));
			cumulative.setYmslxxz(cumulative.getYmslxxz().add(cumulativeBookings.getYmslxxz()));
			cumulative.setYmsldxz(cumulative.getYmsldxz().add(cumulativeBookings.getYmsldxz()));
			cumulative.setYqnjmsl(cumulative.getYqnjmsl().add(cumulativeBookings.getYqnjmsl()));
			cumulative.setYjydjmsljyth(cumulative.getYjydjmsljyth().add(cumulativeBookings.getYjydjmsljyth()));
			cumulative.setYlnjmsl(cumulative.getYlnjmsl().add(cumulativeBookings.getYlnjmsl()));
		}
		cumulative.setBank("合计");
		list.add(cumulative);
		
		setAttr("list", list);
		render("cumulativeBookings.jsp");
	}
	
	/**
	 * 导出CSV
	 */
	public void downlodCSV(){
		String where = " 1 = 1 ";
		String team = getPara("team");
		if(StrUtil.isNotBlank(team)){
			where += " and team = '"+team+"' ";
		}
		
		List<CumulativeBookings> list = CumulativeBookings.dao.find("select * from cumulative_bookings where "+where+" ");
		
		//查询所有大于2017-07-17 17:30:00 的所有数据
		List<Record> listRecord = Db.find("select team,common_product,sum(common_count) count from cat_coin_data where create_time > '2017-07-17 17:30:00' group by team,common_product");
		Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
		for (int i = 0; i < listRecord.size(); i++) {
			Record record = listRecord.get(i);
			map.put(record.getStr("team")+record.getStr("common_product"), record.getBigDecimal("count"));
		}
		
		CumulativeBookings cumulative = new CumulativeBookings();
		cumulative.setYqnymzh(BigDecimal.ZERO);
		cumulative.setYqnjmslhjyth(BigDecimal.ZERO);
		cumulative.setYmslwmdt(BigDecimal.ZERO);
		cumulative.setYmslxxz(BigDecimal.ZERO);
		cumulative.setYmsldxz(BigDecimal.ZERO);
		cumulative.setYqnjmsl(BigDecimal.ZERO);
		cumulative.setYjydjmsljyth(BigDecimal.ZERO);
		cumulative.setYlnjmsl(BigDecimal.ZERO);
		for (CumulativeBookings cumulativeBookings : list) {
			cumulativeBookings = calculateTotal(cumulativeBookings, map);
			cumulative.setYqnymzh(cumulative.getYqnymzh().add(cumulativeBookings.getYqnymzh()));
			cumulative.setYqnjmslhjyth(cumulative.getYqnjmslhjyth().add(cumulativeBookings.getYqnjmslhjyth()));
			cumulative.setYmslwmdt(cumulative.getYmslwmdt().add(cumulativeBookings.getYmslwmdt()));
			cumulative.setYmslxxz(cumulative.getYmslxxz().add(cumulativeBookings.getYmslxxz()));
			cumulative.setYmsldxz(cumulative.getYmsldxz().add(cumulativeBookings.getYmsldxz()));
			cumulative.setYqnjmsl(cumulative.getYqnjmsl().add(cumulativeBookings.getYqnjmsl()));
			cumulative.setYjydjmsljyth(cumulative.getYjydjmsljyth().add(cumulativeBookings.getYjydjmsljyth()));
			cumulative.setYlnjmsl(cumulative.getYlnjmsl().add(cumulativeBookings.getYlnjmsl()));
		}
		cumulative.setBank("合计");
		list.add(cumulative);
		
		List<String> headers = new ArrayList<String>();
		headers.add("战队");
		headers.add("银行");
		headers.add("累计预定17银猫折合单套");
		headers.add("累计预订17金猫数量（含金银同号）");
		headers.add("累计预订银猫数量（5枚单套）");
		headers.add("累计预订银猫数量（小箱装）");
		headers.add("累计预订银猫数量（大箱装）");
		headers.add("累计预订17金猫数量");
		headers.add("累计预订金猫数量（金银同号）");
		headers.add("累计预订16金猫数量");

		//CsvRender csvRender = CsvRender.me(headers, data);
		List<String> columns = new ArrayList<String>();
        columns.add("team");
        columns.add("bank");
        columns.add("yqnymzh");
        columns.add("yqnjmslhjyth");
        columns.add("ymslwmdt");
        columns.add("ymslxxz");
        columns.add("ymsldxz");
        columns.add("yqnjmsl");
        columns.add("yjydjmsljyth");
        columns.add("ylnjmsl");
        
		render(CsvRender.me(headers, list).fileName("cumulativeBookings"+System.currentTimeMillis()+".csv").clomuns(columns));
	}
	
	/**
	 * 按照特定规则计算每列的数据
	 * @param cumulativeBookings
	 * @param map
	 * @return
	 */
	public CumulativeBookings calculateTotal(CumulativeBookings cumulativeBookings,Map<String,BigDecimal> map){
		//战队
		String team = cumulativeBookings.getTeam();
		
		BigDecimal b1 = getBigDecimal(map,team+"PCGS满分熊猫金币2017(单套)");
		BigDecimal b2 = getBigDecimal(map,team+"PCGS满分熊猫金币2017(五连号)");
		BigDecimal b3 = getBigDecimal(map,team+"PCGS满分熊猫金币2017(十连号)");
		BigDecimal b4 = getBigDecimal(map,team+"PCGS满分熊猫金币2017(百连号)");
		BigDecimal b5 = getBigDecimal(map,team+"PCGS满分熊猫金币2016(单套)");
		BigDecimal b6 = getBigDecimal(map,team+"PCGS满分熊猫金币2016(五连号)");
		BigDecimal b7 = getBigDecimal(map,team+"PCGS满分熊猫金币2016(十连号)");
		BigDecimal b8 = getBigDecimal(map,team+"PCGS满分熊猫银币2017(单套)");
		BigDecimal b9 = getBigDecimal(map,team+"PCGS满分熊猫银币2017(十连号)");
		BigDecimal b10 = getBigDecimal(map,team+"PCGS满分熊猫银币2017(百连号)");
		BigDecimal b11 = getBigDecimal(map,team+"PCGS满分熊猫币2017(金银同号)");
		
		//累计预定17银猫折合单套  -  pCGS满分熊猫银币2017(单套)+ PCGS满分熊猫银币2017(十连号)*10+ PCGS满分熊猫银币2017(百连号)*100+ PCGS满分熊猫币2017(金银同号)
		BigDecimal yqnymzh = cumulativeBookings.getBigDecimal("yqnymzh");
		yqnymzh = yqnymzh.add(b8).add(b9.multiply(new BigDecimal(10))).add(b10.multiply(new BigDecimal(100))).add(b11);
		cumulativeBookings.set("yqnymzh", yqnymzh);
		
		
		//累计预订银猫数量（5枚单套）- (PCGS满分熊猫银币2017(单套))
		BigDecimal ymslwmdt = cumulativeBookings.getBigDecimal("ymslwmdt");
		cumulativeBookings.set("ymslwmdt", ymslwmdt.add(b8));
		//累计预订银猫数量（小箱装） -  PCGS满分熊猫银币2017(十连号)
		BigDecimal ymslxxz = cumulativeBookings.getBigDecimal("ymslxxz");
		cumulativeBookings.set("ymslxxz", ymslxxz.add(b9));
		//累计预订银猫数量（大箱装） - PCGS满分熊猫银币2017(百连号)
		BigDecimal ymsldxz = cumulativeBookings.getBigDecimal("ymsldxz");
		cumulativeBookings.set("ymsldxz", ymsldxz.add(b10));
		//累计预订金猫数量（金银同号）  -  满分熊猫币2017(金银同号)
		BigDecimal yjydjmsljyth = cumulativeBookings.getBigDecimal("yjydjmsljyth");
		cumulativeBookings.set("yjydjmsljyth", yjydjmsljyth.add(b11));
		//累计预订17金猫数量 -  PCGS满分熊猫金币2017(单套) + PCGS满分熊猫金币2017(五连号)*5+ PCGS满分熊猫金币2017(十连号)*10+ PCGS满分熊猫金币2017(百连号)*100
		BigDecimal yqnjmsl = cumulativeBookings.getBigDecimal("yqnjmsl");
		//17年累计金猫   PCGS满分熊猫金币2017(单套) + PCGS满分熊猫金币2017(五连号)*5+ PCGS满分熊猫金币2017(十连号)*10+ PCGS满分熊猫金币2017(百连号)*100
		BigDecimal ljjm = b1.add(b2.multiply(new BigDecimal(5))).add(b3.multiply(new BigDecimal(10))).add(b4.multiply(new BigDecimal(100)));
		cumulativeBookings.set("yqnjmsl", yqnjmsl.add(ljjm));
		//累计预订17金猫数量（含金银同号） - PCGS满分熊猫金币2017(单套)+ PCGS满分熊猫金币2017(五连号)*5+ PCGS满分熊猫金币2017(十连号)*10+ PCGS满分熊猫金币2017(百连号)*100+ PCGS满分熊猫币2017(金银同号)
		BigDecimal yqnjmslhjyth = cumulativeBookings.getBigDecimal("yqnjmslhjyth");
		cumulativeBookings.set("yqnjmslhjyth", yqnjmslhjyth.add(ljjm).add(b11));

		
		//累计预订16金猫数量 -- PCGS满分熊猫金币2016(单套) + PCGS满分熊猫金币2016(五连号)*5+ PCGS满分熊猫金币2016(十连号)*10
		BigDecimal ylnjmsl = cumulativeBookings.getBigDecimal("ylnjmsl");
		ylnjmsl = ylnjmsl.add(b5).add(b6.multiply(new BigDecimal(5))).add(b7.multiply(new BigDecimal(10)));
		cumulativeBookings.set("ylnjmsl", ylnjmsl);
		return cumulativeBookings;
	}
	
	
	public BigDecimal getBigDecimal(Map<String,BigDecimal> map,String str ){
		BigDecimal bigDecimal = map.get(str);
		return bigDecimal==null?BigDecimal.ZERO:bigDecimal;
	} 
}

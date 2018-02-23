package com.xiaowu.controller.sales;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.ext.render.csv.CsvRender;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.cache.EhcacheOperation;
import com.xiaowu.common.model.BottomSingle;
import com.xiaowu.common.model.CommonInfo;

/**
 * 销量管理 - 上报销量
 * @author WH
 *
 */
public class ReportedController extends Controller{

	public void index(){
		setAttr("uploadData", EhcacheOperation.get("uploadPath"));
		render("reported.jsp");
	}
	
	/**
	 * 查询数据
	 */
	public void list(){
		Integer pageNumber = getParaToInt("page");
		if(pageNumber==null){
			pageNumber = 1;
		}
		String where = " 1 = 1 ";
		String area = getPara("area");
		if(StrUtil.isNotBlank(area)){
			where += " and area like '%"+area+"%' ";
		}
		String team = getPara("team");
		if(StrUtil.isNotBlank(team)){
			where += " and team like '%"+team+"%' ";
		}
		String name = getPara("name");
		if(StrUtil.isNotBlank(name)){
			where += " and name like '%"+name+"%' ";
		}
		String bank = getPara("bank");
		if(StrUtil.isNotBlank(bank)){
			where += " and bank like '%"+bank+"%' ";
		}
		String product = getPara("product");
		if(StrUtil.isNotBlank(product)){
			where += " and common_product like '%"+product+"%' ";
		}
		String startTime = getPara("startTime");
		if(StrUtil.isNotBlank(startTime)){
			where += " and create_time > '"+startTime+"' ";
		}
		String endTime = getPara("endTime");
		if(StrUtil.isNotBlank(endTime)){
			where += " and create_time <= '"+endTime+"' ";
		}
		Page<CommonInfo> page = CommonInfo.dao.paginate(pageNumber, 10, "select * ", " from common_info where status = 1 and "+where+"  order by create_time desc ");
		setAttr("pageInfo", page);
		render("reported.jsp");
	}
	
	
	/**
	 * 查询底单
	 */
	public void queryBottomSingle(){
		String serialNum = getPara("serialNum");
		List<BottomSingle> lists = BottomSingle.dao.find("select img_path from bottom_single where serial_num = '"+serialNum+"'");
		renderJson(lists);
	}
	
	
	
	/**
	 * 导出CSV
	 */
	public void downlodCSV(){
		String where = " 1 = 1 ";
		String area = getPara("area");
		if(StrUtil.isNotBlank(area)){
			where += " and area like '%"+area+"%' ";
		}
		String team = getPara("team");
		if(StrUtil.isNotBlank(team)){
			where += " and team like '%"+team+"%' ";
		}
		String name = getPara("name");
		if(StrUtil.isNotBlank(name)){
			where += " and name like '%"+name+"%' ";
		}
		String bank = getPara("bank");
		if(StrUtil.isNotBlank(bank)){
			where += " and bank like '%"+bank+"%' ";
		}
		String product = getPara("product");
		if(StrUtil.isNotBlank(product)){
			where += " and common_product like '%"+product+"%' ";
		}
		String startTime = getPara("startTime");
		if(StrUtil.isNotBlank(startTime)){
			where += " and create_time >= '"+startTime+"' ";
		}
		String endTime = getPara("endTime");
		if(StrUtil.isNotBlank(endTime)){
			where += " and create_time <= '"+endTime+"' ";
		}
		System.out.println(where);
		List<CommonInfo> data = CommonInfo.dao.find("select * from common_info where status = 1 and "+where+" order by create_time desc ");
		
		
		List<String> headers = new ArrayList<String>();
		headers.add("战区");
		headers.add("战队");
		headers.add("姓名");
		headers.add("银行");
		headers.add("产品");
		headers.add("件数");
		headers.add("销售额");
		headers.add("上报时间");

		//CsvRender csvRender = CsvRender.me(headers, data);
		List<String> columns = new ArrayList<String>();
        columns.add("area");
        columns.add("team");
        columns.add("name");
        columns.add("bank");
        columns.add("common_product");
        columns.add("common_count");
        columns.add("common_sales_volume");
        columns.add("create_time");
        
		render(CsvRender.me(headers, data).fileName("reported"+System.currentTimeMillis()+".csv").clomuns(columns));
	}
	
	/**
	 * 删除数据
	 */
	public void deleteData(){
		String id = getPara("id");
		int updateCount = Db.update("update common_info set status = 0,delete_time='"+DateUtil.now()+"' where status = 1 and id ='"+id+"' ");
		renderText(updateCount+"");
	}
	
}

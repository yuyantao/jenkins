package com.xiaowu.controller.system;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;
import com.xiaowu.common.model.Feedback;

/**
 * 意见反馈
 * @author WH
 *
 */
public class FeedbackController extends Controller{

	public void index(){
		render("feedback.jsp");
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
		Page<Feedback> page= Feedback.dao.paginate(pageNumber, StaticParameters.pageSize, "select * ", " from feedback where "+where+" order by create_time desc ");
		setAttr("pageInfo", page);
		render("feedback.jsp");
	}
	
}

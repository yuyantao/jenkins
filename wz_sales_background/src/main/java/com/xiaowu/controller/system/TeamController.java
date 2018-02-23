package com.xiaowu.controller.system;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;
import com.xiaowu.common.model.Bank;
import com.xiaowu.common.model.Team;

/**
 * 战队管理
 * @author WH
 *
 */
public class TeamController extends Controller{

	public void index(){
		
		List<Team> list = Team.dao.find("select * from team where level = 1 and is_del = 1 ");
		setAttr("list", list);
		render("team.jsp");
	}
	
	public void list(){
		Integer pageNumber = getParaToInt("page");
		if(pageNumber==null){
			pageNumber = 1;
		}
		String where = " level = 2  ";
		String bankName = getPara("bankName");
		if(StrUtil.isNotBlank(bankName)){
			where += " and bank_name like '%"+bankName+"%' ";
		}
		String parentCode = getPara("parentCode");
		where += " and parent_code = '"+parentCode+"' ";
		Page<Team> page= Team.dao.paginate(pageNumber, StaticParameters.pageSize, "select * ", " from team where is_del = 1 and "+where+"  ");
		setAttr("pageInfo", page);
		render("team.jsp");
	}
	
	/**
	 * 进入编辑页面
	 */
	public void editPage(){
		String id = getPara("id");
		if(StrUtil.isNotBlank(id)){
			Team team = Team.dao.findFirst("select * from team where id = '"+id+"'");
			setAttr("team", team);
		}
		render("teamEdit.jsp");
	}
	
	/**
	 * 保存数据
	 */
	public void save(){
		Team team = getModel(Team.class);
		team.setLevel(2);
		boolean bool;
		//ID不为空则为修改
		if(null!=team.getId()){
			bool = team.update();
		}else{
			bool = team.save();
		}
		if(bool){
			//银行版本号加1
			Db.update("UPDATE version set version = version + 1 where type_code = 3");
		}
		
		renderText(bool+"");
	}
	
	
	/**
	 * 删除数据
	 */
	public void deleteData(){
		String id = getPara("id");
		int updateCount = Db.update("update team set is_del = 0 where id ='"+id+"' and is_del = 1 ");
		if(updateCount==1){
			//产品版本号加1
			Db.update("UPDATE version set version = version + 1 where type_code = 3");
		}
		renderText(updateCount+"");
	}
}

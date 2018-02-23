package com.xiaowu.controller.system;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;
import com.xiaowu.common.model.Bank;
import com.xiaowu.common.model.Product;

/**
 * 银行管理
 * @author WH
 *
 */
public class BankController extends Controller{

	public void index(){
		render("bank.jsp");
	}
	
	public void list(){
		Integer pageNumber = getParaToInt("page");
		if(pageNumber==null){
			pageNumber = 1;
		}
		String where = " 1 = 1 ";
		String bankName = getPara("bankName");
		if(StrUtil.isNotBlank(bankName)){
			where += " and bank_name like '%"+bankName+"%' ";
		}
		
		Page<Product> page= Product.dao.paginate(pageNumber, StaticParameters.pageSize, "select * ", " from bank where "+where+" order by bank_name ");
		setAttr("pageInfo", page);
		render("bank.jsp");
	}
	
	/**
	 * 进入编辑页面
	 */
	public void editPage(){
		String id = getPara("id");
		if(StrUtil.isNotBlank(id)){
			Bank bank = Bank.dao.findFirst("select * from bank where id = '"+id+"'");
			setAttr("bank", bank);
		}
		render("bankEdit.jsp");
	}
	
	/**
	 * 保存数据
	 */
	public void save(){
		Bank bank = getModel(Bank.class);
		boolean bool;
		//ID不为空则为修改
		if(null!=bank.getId()){
			bool = bank.update();
		}else{
			bool = bank.save();
		}
		if(bool){
			//银行版本号加1
			Db.update("UPDATE version set version = version + 1 where type_code = 1");
		}
		
		renderText(bool+"");
	}
	
	
	/**
	 * 启用、删除数据
	 */
	public void deleteData(){
		String id = getPara("id");
		boolean bool = Bank.dao.deleteById(id);
		if(bool){
			//银行版本号加1
			Db.update("UPDATE version set version = version + 1 where type_code = 1");
		}
		renderText(bool==true?"1":"0");
	}
}

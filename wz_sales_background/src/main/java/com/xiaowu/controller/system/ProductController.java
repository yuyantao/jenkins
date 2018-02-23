package com.xiaowu.controller.system;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;
import com.xiaowu.common.model.Product;

/**
 * 产品管理
 * @author WH
 *
 */
public class ProductController extends Controller{

	public void index(){
		render("product.jsp");
	}
	
	public void list(){
		Integer pageNumber = getParaToInt("page");
		if(pageNumber==null){
			pageNumber = 1;
		}
		String where = " 1 = 1 ";
		String name = getPara("name");
		if(StrUtil.isNotBlank(name)){
			where += " and name like '%"+name+"%' ";
		}
		String isDel = getPara("isDel");
		if(StrUtil.isNotBlank(isDel)){
			where += " and is_del = "+isDel;
		}
		
		Page<Product> page= Product.dao.paginate(pageNumber, StaticParameters.pageSize, "select * ", " from product where "+where+" order by stick desc ");
		setAttr("pageInfo", page);
		render("product.jsp");
	}
	
	/**
	 * 进入编辑页面
	 */
	public void editPage(){
		String id = getPara("id");
		if(StrUtil.isNotBlank(id)){
			Product product = Product.dao.findFirst("select * from product where id = '"+id+"'");
			setAttr("product", product);
		}
		render("productEdit.jsp");
	}
	
	/**
	 * 保存数据
	 */
	public void save(){
		Product product = getModel(Product.class);
		boolean bool;
		//ID不为空则为修改
		if(null!=product.getId()){
			bool = product.update();
		}else{
			bool = product.save();
		}
		if(bool){
			//产品版本号加1
			Db.update("UPDATE version set version = version + 1 where type_code = 2");
		}
		
		renderText(bool+"");
	}
	
	
	/**
	 * 启用、删除数据
	 */
	public void deleteData(){
		String id = getPara("id");
		int updateCount = Db.update("update product set is_del = !is_del where id ='"+id+"' ");
		if(updateCount==1){
			//产品版本号加1
			Db.update("UPDATE version set version = version + 1 where type_code = 2");
		}
		renderText(updateCount+"");
	}
}

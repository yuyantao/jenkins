package com.xiaowu.controller;

import com.jfinal.core.Controller;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;
import com.xiaowu.common.model.SysUser;

/**
 * 出入口
 * @author WH
 *
 */
public class EntranceController  extends Controller{

	/**
	 * 默认入口
	 */
	public void index(){
		render("login.jsp");
	}
	
	/**
	 * 主框架
	 */
	public void main(){
		render("main.jsp");
	}
	
	/**
	 * 首页
	 */
	public void home(){
		render("home.jsp");
	}
	
	/**
	 * 登录
	 */
	public void login(){
		setAttr("state", getPara("state"));
		render("login.jsp");
	}
	
	/**
	 * 提交登录
	 */
	public void submitLogin(){
		String name = getPara("name");
		String password = getPara("password");
		if(StrUtil.isBlank(name) || StrUtil.isBlank(password) ){
			 redirect("/login?state=loginError");
			 return;
		}
		SysUser sysUser = SysUser.dao.findFirst("select * from sys_user where account ='"+name+"' and password = md5('"+password+"') ");
		if(sysUser==null){
			redirect("/login?state=loginError");
			return;
		}
		
		setSessionAttr(StaticParameters.loginInfo, sysUser.getUserName());
		redirect("/main");
	}
	
	/**
	 * 退出登录
	 */
	public void exit(){
		removeSessionAttr("loginAction");
		redirect("/login");
	}
	
	
}

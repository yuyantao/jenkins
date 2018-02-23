package com.xiaowu.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaowu.common.config.StaticParameters;
import com.xiaowu.common.model.SysLog;
import com.xiaowu.util.IpKit;


/**
 * 系统拦截器 - 拦截所有未登录
 * @author WH
 *
 */
public class SystemInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		

		

		Controller controller = inv.getController();
		HttpServletRequest request = controller.getRequest();
		String header = request.getHeader("X-Requested-With");
		//判断是否ajax请求
        boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(header);
        
		
		String actionKey = inv.getActionKey();

		//登录状态
		String loginState = inv.getController().getSessionAttr(StaticParameters.loginInfo);

        SysLog sysLog = new SysLog();
        sysLog.setControllerName(inv.getControllerKey());
        sysLog.setMethodName(inv.getMethodName());
        sysLog.setCreateTime(DateUtil.now());
        sysLog.setLoginUser(loginState);
        //登陆时不保存用户密码
        if("submitLogin".equals(sysLog.getMethodName())){
        	sysLog.setParameter(getParameterValue(request).split("&")[0]);
        }else{
        	sysLog.setParameter(getParameterValue(request));
        }
        sysLog.setIp(IpKit.getRealIpV2(request));
        //保存系统日志
        addLog(sysLog);
      
		
		//登录页面不需要权限
		if("/login".equals(actionKey) || "/submitLogin".equals(actionKey)){
			inv.invoke();
			return;
		}
		if(StrUtil.isBlank(loginState)){
			if(isAjax){
				controller.renderJson("userNotLogin");
			}else{
				//跳转login
				inv.getController().redirect("/login");	
			}
		}else{
			//继续运行
			inv.invoke();	
		}
	}

	/**
	 * 异步保存日志
	 * @param sysLog
	 */
	public void addLog(final SysLog sysLog){
		new Thread(new Runnable(){
			public void run() {
				sysLog.save();					
			}
		}).start();
	}
	

	public String getParameterValue(HttpServletRequest request){
		Map<String, String[]> parameterMap = request.getParameterMap();
		StringBuffer bfParams = new StringBuffer();
		for(String key: parameterMap.keySet()){
			String value = parameterMap.get(key)[0];
			if(StrUtil.isNotBlank(value)){
				bfParams.append(key).append("=").append(value).append("&");  
			}
		}
		return bfParams.toString();
	}
	
}

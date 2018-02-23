package com.xiaowu.common.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.xiaowu.common.model._MappingKit;
import com.xiaowu.controller.EntranceController;
import com.xiaowu.controller.catCoin.AreaRankingController;
import com.xiaowu.controller.catCoin.CumulativeBookingsController;
import com.xiaowu.controller.catCoin.DetailedController;
import com.xiaowu.controller.catCoin.EntryPrizeRankingController;
import com.xiaowu.controller.catCoin.IndividualRankingController;
import com.xiaowu.controller.catCoin.TeamRankingController;
import com.xiaowu.controller.sales.ReportedController;
import com.xiaowu.controller.salesStatistics.BankRankingController;
import com.xiaowu.controller.system.BankController;
import com.xiaowu.controller.system.DictionaryController;
import com.xiaowu.controller.system.FeedbackController;
import com.xiaowu.controller.system.ProductController;
import com.xiaowu.controller.system.TeamController;
import com.xiaowu.init.InitDictionary;
import com.xiaowu.interceptor.SystemInterceptor;

/**
 * 核心配置文件
 * @author WH
 *
 */
public class Config  extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		//静态配置文件
		PropKit.use("static.properties");

		me.setDevMode(PropKit.getBoolean("devMode", false));

		//设置默认的视图类型
		me.setViewType(ViewType.JSP);
		
		
		
		//设置文件上传保存基础路径
		me.setBaseUploadPath("upload");
	}

	@Override
	public void configRoute(Routes me) {
		
		//设置默认视图层路径
		me.setBaseViewPath("/WEB-INF/jsp");
		
		//me.add("/",IndexController.class);
		
		//me.add("/api",ApiController.class);
		
		//首页
		me.add("/",EntranceController.class);
		
		//销量管理 - 上报销量
		me.add("/reported",ReportedController.class,"sales");
		
		//销量统计 - 战队排名
		me.add("/salesStatistics/teamRanking",com.xiaowu.controller.salesStatistics.TeamRankingController.class,"salesStatistics");
		//销量统计 - 银行排名
		me.add("/salesStatistics/bankRanking",BankRankingController.class,"salesStatistics");
				
		//猫币管理 - 个人排名
		me.add("/individualRanking",IndividualRankingController.class,"catCoin");
		//猫币管理 - 战队排名
		me.add("/teamRanking",TeamRankingController.class,"catCoin");
		//猫币管理 - 战区排名
		me.add("/areaRanking",AreaRankingController.class,"catCoin");
		//猫币管理 - 报单奖排名
		me.add("/entryPrizeRanking",EntryPrizeRankingController.class,"catCoin");
		//猫币管理 - 猫币详情统计
		me.add("/detailed",DetailedController.class,"catCoin");
		//猫币管理 - 猫币累计预定量
		me.add("/cumulativeBookings",CumulativeBookingsController.class,"catCoin");
		
		//系统管理  - 意见反馈
		me.add("/feedback",FeedbackController.class,"system");
		//系统管理 - 产品管理
		me.add("/product",ProductController.class,"system");
		//系统管理 - 字典管理
		me.add("/dictionary",DictionaryController.class,"system");
		//系统管理 - 银行管理
		me.add("/bank",BankController.class,"system");
		//系统管理 - 战队管理
		me.add("/team",TeamController.class,"system");
				
		//自动注册
		//me.add(new AutoBindRoutes());
		
		/*me.add("/product",ProductController.class);
		
		me.add("/subscribe",SubscribeController.class);
		
		me.add("/smsCode",SMSCodeController.class);
		
		me.add("/manage",ManageController.class);
		
		me.add("/weixin",WeixinController.class);*/
	}
	
	
	public static C3p0Plugin createC3p0Plugin() {
		return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin C3p0Plugin = createC3p0Plugin();
		me.add(C3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin);
		me.add(arp);
		
		// 所有配置在 MappingKit 中设置
		_MappingKit.mapping(arp);
		
		//缓存
		me.add(new EhCachePlugin());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		
		//异常处理
		//me.add(new ExceptionInterceptor());
		
		//系统拦截
		me.add(new SystemInterceptor());
	}

	/**
	 * 接收处理跳转
	 */
	@Override
	public void configHandler(Handlers me) {
	}

	@Override
	public void afterJFinalStart() {
		//开启微信数据获取
		//new Thread(new InitWeixin()).start();
		System.out.println("=============项目启动===============");
		
		new InitDictionary().initData();
		
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

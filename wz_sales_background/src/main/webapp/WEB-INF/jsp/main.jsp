<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>五洲军团销量系统</title>
<meta charset="UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css"
	href="${base}/static/plugIn/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/static/plugIn/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/static/plugIn/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/static/plugIn/h-ui.admin/skin/red/skin.css" id="skin" />
<link rel="stylesheet" type="text/css"
	href="${base}/static/plugIn/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/static/plugIn/laypage-v1.3/laypage/skin/laypage.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/static/plugIn/viewer/viewer.min.css" />
	
</head>
<body>
<input type="hidden" id="mainId" value="main" />
	<!--_header 作为公共模版分离出去-->
	<header class="navbar-wrapper">
		<div class="navbar navbar-fixed-top">
			<div class="container-fluid cl">
				<a class="logo navbar-logo f-l mr-10 hidden-xs"
					href="">五洲军团销量系统</a> <a
					class="logo navbar-logo-m f-l mr-10 visible-xs"
					href="">销量系统</a> <span
					class="logo navbar-slogan f-l mr-10 hidden-xs">v1.0</span> <a
					aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs"
					href="javascript:;">&#xe667;</a>

				<nav id="Hui-userbar"
					class="nav navbar-nav navbar-userbar hidden-xs">
					<ul class="cl">
						<li>${loginInfo}</li>
						<li ><a onclick="exit()">退出 <i class="Hui-iconfont">&#xe726;</i></a>
						</li>
					</ul>
				</nav>
			</div>
		</div>
	</header>
	<!--/_header 作为公共模版分离出去-->

	<!--_menu 作为公共模版分离出去-->
	<aside class="Hui-aside">

		<div class="menu_dropdown bk_2">
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe616;</i> 销量管理<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a _url="reported" title="上报销量">上报销量</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe616;</i> 销量统计管理<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a _url="salesStatistics/teamRanking" title="战队排名">战队排名</a></li>
						<li><a _url="salesStatistics/bankRanking" title="银行排名">银行排名</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="menu-picture">
				<dt>
					<i class="Hui-iconfont">&#xe613;</i> 猫币统计管理<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a _url="individualRanking" title="猫币个人排名">猫币个人排名</a></li>
						<li><a _url="teamRanking" title="猫币战队排名">猫币战队排名</a></li>
						<li><a _url="areaRanking" title="猫币区域排名">猫币区域排名</a></li>
						<li><a _url="entryPrizeRanking" title="报单奖排名">报单奖排名</a></li>
						<li><a _url="detailed" title="猫币区域排名">猫币详情统计</a></li>
						<li><a _url="cumulativeBookings" title="猫币累计预订量">猫币累计预订量</a></li>
					</ul>
				</dd>
			</dl>
			<dl >
				<dt>
					<i class="Hui-iconfont">&#xe616;</i> 系统管理<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a _url="feedback" title="意见反馈">意见反馈</a></li>
						<li><a _url="product" title="产品管理">产品管理</a></li>
						<li><a _url="bank" title="银行管理">银行管理</a></li>
						<li><a _url="team" title="战队管理">战队管理</a></li>
						<!-- <li><a _url="dictionary" title="字典管理">字典管理</a></li> -->
					</ul>
				</dd>
			</dl>
		</div>
	</aside>
	<div class="dislpayArrow hidden-xs">
		<a class="pngfix" href="javascript:void(0);"
			onClick="displaynavbar(this)"></a>
	</div>
	<!--/_menu 作为公共模版分离出去-->

	<section class="Hui-article-box"></section>

	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript"
		src="${base}/static/plugIn/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="${base}/static/plugIn/layer/2.4/layer.js"></script>
	<script type="text/javascript"
		src="${base}/static/plugIn/h-ui/js/H-ui.js"></script>
	<script type="text/javascript"
		src="${base}/static/plugIn/h-ui.admin/js/H-ui.admin.page.js"></script>
	<script type="text/javascript"
		src="${base}/static/plugIn/laypage-v1.3/laypage/laypage.js"></script>
	<script type="text/javascript"
		src="${base}/static/js/common.min.js?v=4"></script>
	<script type="text/javascript"
		src="${base}//static/plugIn/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${base}//static/plugIn/echarts/echarts.min.js"></script>
	<script type="text/javascript"
		src="${base}//static/plugIn/echarts/china.js"></script>
	<script type="text/javascript"
		src="${base}//static/plugIn/viewer/viewer.min.js"></script>
	<script type="text/javascript"
		src="${base}//static/plugIn/Validform/5.3.2/Validform.min.js"></script>
	
	<!--/_footer /作为公共模版分离出去-->

	<script type="text/javascript">
	
	function loadContent(url,title){
		//$(".Hui-article-box").load("${base}/"+url);
		
		load({
			element:".Hui-article-box",
			url:"${base}/"+url
		});
		
		var stateObject = {};
		var newUrl = "./main#"+url;
		history.pushState(stateObject,title,newUrl);
	}
	$(function(){
		var hash = window.location.hash;
		if(hash!=''){
			hash = hash.replace("#","");
			loadContent(hash,"首页");
		}else{
			loadContent("home","首页");
		}
	});
	
	$(".menu_dropdown a").click(function(){
		loadContent($(this).attr("_url"),$(this).attr("title"));
	});
	
	//退出
	function exit(){
		layer.confirm('是否退出系统？', {icon: 3, title:'提示'}, function(index){
		 	 window.location.href="${base}/exit";
		 	 layer.close(index);
		});
	}
	
</script>
</body>
</html>

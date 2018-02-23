<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/static/login/css/style.css?v=1"/>
		<script type="text/javascript">
			var mainId = document.getElementById("mainId");
			console.log('-------------------------------');
			console.log(mainId);
			console.log('-------------------------------');
			if(mainId){
				location.reload();
			}
		</script>
	</head>
	<body >
		<div class="login">
			<h2>五洲军团销售系统</h2>
			<div class="login-top">
				<h1>${state == 'loginError' ? '帐号密码错误': '请输入账号密码'}</h1>
				<form action="submitLogin" method="post" id="formId">
					<input type="text" name="name" value="用户帐号" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '用户帐号';}">
					<input type="password" name="password" value="password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'password';}">
			    </form>
			</div>
			<div class="login-bottom">
				<h3><a onclick="submitLogin()"> 登 录 </a></h3>
			</div>
		</div>	
		<div class="copyright">
			<p>(c) Copyright 2017 WH. All Rights Reserved. </p>
		</div>
		<script type="text/javascript">
			function submitLogin(){
				document.getElementById("formId").submit();
			}
			document.onkeydown=function(event){
				var event=event||window.event;
			    if(event.keyCode==13){
			    	submitLogin();
			    }
			};
		</script>
	</body>
</html>

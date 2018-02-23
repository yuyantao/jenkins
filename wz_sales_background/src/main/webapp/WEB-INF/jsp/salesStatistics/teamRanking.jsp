<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	销量统计管理
	<span class="c-gray en">&gt;</span>
	战队排名
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="Hui-article">
<article class="cl pd-20">
<iframe name="name_iframe" id="id_iframe" class="hide"></iframe>
<form action="" id="queryForm" target="name_iframe" method="post">
<input type="hidden" id="page" name="page" />
	<div class="row cl pd-5">
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			昨天日期范围：
		</div>
		<div class="text-l col-xs-10  col-sm-5  col-md-4">
			<input type="text" name="yesterdaytartTime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'yesterdayogmax\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${lastMonthLastDay}" id="yesterdayogmin" class="input-text Wdate" style="width:170px;height: 31px;">
			-
			<input type="text" name="yesterdayndTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'yesterdayogmin\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${yesterday}" id="yesterdayogmax" class="input-text Wdate" style="width:170px;height: 31px;">
		</div>
	</div>
	<div class="row cl pd-5">
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			今天日期范围：
		</div>
		<div class="text-l col-xs-10  col-sm-5  col-md-4">
			<input type="text" name="startTime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'logmax\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${lastMonthLastDay}" id="logmin" class="input-text Wdate" style="width:170px;height: 31px;">
			-
			<input type="text" name="endTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'logmin\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${toDay}" id="logmax" class="input-text Wdate" style="width:170px;height: 31px;">
		</div>
		<div class="text-l col-xs-2  col-sm-2  col-md-1">
			<button name="" id="" class="btn btn-success" type="button" onclick="loadData(1)"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
		</div>
	</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<a class="btn btn-secondary radius" data-title="下载csv" _href="${base}/reported/downlodCSV" onclick="haha()" ><i class="Hui-iconfont">&#xe640;</i> 下载csv</a>
		</span>
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th >排名</th>
					<th style="width: 100px">排名变动情况</th>
					<th >战区</th>
					<th >战队</th>
					<th >队长</th>
					<c:forEach items="${teamList}" var="team">
						<th>${team.name}</th>
					</c:forEach>
					<th >总计</th>
				</tr>
			</thead>
				<%-- <tbody id="listData">
					<c:set var="listNumber" value="0"></c:set>
					<c:forEach items="${list}" var="list" varStatus="ind">
					<c:set var="listNumber" value="${ind.index+1}"></c:set>
					<tr class="text-c">
						<th >${list.ranking}</th>
						<td style="font-weight: bold;">
						<c:if test="${fn:contains(list.changeCondition,'↓')}">
							<font style="color: green;">${list.changeCondition}</font>
						</c:if>
						<c:if test="${fn:contains(list.changeCondition,'↑')}">
							<font style="color: red;">${list.changeCondition}</font>
						</c:if>
						<c:if test="${fn:contains(list.changeCondition,'不变')}">
							<p style="color: red;background-color:yellow;margin-bottom: 0;">${list.changeCondition}</p>
						</c:if>
						</td>
						<td>${list.area}</td>
						<td>${list.team}</td>
						<td>${list.leader}</td>
						<c:forEach items="${teamList}" var="team">
							<td>
								<c:if test="${team.name == list.area}">
									${list.total}
								</c:if>
							</td>
						</c:forEach>
						<td>${list.total}</td>
					</tr>
					</c:forEach>
					<c:if test="${listNumber==0}">
						<tr><td colspan="9" class="text-c c-warning">暂无数据</td></tr>
					</c:if>
				</tbody> --%>
				<tbody id="listData">
					<c:set var="listNumber" value="0"></c:set>
					<c:forEach items="${list}" var="list" varStatus="ind">
					<c:set var="listNumber" value="${ind.index+1}"></c:set>
					<tr class="text-c">
						<c:forEach items="${list}" var="li">
							<td >${li}</td>
						</c:forEach>
					</tr>
					</c:forEach>
					<c:if test="${listNumber==0}">
						<tr><td colspan="12" class="text-c c-warning">暂无数据</td></tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</article>
</div>

<script type="text/javascript">


$(function(){
	//默认加载第一页
	loadData(1);
});

 
function loadData(page){
	$("#page").val(page);
	pagingList({
		url:"${base}/salesStatistics/teamRanking/list",//访问地址，必填
		data : $("#queryForm").serializeArray(),			//传输参数
	});
}

function haha(){
	$("#queryForm").attr("action","${base}/salesStatistics/teamRanking/downlodCSV");
	$("#queryForm").submit();
}


</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
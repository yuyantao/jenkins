<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	猫币统计管理
	<span class="c-gray en">&gt;</span>
	猫币详情统计
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="Hui-article">
<article class="cl pd-20">
<iframe name="name_iframe" id="id_iframe" class="hide"></iframe>
<form action="" id="queryForm" target="name_iframe" method="post">
<input type="hidden" id="page" name="page" />
	
	<div class="row cl pd-5">
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			日期范围：
		</div>
		<div class="text-l col-xs-10  col-sm-5  col-md-4">
			<input type="text" name="startTime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'logmax\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${yesterday}" id="logmin" class="input-text Wdate" style="width:170px;height: 31px;">
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
					<th width="50px">战区</th>
					<th width="50px">战队</th>
					<th width="50px">姓名</th>
					<c:forEach items="${menu}" var="m">
						<th>${m.name}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody id="listData">
				<c:set var="listNumber" value="0"></c:set>
				<c:forEach items="${list}" var="list" varStatus="ind" >
				<c:set var="listNumber" value="${ind.index+1}"></c:set>
				<tr class="text-c">
					<c:forEach items="${list}" var="l">
						<td>${l}</td>
					</c:forEach>
				</tr>
				</c:forEach>
				<c:if test="${listNumber==0}">
					<tr><td colspan="16" class="text-c c-warning">暂无数据</td></tr>
				</c:if>
				<tr>
					<td colspan="16">
						<div class="f-l lh-30" >共有数据：<b>${pageInfo.totalRow}</b> 条 ,当前页：<b id="pageNum">${pageInfo.pageNumber}</b> 页 ,每页显示：<b>${pageInfo.pageSize}</b> 条</div>
						<div class="f-r" id="paddingInfo">${pageInfo.totalPage}</div>
					</td>
				</tr>
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
		url:"${base}/detailed/list",//访问地址，必填
		data : $("#queryForm").serializeArray(),			//传输参数
		paddingId:"paddingInfo",//分页div的Id,不需要分页则不需要填
	});
}

function haha(){
	$("#queryForm").attr("action","${base}/detailed/downlodCSV");
	$("#queryForm").submit();
}


</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
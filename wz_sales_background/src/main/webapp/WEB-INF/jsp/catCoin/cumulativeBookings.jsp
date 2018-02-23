<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	猫币统计管理
	<span class="c-gray en">&gt;</span>
	猫币累计统计量
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="Hui-article">
<article class="cl pd-20">
<iframe name="name_iframe" id="id_iframe" class="hide"></iframe>
<form action="" id="queryForm" target="name_iframe" method="post">
<input type="hidden" id="page" name="page" />
	
	<div class="row cl pd-5">
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			战队：
		</div>
		<div class="text-l col-xs-10  col-sm-4  col-md-2">
			<input type="text" name="team" id="" placeholder="请输入战队" style="width:200px" class="input-text">
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
					<th >战队</th>
					<th >银行</th>
					<th >累计预定17银猫折合单套</th>
					<th >累计预订17金猫数量（含金银同号）</th>
					<th >累计预订银猫数量（5枚单套）</th>
					<th >累计预订银猫数量（小箱装）</th>
					<th >累计预订银猫数量（大箱装）</th>
					<th >累计预订17金猫数量</th>
					<th >累计预订金猫数量（金银同号）</th>
					<th >累计预订16金猫数量</th>
				</tr>
			</thead>
			<tbody id="listData">
				<c:set var="listNumber" value="0"></c:set>
				<c:forEach items="${list}" var="list" varStatus="ind">
				<c:set var="listNumber" value="${ind.index+1}"></c:set>
				<tr class="text-c">
					<td>${list.team}</td>
					<td>${list.bank}</td>
					<td>${list.yqnymzh}</td>
					<td>${list.yqnjmslhjyth}</td>
					<td>${list.ymslwmdt}</td>
					<td>${list.ymslxxz}</td>
					<td>${list.ymsldxz}</td>
					<td>${list.yqnjmsl}</td>
					<td>${list.yjydjmsljyth}</td>
					<td>${list.ylnjmsl}</td>
				</tr>
				</c:forEach>
				<c:if test="${listNumber==0}">
					<tr><td colspan="10" class="text-c c-warning">暂无数据</td></tr>
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
		url:"${base}/cumulativeBookings/list",//访问地址，必填
		data : $("#queryForm").serializeArray(),			//传输参数
	});
}

function haha(){
	$("#queryForm").attr("action","${base}/cumulativeBookings/downlodCSV");
	$("#queryForm").submit();
}


</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
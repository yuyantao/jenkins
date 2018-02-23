<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<style>
.warZone{
	width: 100%;
}
.warZone li{
	padding: 10px 0;
	border-bottom: 1px solid #ccc; 
	text-align: center;
	font-size: 30px;
	cursor: pointer;
}
.warZone li.active{
	background-color: #f4f2f2;
}
</style>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	系统管理
	<span class="c-gray en">&gt;</span>
	战队管理
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="Hui-article">
<article class="cl pd-20">
<iframe name="name_iframe" id="id_iframe" class="hide"></iframe>
<form action="" id="queryForm" target="name_iframe" method="post">
<input type="hidden" id="page" name="page" />
<input type="hidden" id="parentCode" name="parentCode" />
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20 mb-5">
		<span class="l">
			<a class="btn btn-secondary radius" data-title="下载csv" _href="${base}/reported/downlodCSV" onclick="edit()" ><i class="Hui-iconfont">&#xe600;</i> 添加战队</a>
		</span>
	</div>
	<div class="panel panel-default">
		<div class="panel-header">战区/战队</div>
		<div class="panel-body" style="padding-top: 0">
			<div class="row cl">
				<div class="col-sm-2">
					<ul class="warZone">
						<c:forEach items="${list}" var="t" varStatus="ind">
							 <li data="${t.code}" <c:if test="${ind.index==0}">class="active"</c:if> >${t.name}</li>
						</c:forEach>
					</ul>
				</div>
				<div class="col-sm-10">
					<div >
						<table class="table table-border table-bordered table-bg table-hover table-sort">
							<thead>
								<tr class="text-c">
									<th >战队</th>
									<th >队长</th>
									<th >操作首拼</th>
								</tr>
							</thead>
							<tbody id="listData">
								<c:set var="listNumber" value="0"></c:set>
								<c:forEach items="${pageInfo.list}" var="list" varStatus="ind">
								<c:set var="listNumber" value="${ind.index+1}"></c:set>
								<tr class="text-c">
									<td>${list.name}</td>
									<td>${list.leader}</td>
									<td>
										<input class="btn btn-secondary radius size-S" type="button" onclick="edit('${list.id}')" value="修改" />
										<input class="btn btn-danger radius size-S" type="button" onclick="deleteData('${list.id}')" value="删除" />
									</td>
								</tr>
								</c:forEach>
								<c:if test="${listNumber==0}">
									<tr><td colspan="9" class="text-c c-warning">暂无数据</td></tr>
								</c:if>
								<tr>
									<td colspan="9">
										<div class="f-l lh-30" >共有数据：<b>${pageInfo.totalRow}</b> 条 ,当前页：<b id="pageNum">${pageInfo.pageNumber}</b> 页 ,每页显示：<b>${pageInfo.pageSize}</b> 条</div>
										<div class="f-r" id="paddingInfo">${pageInfo.totalPage}</div>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
		</div>
	</div>
	</article>
</div>

<script type="text/javascript">




$(function(){
	//默认加载第一页
	loadData(1);
});

$(".warZone li").click(function(){
	$(".warZone li").removeClass("active");
	$(this).addClass("active");
	loadData(1);
});
 
function loadData(page){
	
	$("#parentCode").val($(".warZone li[class='active']").attr("data"));
	
	$("#page").val(page);
	pagingList({
		url:"${base}/team/list",//访问地址，必填
		data : $("#queryForm").serializeArray(),			//传输参数
		paddingId:"paddingInfo",//分页div的Id,不需要分页则不需要填
	});
}

function edit(id){
	crud.editPage({
		title:"添加/修改",
		area: ['700px', '200px'],
		id:id,
		editPageUrl:"${base}/team/editPage",
		saveUrl:"${base}/team/save",
		success:function(){
			$(".parentCodeClass").val($(".warZone li[class='active']").attr("data"));
		}
	});

	
	
}

function deleteData(id){
	crud.del({
		url:"${base}/team/deleteData",
		data:{id:id},
		success:function(){
			loadData(1);
		}
	})
}


</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
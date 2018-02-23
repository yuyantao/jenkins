<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	系统管理
	<span class="c-gray en">&gt;</span>
	产品管理
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="Hui-article">
<article class="cl pd-20">
<iframe name="name_iframe" id="id_iframe" class="hide"></iframe>
<form action="" id="queryForm" target="name_iframe" method="post">
<input type="hidden" id="page" name="page" />
	<div class="row cl pd-5 ">
		
	</div>
	<div class="row cl pd-5">
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			产品名称：
		</div>
		<div class="text-l col-xs-10  col-sm-4  col-md-2">
			<input type="text" name="name" id="" placeholder="请输入产品名称" style="width:250px" class="input-text">
		</div>
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			状态：
		</div>
		<div class="text-l col-xs-10  col-sm-4  col-md-2">
			<span class="select-box" style="width:250px">
				<select class="select" name="isDel" size="1">
					<option value="" selected="">全部</option>
					<option value="1">正常</option>
					<option value="0">禁用</option>
				</select>
			</span>
		</div>
		
		
		<div class="text-l col-xs-2  col-sm-2  col-md-1">
			<button name="" id="" class="btn btn-success" type="button" onclick="loadData(1)"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
		</div>
	</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
			<a class="btn btn-secondary radius" data-title="下载csv" _href="${base}/reported/downlodCSV" onclick="edit()" ><i class="Hui-iconfont">&#xe600;</i> 添加产品</a>
		</span>
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th >产品名称</th>
					<th >价格</th>
					<th >排序</th>
					<th >状态</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody id="listData">
				<c:set var="listNumber" value="0"></c:set>
				<c:forEach items="${pageInfo.list}" var="list" varStatus="ind">
				<c:set var="listNumber" value="${ind.index+1}"></c:set>
				<tr class="text-c">
					<td>${list.name}</td>
					<td>${list.price}</td>
					<td>${list.stick}</td>
					<td>
					<c:if test="${list.isDel == 1}">
						<span class="label label-success radius">正常</span>
					</c:if>
					<c:if test="${list.isDel == 0}">
						<span class="label label-default radius">禁用</span>
					</c:if>
					</td>
					<td>
						<input class="btn btn-secondary radius size-S" type="button" onclick="edit('${list.id}')" value="修改" />
						<c:if test="${list.isDel == 1}">
							<input class="btn btn-danger radius size-S" type="button" onclick="deleteData('${list.id}',0)" value="删除" />
						</c:if>
						<c:if test="${list.isDel == 0}">
							<input class="btn btn-warning radius size-S" type="button" onclick="deleteData('${list.id}',1)" value="启用" />
						</c:if>
						
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
		url:"${base}/product/list",//访问地址，必填
		data : $("#queryForm").serializeArray(),			//传输参数
		paddingId:"paddingInfo",//分页div的Id,不需要分页则不需要填
	});
}

function edit(id){
	
	crud.editPage({
		title:"添加/修改",
		area: ['700px', '300px'],
		id:id,
		editPageUrl:"${base}/product/editPage",
		saveUrl:"${base}/product/save"
	});

}

function deleteData(id,state){
	var title;
	if(state==1){
		title = "是否确认启用!";
	}
	crud.del({
		url:"${base}/product/deleteData",
		data:{id:id},
		title:title,
		success:function(){
			loadData(1);
		}
	})
}


</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
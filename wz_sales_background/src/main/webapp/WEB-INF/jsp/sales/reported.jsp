<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>

<style>
#showImg img{
	width: 100px;
}
</style>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	销量管理
	<span class="c-gray en">&gt;</span>
	上报销量
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="Hui-article">
<article class="cl pd-20">
<input type="hidden" id="uploadData" value="${uploadData}"/>
<iframe name="name_iframe" id="id_iframe" class="hide"></iframe>
<form action="" id="queryForm" target="name_iframe" method="post">
<input type="hidden" id="page" name="page" />
	<div class="row cl pd-5 ">
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			战区：
		</div>
		<div class="text-l col-xs-10  col-sm-4  col-md-2">
			<input type="text" name="area" id="" placeholder="请输入战区" style="width:200px" class="input-text">
		</div>
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			战队：
		</div>
		<div class="text-l col-xs-10  col-sm-3  col-md-2">
			<input type="text" name="team" id="" placeholder="请输入战队" style="width:200px" class="input-text">
		</div>
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			姓名：
		</div>
		<div class="text-l col-xs-10  col-sm-5  col-md-4">
			<input type="text" name="name" id="" placeholder="请输入姓名" style="width:200px" class="input-text">
		</div>
	</div>
	<div class="row cl pd-5">
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			银行：
		</div>
		<div class="text-l col-xs-10  col-sm-4  col-md-2">
			<input type="text" name="bank" id="" placeholder="请输入银行" style="width:200px" class="input-text">
		</div>
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			产品名称：
		</div>
		<div class="text-l col-xs-10  col-sm-3  col-md-2">
			<input type="text" name="product" id="" placeholder="请输入产品名称" style="width:200px" class="input-text">
		</div>
		<div class="text-r col-xs-2  col-sm-2  col-md-1">
			日期范围：
		</div>
		<div class="text-l col-xs-10  col-sm-5  col-md-4">
			<input type="text" name="startTime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'logmax\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  id="logmin" class="input-text Wdate" style="width:170px;height: 31px;">
			-
			<input type="text" name="endTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'logmin\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  id="logmax" class="input-text Wdate" style="width:170px;height: 31px;">
		</div>
		<div class="text-l col-xs-2  col-sm-2  col-md-1">
			<button name="" id="" class="btn btn-success" type="button" onclick="loadData(1)"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
		</div>
	</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<a class="btn btn-secondary radius" data-title="下载csv" _href="${base}/reported/downlodCSV" onclick="haha()" ><i class="Hui-iconfont">&#xe640;</i> 下载csv</a>
		<a class="btn btn-primary radius" style="display: none;" data-title="添加资讯" _href="article-add.html" onclick="article_add('添加资讯','article-add.html')" href="javascript:;"><i class="Hui-iconfont">&#xe640;</i> 下载excel</a>
		</span>
		
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th >战区</th>
					<th >战队</th>
					<th >姓名</th>
					<th >银行</th>
					<th >产品</th>
					<th >件数</th>
					<th >销售额</th>
					<th >上报时间</th>
					<th >底单</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody id="listData">
				<c:set var="listNumber" value="0"></c:set>
				<c:forEach items="${pageInfo.list}" var="list" varStatus="ind">
				<c:set var="listNumber" value="${ind.index+1}"></c:set>
				<tr class="text-c">
					<td>${list.area}</td>
					<td>${list.team}</td>
					<td>${list.name}</td>
					<td>${list.bank}</td>
					<td>${list.commonProduct}</td>
					<td>${list.commonCount}</td>
					<td>${list.commonSalesVolume}</td>
					<td>${list.createTime}</td>
					<td>
						<c:if test="${list.productLine == 0}">未上传底单</c:if>
						<c:if test="${list.productLine > 0}"><u style="cursor: pointer;" onclick="showBottomSingle('${list.serialNum}')">查看底单（${list.productLine}）</u></c:if>
					</td>
					<td>
						<input class="btn btn-danger radius size-S" onclick="deleteData('${list.id}')" type="button" value="删除">
					</td>
				</tr>
				</c:forEach>
				<c:if test="${listNumber==0}">
					<tr><td colspan="10" class="text-c c-warning">暂无数据</td></tr>
				</c:if>
				<tr>
					<td colspan="10">
						<div class="f-l lh-30" >共有数据：<b>${pageInfo.totalRow}</b> 条 ,当前页：<b id="pageNum">${pageInfo.pageNumber}</b> 页 ,每页显示：<b>${pageInfo.pageSize}</b> 条</div>
						<div class="f-r" id="paddingInfo">${pageInfo.totalPage}</div>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
	</article>
</div>
<ul id="images" style="display: none;"></ul>
	
<script type="text/javascript">


$(function(){
	//默认加载第一页
	loadData(1);
});

 
function loadData(page){
	$("#page").val(page);
	pagingList({
		url:"${base}/reported/list",//访问地址，必填
		data : $("#queryForm").serializeArray(),			//传输参数
		paddingId:"paddingInfo",//分页div的Id,不需要分页则不需要填
	});
}

function haha(){
	$("#queryForm").attr("action","${base}/reported/downlodCSV");
	$("#queryForm").submit();
}

var viewer = new Viewer(document.getElementById('images'), {
	scalable:false,
	title:false
 });

//查看底单
function showBottomSingle(serialNum){
	var uploadData = $("#uploadData").val();
	console.log(uploadData);
	ajax({
		url:"${base}/reported/queryBottomSingle",
		data:{serialNum:serialNum},
		success:function(data){
			 var s = "";
			 for(var i=0;i<data.length;i++){
				 s+="<li><img src='"+uploadData+data[i].img_path+"'></li>";
			 }
			 viewer.destroy();
			 $("#images").html(s);
			 viewer = new Viewer(document.getElementById('images'), {
				scalable:false,
				title:false
			 });
			 viewer.show();
		}
	})
}


function deleteData(id){
	crud.del({
		url:"${base}/reported/deleteData",
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
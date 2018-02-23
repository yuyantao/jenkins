<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<style>
.layui-layer-btn{
    border-top: 1px solid #eee;
}
</style>

<form class="form form-horizontal" id="form-article-add"
	style="width: 80%;margin-left: 10%">
	<input type="hidden" name="team.parent_code" id="parentCodeId" class="parentCodeClass" />
	<input type="hidden" name="team.id" id="id" value="${team.id}"/>
	<div class="row cl">
		<label class="form-label col-sm-3"><span class="c-red">*</span>战队名称：</label>
		<div class="formControls col-sm-6">
			<input type="text" class="input-text" placeholder="请输入战队名称" id="" name="team.name" datatype="*" nullmsg="请输入战队名称"  value="${team.name}" />
		</div>
		<div class="col-sm-3"></div>
	</div>
	<div class="row cl">
		<label class="form-label col-sm-3"><span class="c-red">*</span>队长姓名：</label>
		<div class="formControls col-sm-6">
			<input type="text" class="input-text" placeholder="请输入队长姓名" id="" name="team.leader" datatype="*" value="${team.leader}" />
		</div>
		<div class="col-sm-3"></div>
	</div>
</form>


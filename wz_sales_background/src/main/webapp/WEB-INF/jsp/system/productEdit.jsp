<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<style>
.layui-layer-btn{
    border-top: 1px solid #eee;
}
</style>

<form class="form form-horizontal" id="form-article-add"
	style="width: 80%;margin-left: 10%">
	<input type="hidden" name="product.id" id="id" value="${product.id}"/>
	<div class="row cl">
		<label class="form-label col-sm-3"><span class="c-red">*</span>产品名称：</label>
		<div class="formControls col-sm-6">
			<input type="text" class="input-text" placeholder="请输入产品名称" id="" name="product.name" datatype="*" nullmsg="请输入产品名称"  value="${product.name}" />
		</div>
		<div class="col-sm-3"></div>
	</div>
	<div class="row cl">
		<label class="form-label col-sm-3"><span class="c-red">*</span>价格：</label>
		<div class="formControls col-sm-6">
			<input type="text" class="input-text" placeholder="请输入价格" id="" name="product.price" datatype="*" value="${product.price}" />
		</div>
		<div class="col-sm-3"></div>
	</div>
	<div class="row cl">
		<label class="form-label col-sm-3"><span class="c-red">*</span>排序：</label>
		<div class="formControls col-sm-6">
			<input type="text" class="input-text" placeholder="请输入排序" id="" name="product.stick" datatype="n" value="${product.stick}" />
		</div>
		<div class="col-sm-3"></div>
	</div>
	<div class="row cl">
		<label class="form-label col-sm-3"><span class="c-red">*</span>状态：${product.isDel}</label>
		<div class="formControls col-sm-6">
			<span class="select-box"> 
				<select name="product.is_del" class="select" datatype="*">
						<option value="1" <c:if test="${product.isDel==1}">selected</c:if> >正常</option>
						<option value="0" <c:if test="${product.isDel==0}">selected</c:if> >禁用</option>
				</select>
			</span>
		</div>
		<div class="col-sm-3"></div>
	</div>
</form>


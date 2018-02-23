<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<style>
.layui-layer-btn{
    border-top: 1px solid #eee;
}
</style>

<form class="form form-horizontal" id="form-article-add"
	style="width: 80%;margin-left: 10%">
	<input type="hidden" name="bank.id" id="id" value="${bank.id}"/>
	<div class="row cl">
		<label class="form-label col-sm-3"><span class="c-red">*</span>产品名称：</label>
		<div class="formControls col-sm-6">
			<input type="text" class="input-text" placeholder="请输入银行名称" id="" name="bank.bank_name" datatype="*" nullmsg="请输入银行名称"  value="${bank.bankName}" />
		</div>
		<div class="col-sm-3"></div>
	</div>
	<div class="row cl">
		<label class="form-label col-sm-3"><span class="c-red">*</span>首拼：</label>
		<div class="formControls col-sm-6">
			<input type="text" class="input-text" placeholder="请输入拼音首拼" id="" name="bank.pin_yin" datatype="*" value="${bank.pinYin}" />
		</div>
		<div class="col-sm-3"></div>
	</div>
</form>


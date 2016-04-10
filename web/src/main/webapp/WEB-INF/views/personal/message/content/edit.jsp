<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="订阅内容"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
			<form:form id="inputForm" action="${ctx}/personal/message/content/save" method="post" modelAttribute="m" class="form-horizontal">
				<form:hidden path="id"/>
				<form:hidden path="msgSendId"/>
				<table class="formtable">
					<tr>
						<td width="20%">标题：</td>
						<td width="80%"><form:input path="title" size="50" cssClass="validate[required,maxLength[200]]"/></td>
					</tr>
					<tr>
		  				<td>内容：</td>
		  				<td><form:textarea path="content" cols="46" cssStyle="resize:none"/></td>
					</tr>
	  			</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
    </div>
<ewcms:footer/>
<script type="text/javascript">
	var validationEngine = $("#editForm").validationEngine({
		promptPosition:'bottomRight',
		showOneMessage: true
	});
	<ewcms:showFieldError commandName="m"/>
</script>
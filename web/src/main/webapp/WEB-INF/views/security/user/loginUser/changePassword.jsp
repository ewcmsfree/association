<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 用户"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
			<form id="editForm" method="post" action="${ctx}/security/user/loginUser/changePassword" class="form-horizontal">
		        <h1 class="title">修改密码</h1>
		        <fieldset>
					<table class="formtable">
						<tr>
							<td width="20%"><label for="oldPassword">旧密码：</label></td>
							<td width="80%"><input type="password" id="oldPassword" name="oldPassword" class="validate[required]"/></td>
						</tr>
						<tr>
							<td><label for="newPassword">新密码：</label></td>
							<td><input type="password" id="newPassword" name="newPassword" class="validate[required,minSize[5],maxSize[100]]" placeholder="请输入至少5位的新密码"/></td>
						</tr>
						<tr>
							<td><label for="plainPassword">确认新密码：</label></td>
							<td><input type="password" id="plainPassword" name="plainPassword" class="validate[required,equals[newPassword]]"/></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		var validationEngine = $("#editForm").validationEngine({
			promptPosition:'bottomRight',
			validateNonVisibleFields:true,
			showOneMessage: true
		});
		<ewcms:showFieldError commandName="m"/>
	});
</script>
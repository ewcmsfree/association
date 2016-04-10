<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 发件箱"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true,border:false">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
			<form:form id="editForm" action="${ctx}/personal/message/send/save" method="post" modelAttribute="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
				<table class="formtable">
					<tr>
						<td width="20%"><form:label path="title">标题：</form:label></td>
						<td width="80%"><form:input path="title" size="50" cssClass="validate[required, maxLength[200]]"/></td>
	  				</tr>
	  				<tr>
  						<td>内容：</td>
						<td><textarea cols="46" id="content" name="content" class="validate[required]"></textarea></td>
	  				</tr>
	  				<tr>
  						<td>类型：</td>
						<td><form:select id="type" path="type" items="${sendTypeList}" itemLabel="info" data-options="editable:false"/></td>
	  				</tr>
					<tr id="trUserName">
 						<td>收信人：</td>
						<td>
							<input id="userInfo" name="userIds" class="validate[required]" style="width:100%;"/>
						</td>
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
	$(function(){
		<c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomRight',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
		</c:choose>
		
		$('#userInfo').combobox({
			url: '${ctx}/personal/message/send/user?msgSendId=${m.id}',
			valueField:'id',
	        textField:'text',
			editable:false,
			multiple:true,
			multiline:true,
			cascadeCheck:false,
			panelWidth:200,
			panelHeight:140,
			height:50
		});
		
		$('#type').combobox({
			onChange:function(newValue, oldValue){
				if (newValue != 'GENERAL'){
		    		$('#trUserName').hide();
		    	}else{
		    		$('#trUserName').show();
		    	}
			}
		});	
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>
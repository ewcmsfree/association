<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - ACL"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/personal/memoranda/save" commandName="m" class="form-horizontal">
				<input type="hidden" id="year" name="year" value="${year}"/>
				<input type="hidden" id="month" name="month" value="${month}"/>
				<input type="hidden" id="day" name="day" value="${day}"/>
				<form:hidden path="id"/>
				<form:hidden path="noteDate"/>
				<form:hidden path="userId"/>
				<c:forEach var="selection" items="${selections}">
					<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
				<table class="formtable">
					<tr>
						<td width="20%">标题：</td>
						<td width="80%"><form:input path="title" size="25" cssClass="validate[required,maxLength[25]]"/></td>
					</tr>
					<tr>
		  				<td>内容：</td>
		  				<td><form:textarea path="content" cols="46" cssStyle="resize:none"/></td>
					</tr>
					<tr>
		  				<td><form:label path="warn">提醒：</form:label></td>
		  				<td><form:checkbox path="warn"/></td>
					</tr>
					<tr id="tr_warn" >
		  				<td>&nbsp;</td>
		  				<td>
							<table class="formtable">
			  					<tr>
			    					<td>时间：</td>
			    					<td><form:input path="warnTime" cssClass="easyui-timespinner" cssStyle="width:80px;"/></td>
              					</tr>
              					<tr>
                					<td>重复频率：</td>
                					<td><form:select path="frequency" items="${frequencyList}" itemLabel="info" cssClass="easyui-combobox"/></td>
              					</tr>
              					<tr>
                					<td>提前：</td>
                					<td><form:select path="before" items="${beforeList}" itemLabel="info" cssClass="easyui-combobox"/></td>
              					</tr>
              					<tr>
                					<td>错过提醒：</td>
                					<td><form:checkbox path="missRemind"/></td>
              					</tr>
							</table>
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
    			parent.location.reload();
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
		
		$('input[name=warn]').bind('change', function(){
			if ($(this).is(':checked')) {
            	$('#tr_warn').show();
        	} else {
            	$('#tr_warn').hide();
        	}
		});
		
		$('input[name=warn]').trigger('change');
	});
</script>
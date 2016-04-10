<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 文字报表"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true,border:false">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
			<form:form id="editForm" action="${ctx}/system/report/text/save" method="post" modelAttribute="m" enctype="multipart/form-data" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
				<table class="formtable" >
					<tr>
						<td width="20%"><form:label path="name">名称：</form:label></td>
						<td width="80%"><form:input path="name" cssClass="validate[required, maxLength[20]]"/></td>
					</tr>
					<tr>
						<td>报表文件：</td>
						<td><input type="file" accept="jrxml" id="textReportFile" name="textReportFile" onchange="javascript:if(this.value.toLowerCase().lastIndexOf('jrxml')==-1){alert('请选择jrxml文件！');this.value='';}"/></td>
					</tr>
					<tr>
						<td>数据源类型：</td>
						<td><form:select path="baseDs.id" items="${baseDsList}" itemLabel="name" itemValue="id" cssClass="easyui-combobox"/></td>
					</tr>
					<tr>
						<td><form:label path="hidden">是否隐藏：</form:label></td>
						<td><form:checkbox path="hidden"/></td>
					</tr>
					<tr>
						<td><form:label path="remarks">备注：</form:label></td>
		  				<td><form:textarea path="remarks" cols="46" cssStyle="resize:none" placeholder="简要说明"/></td>
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
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>
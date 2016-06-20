<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 报表参数"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/system/report/parameter/${reportType}/${reportId}/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="className"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
		  				<td width="15%">参数编号：</td>
		  				<td width="35%"><form:input path="id" readonly="true"/></td>
		  				<td width="15%">参数名：</td>
		  				<td width="35%"><form:input path="enName" readonly="true"/></td>
					</tr>
					<tr>
          				<td><form:label path="cnName">中文名：</form:label></td>
		  				<td><form:input path="cnName"/></td>
		  				<td>默认值：</td>
		  				<td>
		    				<span id="defaultvalue_span"><form:input path="defaultValue"/></span>
							<span id="userName_span"><input id="sessionValue" type="text" name="sessionValue"/></span>
		  				</td>
					</tr>
					<tr>
		  				<td><form:label path="type">数据输入方式：</form:label></td>
		  				<td><form:select path="type" items="${typeMap}" itemLabel="description"/></td>
		  				<td><form:label path="value">辅助数据设置：</form:label></td>
		  				<td><form:input path="value"/></td>
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
		
		$('#type').click(function() {
			if ($('#type').val() == 'SESSION'){
				$('#userName_span').show();
				$('#defaultvalue_span').hide();
			}else{
				$('#userName_span').hide();
				$('#defaultvalue_span').show();
			}
		});
		$('#type').trigger('click');
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>

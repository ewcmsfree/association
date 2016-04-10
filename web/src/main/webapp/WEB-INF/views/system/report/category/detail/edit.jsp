<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - jndi数据源"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/system/report/category/detail/${categoryId}/save" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
		  				<td width="20%">文字报表：</td>
		  				<td><input id="text_categories" name="textReportIds" style="width:200px;"/></td>
	    			</tr>
	    			<tr>
		  				<td>图型报表：</td>
		  				<td><input id="chart_categories" name="chartReportIds" style="width:200px;"/></td>
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
		
		$('#text_categories').combobox({
			url: '${ctx}/system/report/category/detail/${categoryId}/text',
			valueField:'id',
	        textField:'text',
			editable:false,
			multiple:true,
			cascadeCheck:false,
			panelWidth:200,
			panelHeight:70
		});
		$('#chart_categories').combobox({
			url: '${ctx}/system/report/category/detail/${categoryId}/chart',
			valueField:'id',
	        textField:'text',
			editable:false,
			multiple:true,
			cascadeCheck:false,
			panelWidth:200,
			panelHeight:70
		});
	});
	
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>

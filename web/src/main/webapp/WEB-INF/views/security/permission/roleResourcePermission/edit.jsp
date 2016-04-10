<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 资源 权限"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/security/permission/roleResourcePermission/${roleId}/save" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  		<table class="formtable">
		        		<tr>
				  			<td width="20%">资源名称：</td>
				  			<td width="80%">
						    	<c:choose>
								  	<c:when test="${empty(m.id)}">
								  		<c:set var="resourceUrl" value="${ctx}/security/resource/tree"/>
								  	</c:when>
								  	<c:otherwise>
								  		<c:set var="resourceUrl" value="${ctx}/security/resource/tree/${m.resourceId}/singleChecked"/>
								  	</c:otherwise>
								</c:choose>
								<form:input path="resourceId" data-options="url:'${resourceUrl}',editable:false,onSelect:function(node){$('#resourceName').val(node.text);}"/>
				  				<form:input path="resourceName" cssClass="validate[required]" cssStyle="display:none" data-prompt-position="bottomRight:90,0"/>
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>权限名称：</td>
			    			<td><form:select path="permissionIds" items="${permissions}" itemValue="id" itemLabel="name" cssClass="validate[required]" cssStyle="width:50%;height:120px"/></td>
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
	    			validateNonVisibleFields:true,
	    			showOneMessage: true
	    		});
    			<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	    
		$('#resourceId').combotree({
	    	panelWidth:200,
	    	panelHeight:130,
			onBeforeSelect : function(node){
				if (node.attributes.root){
					$.messager.alert('提示', '根节点不能被选中，请重新选择', 'info');
					return false;
				}
			}
		});
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>
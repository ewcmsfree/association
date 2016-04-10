<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 角色"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/security/permission/role/save" method="post" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
				  		<td width="20%"><form:label path="name">角色名称：</form:label></td>
				  		<td width="80%"><form:input path="name" cssClass="validate[required,ajax[ajaxNameCall]]" placeholder="角色描述名"/></td>
			    	</tr> 	
			    	<tr>
	             		<td><form:label path="role">角色标识：</form:label></td>
			  			<td><form:input path="role" cssClass="validate[required]" placeholder="程序中使用的名称"/></td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="description">详细描述：</form:label></td>
			    		<td><form:input path="description"/></td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="show">状态：</form:label></td>
			    		<td><form:radiobuttons path="show" items="${availableList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
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
	    		$.validationEngineLanguage.allRules.ajaxNameCall= {
                    "url": "${ctx}/security/permission/role/validate",
                    extraDataDynamic : ['#id'],
                    "alertTextLoad": "* 正在验证，请稍等。。。"
                };
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
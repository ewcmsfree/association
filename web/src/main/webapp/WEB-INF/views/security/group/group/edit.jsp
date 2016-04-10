<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 分组"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/security/group/group/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        		<tr>
				  			<td width="20%"><form:label path="name">分组名称：</form:label></td>
				  			<td width="80%"><form:input path="name" cssClass="validate[required,ajax[ajaxNameCall]]" placeholder="输入分组的名称"/></td>
			    		</tr>
			    		<tr>
			    			<td><form:label path="type">类型：</form:label></td>
			    			<td>
			    				<form:select path="type" items="${types}" itemLabel="info"/>
			    			</td>
			    		</tr>
			    		<tr id="tr-user">
			    			<td><form:label path="defaultGroup">是否默认：</form:label></td>
			    			<td><form:radiobuttons path="defaultGroup" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
			    		</tr>
			    		<tr>
			    			<td><form:label path="show">是否有效</form:label></td>
			    			<td><form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
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
                    "url": "${ctx}/security/group/group/validate",
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
		
		$('#type').combobox({
			panelHeight:'auto',
			panelWidth:200,
			editable:false,
			onSelect:function(record){
				if (record.value == 'user'){
					$('#tr-user').show();
				} else {
					$('#tr-user').hide();
				}
			}
		});
		
		if ($('#type option:selected').val() == 'user'){
			$('#tr-user').show();
		} else {
			$('#tr-user').hide();
		}
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>

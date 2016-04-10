<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 授权"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/security/auth/save" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
				<h1 class="title">基本信息</h1>
		    	<fieldset>
				  	<table class="formtable">
			        	<tr>
							<td width="20%"><form:label path="type">类型：</form:label></td>
							<td width="80%"><form:select path="type" items="${types}" itemLabel="info" data-options="width:150,panelHeight:'auto',editable:false"/></td>
				    	</tr>
				    	<tr id="tr-user">
		            		<td>用户：</td>
				  			<td>
								<form:input path="userId"/>
				  				<form:input path="userName" cssClass="validate[required,funcCall[funcValidate]]" cssStyle="display:none" data-prompt-position="bottomRight:140,70"/>
							</td>
				    	</tr>
				    	<tr id="tr-group">
				    		<td><span id="span-group"></span>：</td>
				    		<td>
				    			<form:input path="groupId"/>
				    			<form:input path="groupName" cssClass="validate[required,funcCall[funcValidate]]" cssStyle="display:none" data-prompt-position="bottomRight:140,70"/>
				    		</td>
				    	</tr>
				    	<tr id="tr-organization_job-1">
				    		<td>组织机构：</td>
				    		<td>
							    <c:choose>
									<c:when test="${empty(m.organizationId)}">
									  	<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree"/>
									  </c:when>
									<c:otherwise>
									  	<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree/${m.organizationId}/singleChecked"/>
									</c:otherwise>
								</c:choose>
								<form:input path="organizationId" data-options="url:'${organizationUrl}',editable:false,onSelect:function(node){$('#organizationName').val(node.text);}"/>
				  				<form:input path="organizationName" cssClass="validate[required,funcCall[funcValidate]]" cssStyle="display:none" data-prompt-position="bottomRight:140,70"/>
							</td>
				    	</tr>
				    	<tr id="tr-organization_job-2">
				    		<td>工作职务：</td>
				    		<td>
				    			<c:choose>
									<c:when test="${empty(m.jobId)}">
									  	<c:set var="jobUrl" value="${ctx}/security/organization/job/tree"/>
									  </c:when>
									<c:otherwise>
									  	<c:set var="jobUrl" value="${ctx}/security/organization/job/tree/${m.jobId}/singleChecked"/>
									</c:otherwise>
								</c:choose>
								<form:input path="jobId" data-options="url:'${jobUrl}',editable:false,onSelect:function(node){$('#jobName').val(node.text);}"/>
				  				<form:input path="jobName" cssClass="validate[required,funcCall[funcValidate]]" cssStyle="display:none" data-prompt-position="bottomRight:140,94"/>
				    		</td>
				    	</tr>
				  	</table>
				</fieldset>
				<h1 class="title">角色信息</h1>
		    	<fieldset>
		    		<table class="formtable">
		    			<tr>
							<td width="20%"><form:label path="roleIds">角色：</form:label></td>
							<td width="80%"><form:input path="roleIds" cssClass="easyui-combobox" data-options="url:'${ctx}/security/permission/role/canUse',method:'get',valueField:'id',textField:'name',panelHeight:140,editable:false,multiple:true,multiline:true,height:50" cssStyle="width:100%;"/></td>
				    	</tr>
		    		</table>
		    	</fieldset>
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
		$('#type').combobox({
			onSelect:function(record){
				typeSelect(record.value)
			}
		});
		
		typeSelect($('#type').combobox('getValue'));
		
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
	});
	
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});

	function funcValidate(field, rules, i, options){
		var value = $('#type').combobox('getValue');
		if (value == 'user' && $('#userName').val() == ''){
			$('#userName').validationEngine('showPrompt','不能为空','error'); 	
		} else if ((value == 'user_group' || value == 'organization_group') && $('#groupName').val() == ''){
			$('#groupName').validationEngine('showPrompt','不能为空','error');
		} else if (value == 'organization_job'){
			if ($('#organizationName').val() == ''){
				$('#organizationName').validationEngine('showPrompt','不能为空','error');
			}
			if ($('#jobName').val() == ''){
				$('#jobName').validationEngine('showPrompt','不能为空','error');
			}
		}
	}
	
	function typeSelect(value){
		$('tr[id^="tr-"]').css('display', 'none');
		if (value == 'user_group'){
			$('#span-group').text('用户分组');
			$('#tr-group').css('display', '');
		} else if (value == 'organization_group'){
			$('#span-group').text('组织机构分组');
			$('#tr-group').css('display', '');
		} else {
			$('tr[id*="tr-' + value + '"]').css('display', '');
		}
		
		if (value == 'user'){
			if ($('#id').val() == ''){
				$('#userName').val('');
			}
			$('#groupName').val('test');
			$('#organizationName').val('test');
			$('#jobName').val('test');
			$('#userId').combobox({
				width:150,
				panelWidth:150,
				panelHeight:130,
				url:'${ctx}/security/user/user/canUse',
				method:'get',
				valueField:'id',
				textField:'usernameAndRealname',
				editable:false,
				onLoadSuccess:function(){
					if ($('#userId').combobox('getValue') == 0){
						$('#userId').combobox('setValue', '');
					}
				},
				onSelect:function(record){
					$('#userName').val(record.username);
				}
			});
		} else if (value == 'user_group' || value == 'organization_group'){
			if ($('#id').val() == ''){
				$('#groupName').val('');
			}
			$('#userName').val('test');
			$('#organizationName').val('test');
			$('#jobName').val('test');
			var groupType = (value == 'user_group') ? 'user' : 'organization';
			$('#groupId').combobox({
				width:150,
				panelWidth:150,
				panelHeight:130,
				url:'${ctx}/security/group/group/canUse?type=' + groupType,
				method:'get',
				valueField:'id',
				textField:'name',
				editable:false,
				onLoadSuccess:function(){
					if ($('#groupId').combobox('getValue') == 0){
						$('#groupId').combobox('setValue', '');
					}
				},
				onSelect:function(record){
					$('#groupName').val(record.name);
				}
			});
		} else if (value == 'organization_job'){
			if ($('#id').val() == ''){
				$('#organizationName').val('');
				$('#jobName').val('');
			}
			$('#userName').val('test');
			$('#groupName').val('test');
			$('#organizationId, #jobId').combotree({
				width:150,
		    	panelWidth:200,
		    	panelHeight:130,
				onBeforeSelect : function(node){
					if (node.attributes.root){
						$.messager.alert('提示', '根节点不能被选中，请重新选择', 'info');
						return false;
					}
				}
			});
		}
	}
</script>
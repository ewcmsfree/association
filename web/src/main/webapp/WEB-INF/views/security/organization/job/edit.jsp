<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 机构"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/security/organization/job/save" method="post" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
	        	<form:hidden path="parentId"/>
	        	<form:hidden path="parentIds"/>
	        	<form:hidden path="weight"/>
	        	
				<h1 class="title">基本信息</h1>
		    	<fieldset>
			  		<table class="formtable">
		        		<tr>
				  			<td width="20%"><form:label path="name">职务名称：</form:label></td>
				  			<td width="80%"><form:input path="name" readonly="true" size="40"/></td>
			    		</tr> 	
			    		<tr>
	             			<td><form:label path="icon">图标：</form:label></td>
			  				<td>
		  						<span id="showIcon" class="${m.icon}"></span>&nbsp;
		  						<form:input path="icon" cssStyle="width:18px;"/>&nbsp;&nbsp;
		  						<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清除</a>
		  					</td>
			    		</tr>
			    		<tr>
			    			<td><form:label path="show">是否显示：</form:label></td>
			    			<td><form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
			    		</tr>
			  		</table>
		    	</fieldset>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a id="tb-submit" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);">提交</a>
	  		<a id="tb-reset" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);">重置</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	var currentNode = parent.$('#tree').tree('find', '${m.id}');
	$(function(){
		$('#icon').combobox({
			panelWidth:150,
			panelHeight:130,
			url:'${ctx}/system/icon/treeUseIcon',
			method:'get',
			valueField:'identity',
			textField:'identity',
			editable:false,
			formatter:function(row){
				return (row.iconType == 'css_class') ? '<span class="' + row.cssClass + '"/>' : '<span class="' + row.identity + '"/>';
			},
			onSelect:function(record){
				$('#showIcon').attr('class', record.identity);
				parent.$('#tree').tree('update', {
					target: currentNode.target,
					iconCls: record.identity
				});
			}
		});
		
		$('#tb-submit').bind('click', function(){
			parent.$('#tree').tree('update', {
				target: currentNode.target,
				text: $('#name').val()
			});
			$('#editForm').submit();
		});
		
		$('#tb-reset').bind('click', function(){
			parent.$('#tree').tree('update', {
				target: currentNode.target,
				text: $('#name').val(),
				iconCls: $('#icon').val()
			})
			$('#editForm').form('reset');
		});
		
		$('#tb-clear').bind('click', function(){
			$('#showIcon').attr('class', '');
			$('#icon').combobox('setValue', '');
			parent.$('#tree').tree('update', {
				target: currentNode.target,
				iconCls: ''
			});
		});
		
		var validationEngine = $("#editForm").validationEngine({
			promptPosition:'bottomRight',
			showOneMessage: true
		});
      	<ewcms:showFieldError commandName="m"/>
	});
</script>
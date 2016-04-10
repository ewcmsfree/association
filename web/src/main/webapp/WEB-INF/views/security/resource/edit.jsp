<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 资源"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/security/resource/table/save" commandName="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<form:hidden path="parentIds"/>
		    	<form:hidden path="weight"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		    		<tr>
			  			<td width="20%"><form:label path="name">名称：</form:label></td>
			  			<td width="80%"><form:input path="name" cssClass="validate[required]" placeholder="输入的名称"/></td>
			   		</tr>
		    		<tr>
	           			<td><form:label path="icon">图标：</form:label></td>
		  				<td>
		  					<span id="showIcon" class="${m.icon}"></span>&nbsp;
		  					<form:input path="icon" cssStyle="width:18px;"/>&nbsp;&nbsp;
		  					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清除</a>
		  				</td>
		    		</tr>
			  		<c:choose>
			    		<c:when test="${m.parentId eq 0}">
			    			<form:hidden path="parentId" value="0"/>
			    			<form:hidden path="identity" value=""/>
			    		</c:when>
			    		<c:otherwise>
							<tr>
						  		<td>父节点：</td>
						  		<td>
							    	<c:choose>
									  	<c:when test="${empty(m.id)}">
									  		<form:input path="parentId" cssClass="easyui-combotree" data-options="url:'${ctx}/security/resource/tree',editable:false,panelWidth:200,panelHeight:130,onSelect:function(node){$('#parentName').val(node.text);}"/>
						  					<form:input path="parentName" cssClass="validate[required]" cssStyle="display:none"/>
									  	</c:when>
									  	<c:otherwise>
									  		<form:hidden path="parentId"/>
									  		<c:out value="${m.parentName}"/>
									  	</c:otherwise>
									</c:choose>
					    		</td>
					    	</tr>
					    	<tr>
				    			<td><form:label path="identity">资源标识：</form:label></td>
				    			<td><form:input path="identity" placeholder="输入的资源标识"/></td>
				    		</tr>
				    		<c:set var="parentIdList" value="${fn:split(m.parentIds,'/')}"/>
				    		<c:if test="${fn:length(parentIdList)==2}">
				    			<tr>
				    				<td><form:label path="style">菜单风格</form:label></td>
				    				<td><form:select path="style" items="${styles}" itemLabel="info" cssClass="easyui-combobox" data-options="panelHeight:'auto'"/></td>
				    			</tr>
				    		</c:if>
					   		<tr>
					   			<td><form:label path="url">URL地址：</form:label></td>
					   			<td><form:input path="url" placeholder="输入的URL地址"/></td>
					   		</tr>
					   		<tr>
					   			<td><form:label path="show">是否显示</form:label></td>
					   			<td><form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/></td>
					   		</tr>
					   	</c:otherwise>
					</c:choose>
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
	    
		$('#icon').combobox({
			panelWidth:150,
			panelHeight:100,
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
			}
		});
		
		$('#tb-clear').bind('click', function(){
			$('#showIcon').attr('class', '');
			$('#icon').combobox('setValue', '');
		});
	});
	$.ewcms.refresh({grid : 'treegrid', operate : '${operate}', data : '${lastM}'});
</script>

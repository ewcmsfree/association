<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 学会分类"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/personnel/acadCategory/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
						<td width="20%"><form:label path="name">学会分类名称：</form:label></td>
						<td width="80%"><form:input path="name" cssClass="validate[required,ajax[ajaxNameCall]]"/></td>
					</tr>
		        		<tr>
				  			<td width="20%">组织机构：</td>
				  			<td width="80%">
							    <c:choose>
									<c:when test="${empty(m.organizationIds)}">
									  	<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree"/>
									  </c:when>
									<c:otherwise>
									  	<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree/${m.organizationIds}/multipleChecked"/>
									</c:otherwise>
								</c:choose>
				  				<div class="easyui-panel" style="padding:5px">
									<ul id="tt" class="easyui-tree" data-options="url:'${organizationUrl}',method:'get',animate:true,checkbox:true,cascadeCheck:false"></ul>
								</div>
								<form:hidden path="selectOrganizationIds"/>
				  			</td>
			    		</tr>					
				</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:formSubmit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	function formSubmit(){
		var nodes = $('#tt').tree('getChecked');
		var s = '';
		for(var i=0; i<nodes.length; i++){
			if (nodes[i].attributes.root){
				$.messager.alert('提示', '根节点不能被选中，请重新选择', 'info');
				return false;
			}
			if (s != '') s += ',';
			s += nodes[i].id;
		}
		$("#selectOrganizationIds").val(s);
		$('#editForm').submit();
	}
	
	$(function(){
		<c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
	    		$.validationEngineLanguage.allRules.ajaxNameCall= {
	                "url": "${ctx}/personnel/acadCategory/validate",
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
	
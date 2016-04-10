<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 就读学院情况"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/personnel/archive/educationExperience/${userId}/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
					<tr>
						<td><form:label path="graduationDate">毕业时间</form:label><label style="color: red;">&nbsp;*</label></td>
						<td>
							<input type="text" id="graduationDate_show" class="validate[required, custom[date]]" value="${m.graduationDate}" style="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
						    <form:input path="graduationDate" cssStyle="margin-left:0px;z-index:1;position:absolute;"/>
						</td>
					</tr>
		        	<tr>
						<td width="30%"><form:label path="universityName">就读院校名称</form:label><label style="color: red;">&nbsp;*</label></td>
						<td width="70%"><form:input path="universityName" cssClass="validate[required]"/></td>
					</tr>
					<tr>
						<td><form:label path="major">所学专业</form:label><label style="color: red;">&nbsp;*</label></td>
						<td><form:input path="major" cssClass="validate[required]"/></td>
					</tr>
					<tr>
						<td><form:label path="education">学历</form:label><label style="color: red;">&nbsp;*</label></td>
						<td><form:input path="education" cssClass="validate[required]"/></td>
					</tr>
					<tr>
						<td><form:label path="degree">学位</form:label><label style="color: red;">&nbsp;*</label></td>
						<td><form:input path="degree" cssClass="validate[required]"/></td>
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
		$('#graduationDate').datebox({
			editable:false,
			onSelect:function(date){
				$('#graduationDate_show').val(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
			}
		});
		
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
	
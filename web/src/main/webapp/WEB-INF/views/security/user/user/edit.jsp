<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 用户"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/security/user/user/save" commandName="m"  class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<form:hidden path="createDate"/>
		    	<form:hidden path="salt"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
				  		<td width="20%"><form:label path="username">用户名：</form:label></td>
				  		<td width="80%"><form:input path="username" cssClass="validate[required,custom[username],ajax[ajaxCall]]" placeholder="5到20个汉字、字母、数字或下划线" size="30"/></td>
			    	</tr>
			    	<tr>
	             		<td><form:label path="email">邮箱：</form:label></td>
			  			<td><form:input path="email" cssClass="validate[required,custom[email],ajax[ajaxCall]]" placeholder="如wu_zhijun@msn.com" size="30"/></td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="mobilePhoneNumber">手机号：</form:label></td>
			    		<td><form:input path="mobilePhoneNumber" cssClass="validate[required,custom[mobilePhoneNumber],ajax[ajaxCall]]" size="30"/></td>
			    	</tr>
			    	<c:choose>
			    		<c:when test="${empty(m.id)}">
			    			<tr>
			    				<td><form:label path="password">初始密码：</form:label></td>
			    				<td><form:password path="password" cssClass="validate[required,minSize[5],maxSize[100]]" placeholder="请输入至少5位的初始密码" size="30"/></td>
			    			</tr>
			    		</c:when>
			    		<c:otherwise>
			    			<form:hidden path="password"/>
			    		</c:otherwise>
			    	</c:choose>
			    	<tr>
			        	<td><form:label path="status">状态：</form:label></td>
			        	<td><form:radiobuttons path="status" items="${statusList}" itemLabel="info" cssClass="validate[required]" delimiter="&nbsp;"/></td>
			        </tr>
			        <tr>
			        	<td><form:label path="admin">是否管理员：</form:label></td>
			        	<td><form:radiobuttons path="admin" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]" delimiter="&nbsp;"/></td>
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
<script type="text/javascript" src="${ctx}/static/js/security/security.js"></script>
<script type="text/javascript">
	$(function(){
		<c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
	    		$.security.user.initValidator($("#editForm"));
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
    	</c:choose>
    	$('#archive').combobox({
			width:150,
			panelWidth:150,
			panelHeight:130,
			url:'${ctx}/personnel/archive/canUse',
			method:'get',
			valueField:'id',
			textField:'name',
			editable:false
		});
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});
</script>

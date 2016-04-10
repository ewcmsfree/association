<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 用户"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',border:false">	
		    <form:form id="editForm" method="post" commandName="user" cssClass="form-horizontal">
		        <form:hidden path="id"/>
		        <form:hidden path="salt"/>
		        <form:hidden path="password"/>
		        <h1 class="title">个人信息</h1>
		        <fieldset>
		        	<table class="formtable">
			        	<tr>
			        		<td width="20%"><form:label path="username">用户名：</form:label></td>
			        		<td width="80%"><form:input path="username" cssClass="validate[required,custom[username],ajax[ajaxCall]]" placeholder="5到20个汉字、字母、数字或下划线"/></td>
			        	</tr>
			        	<!-- 
			        	<tr>
			        		<td><form:label path="realname">实名：</form:label></td>
			        		<td><form:input path="realname" placeholder="输入真实姓名"/></td>
			        	</tr>
			        	 -->
			        	<tr>
			        		<td><form:label path="email">邮箱：</form:label></td>
			        		<td><form:input path="email" cssClass="validate[required,custom[email],ajax[ajaxCall]]" placeholder="如wu_zhijun@msn.com"/></td>
			        	</tr>
			        	<tr>
			        		<td><form:label path="mobilePhoneNumber">手机号：</form:label></td>
			        		<td><form:input path="mobilePhoneNumber" cssClass="validate[required,custom[mobilePhoneNumber],ajax[ajaxCall]]" placeholder="如18970986887"/></td>
			        	</tr>
			        	<tr>
			        		<td><form:label path="createDate">创建时间：</form:label></td>
			        		<td><form:input path="createDate" readonly="true" disabled="true"/></td>
			        	</tr>
			        	<tr>
			        		<td><form:label path="status">状态：</form:label></td>
			        		<td><form:radiobuttons path="status" items="${statusList}" itemLabel="info" cssClass="validate[required]" disabled="true" delimiter="&nbsp;"/></td>
			        	</tr>
			        	<tr>
			        		<td><form:label path="admin">是否管理员：</form:label></td>
			        		<td><form:radiobuttons path="admin" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]" disabled="true" delimiter="&nbsp;"/></td>
			        	</tr>
			        </table>
			    </fieldset>
			    <c:if test="${not empty lastOnline}">
			    <br/>
			    <h1 class="title">上一次登录情况</h1>
			    <fieldset>
			    	<table class="formtable">
			        	<tr>
			        		<td width="20%">登录IP：</td>
			        		<td width="80%"><input id="host" type="text" value="${lastOnline.host}"></td>
			        	</tr>
			        	<tr>
			        		<td>登录时间：</td>
			        		<td><input id="lastLoginTimestamp" type="text" value="<spring:eval expression='lastOnline.lastLoginTimestamp'/>"></td>
			        	</tr>
			        	<tr>
			        		<td>退出时间：</td>
			        		<td><input id="lastStopTimestamp" type="text" value="<spring:eval expression='lastOnline.lastStopTimestamp'/>"></td>
			        	</tr>
			        	<tr>
			        		<td>登录次数：</td>
			        		<td><input id="loginCount" type="text" value="${lastOnline.loginCount}"></td>
			        	</tr>
			        	<tr>
			        		<td>总在线时长：</td>
			        		<td><input id="totalOnlineTime" type="text" value="${lastOnline.totalOnlineTime}">秒</td>
			        	</tr>
			        </table>
			     </fieldset>
			     </c:if>
		    </form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset')">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/js/security/security.js"></script>
<script type="text/javascript">
	$(function(){
        $("#editForm").find(":checkbox,:radio").filter(":not(:checked)").next("label").andSelf().remove();
        $("#username,#createDate,#status,#admin,#host,#lastLoginTimestamp,#lastStopTimestamp,#loginCount,#totalOnlineTime").attr("disabled", true);
        
        $.security.user.initValidator($("#editForm"));
        <ewcms:showFieldError commandName="m"/>
	});
</script>
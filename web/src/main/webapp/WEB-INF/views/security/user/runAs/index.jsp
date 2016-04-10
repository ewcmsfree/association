<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="切换用户"/>
	<ewcms:showAlertMessage/>
	<c:if test="${needRefresh}">
		<script type="text/javascript">
			//var loginTag = "${user.username}";
			//<c:if test="${not empty user.realname}">
			//	loginTag += "(${user.realname})";
			//</c:if>
			//<c:if test="${isRunas}">
			//	loginTag += "[上一个身份：${previousUsername}&nbsp;|&nbsp;<a href='${ctx}/switchBack'>切换回该身份</a>]";
			//</c:if>
			//parent.$("#user-name").html(loginTag);
			parent.location.reload();
		</script>
	</c:if>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',fit:true" style="border:0;">
			<h1 class="title">切换身份</h1>
			<fieldset style="height:2%">
				<c:if test="${isRunas}">上一个身份：${previousUsername}&nbsp;|&nbsp;<a href="${ctx}/security/user/runAs/switchBack">切换回该身份</a></c:if>
			</fieldset>
			<h1 class="title">切换到其他用户</h1>
			<fieldset style="height:35%">
			<table id="tt-canswith" class="easyui-datagrid" data-options="url:'${ctx}/security/user/runAs/queryCanSwith',fit:true,nowrap:true,pagination:true,rownumbers:true,singleSelect:true,striped:true,pageSize:10">
				<thead>
					<tr>
						<th data-options="field:'username',width:200">用户名</th>
						<th data-options="field:'opMethod',width:400,align:'center'">操作</th>
					</tr>
				</thead>
			</table>
			</fieldset>
			<br/>
			<h1 class="title">授予身份给其他人</h1>
			<fieldset style="height:35%">
			<c:set var="toUserIds" value="${toUserIds}"/>
			<table id="tt-alluser" class="easyui-datagrid" data-options="url:'${ctx}/security/user/runAs/queryAllUser',toolbar:'#tb-alluser',fit:true,nowrap:true,pagination:true,rownumbers:true,singleSelect:true,striped:true,pageSize:10">
				<thead>
					<tr>
						<th data-options="field:'username',width:200">用户名</th>
						<th data-options="field:'opMethod',width:400,align:'center'">操作</th>
					</tr>
				</thead>
			</table>
			<div id="tb-alluser" style="padding:5px;height:auto;">
		        <div style="padding-left:5px;">
		        	<form id="queryform" style="padding:0;margin:0;">
		        		<table class="formtable">
		              		<tr>
		              			<td width="6%">用户名：</td>
		              			<td width="19%"><input type="text" name="LIKE_username" style="width:120px"/></td>
		    					<td width="6%">&nbsp;</td>
		    					<td width="19%">&nbsp;</td>
		    					<td width="6%">&nbsp;</td>
		    					<td width="19%">&nbsp;</td>
								<td width="25%" colspan="2">
		            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query({gridId:'#tt-alluser'});">查询</a>
		           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
		           				</td>
		           			</tr>
		           		</table>
		          </form>
		        </div>
			</div>
			</fieldset>
		</div>
	</div>
<ewcms:footer/>
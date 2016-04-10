<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/security/user/lastOnline/query',toolbar:'#tb',fit:true,nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'userName',width:150,sortable:true">用户</th>
				<th data-options="field:'uid',width:240,sortable:true">用户会话ID</th>
				<th data-options="field:'host',width:150,sortable:true">用户主机IP</th>
				<th data-options="field:'systemHost',width:150,sortable:true">系统主机IP</th>
				<th data-options="field:'lastLoginTimestamp',width:145,sortable:true">最后登录时间</th>
				<th data-options="field:'lastStopTimestamp',width:145,sortable:true">最后退出时间</th>
				<th data-options="field:'loginCount',width:70,sortable:true">登录次数</th>
				<th data-options="field:'totalOnlineTime',width:90,sortable:true">总在线时长</th>
				<th data-options="field:'userAgent',width:500,sortable:true">User-Agent</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="6%">用户名</td>
              			<td width="19%">
							<form:select id="userId" name="EQ_userId" path="userList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${userList}" itemValue="id" itemLabel="usernameAndRealname"/>
							</form:select>
						</td>
    					<td width="6%">退出时间</td>
    					<td width="44%"><input type="text" id="lastStopTimestamp" name="GTE_lastStopTimestamp" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/> 至 <input type="text" id="lastStopTimestamp" name="LTE_lastStopTimestamp" class="easyui-datetimebox" style="width:145px" data-options="editable:false"/></td>
						<td width="25%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
<ewcms:footer/>
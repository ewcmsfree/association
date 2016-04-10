<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="fit:true,url:'${ctx}/personnel/archive/history/${archiveId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10,fitColumns:true">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'opDate',width:145">操作时间</th>
			    <th data-options="field:'statusInfo',width:150">操作说明</th>
			    <th data-options="field:'reason',width:500">原因</th>
			</tr>
		</thead>
	</table>
<ewcms:footer/>
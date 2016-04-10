<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="fit:true,url:'${ctx}/personnel/archive/educationExperience/${userId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10,fitColumns:true">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'universityName',width:300">就读院校名称</th>
			    <th data-options="field:'major',width:400">所学专业</th>
			    <th data-options="field:'graduationDate',width:145">毕业时间</th>
			    <th data-options="field:'education',width:100">学历</th>
			    <th data-options="field:'degree',width:100">学位</th>
			</tr>
		</thead>
	</table>
<ewcms:footer/>
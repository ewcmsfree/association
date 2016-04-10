<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/personnel/archive/educationExperience/${userId}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10,fitColumns:true">
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
	<div id="tb" style="padding:5px;height:auto;">
		<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({src:'${ctx}/personnel/archive/educationExperience/${userId}/save',title:'新增',width:510,height:280});">新增</a>
		<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({src:'${ctx}/personnel/archive/educationExperience/${userId}/save',title:'修改',width:510,height:280});">修改</a>
		<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({src:'${ctx}/personnel/archive/educationExperience/${userId}/delete',title:'删除'});">删除</a>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
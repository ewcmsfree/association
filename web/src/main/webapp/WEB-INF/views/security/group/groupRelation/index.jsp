<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/security/group/groupRelation/${group.id}/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10,fitColumns:true">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <c:if test="${group.type eq 'user'}">
			    	<th data-options="field:'userId',width:100">用户编号</th>
			    	<th data-options="field:'username',width:200">用户名</th>
			    	<th data-options="field:'realname',width:200">实名</th>
			    </c:if>
			    <c:if test="${group.type eq 'organization'}">
				    <th data-options="field:'organizationId',width:100">组织机构编号</th>
				    <th data-options="field:'organizationName',width:200">组织机构</th>
			    </c:if>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:500,height:260});">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:500,height:260});">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="$.ewcms.query();">刷新</a>
		</div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'name',width:150,sortable:true">角色名称</th>
				<th data-options="field:'role',width:300,sortable:true">角色标识</th>
				<th data-options="field:'description',width:400">详细描述</th>
				<th data-options="field:'show',width:150,sortable:true,
						formatter:function(val,row){
							return val ? '可用' : '不可用';
						}">状态</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:450,height:180});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:450,height:180});">修改</a>
			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
			<a id="tb-status" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-status',iconCls:'icon-status'">状态</a>
		</div>
		<div id="menu-status" style="width:150px">
			<div id="menu-status-show" data-options="iconCls:'icon-status-show'" onclick="$.ewcms.status({status:true,info:'可用'});">可用</div>
			<div id="menu-status-hide" data-options="iconCls:'icon-status-hide'" onclick="$.ewcms.status({status:false,info:'不可用'});">不可用</div>
		</div>
        <div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="6%">角色名称：</td>
              			<td width="19%"><input type="text" name="LIKE_name" style="width:120px"/></td>
    					<td width="6%">角色标识：</td>
    					<td width="19%"><input type="text" name="LIKE_role" style="width:120px"/></td>
    					<td width="6%">状态：</td>
    					<td width="19%">
    						<form:select id="show" name="EQ_show" path="availableList" cssClass="easyui-combobox"  data-options="width:120,panelHeight:'auto',editable:false">
					  			<form:option label="所有" value=""/>
					  			<form:options items="${availableList}" itemLabel="info"/>
							</form:select>
						</td>
						<td width="25%" colspan="2">
            				<a href="javascript:void(0);" id="tb-query" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" id="tb-clear" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			toolbar:'#tb',
			fit:true,
			url:'${ctx}/security/permission/role/query',
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			fitColumns:true,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/security/permission/roleResourcePermission/' + rowData.id + '/index" frameborder="0" width="100%" height="315px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			}
		});
	});
</script>
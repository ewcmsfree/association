<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'name',width:200,sortable:true">分组名称</th>
				<th data-options="field:'type',width:300,sortable:true,
						formatter:function(val, row){
							return row.typeInfo;
						}">类型</th>
				<th data-options="field:'defaultGroup',width:100,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">是否默认分组</th>
				<th data-options="field:'show',width:100,sortable:true,
						formatter:function(val,row){
							return val ? '有效' : '无效';
						}">状态</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:450,height:180});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:450,height:180});">修改</a>
			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
			<a id="tb-defaultgroup" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-defaultgroup',iconCls:'icon-default-group'">默认分组</a>
			<a id="tb-status" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-status',iconCls:'icon-status'">状态</a>
		</div>
		<div id="menu-defaultgroup" style="width:150px">
			<div id="menu-defaultgroup-setup" data-options="iconCls:'icon-default-group-setup'" onclick="$.ewcms.status({src:'${ctx}/security/group/group/changeDefaultGroup',status:true,info:'设置默认分组'});">设置</div>
			<div id="menu-defaultgroup-cancel" data-options="iconCls:'icon-default-group-cancel'" onclick="$.ewcms.status({src:'${ctx}/security/group/group/changeDefaultGroup',status:false,info:'取消默认分组'});">取消</div>
		</div>    
		<div id="menu-status" style="width:150px">
			<div id="menu-status-show" data-options="iconCls:'icon-status-show'" onclick="$.ewcms.status({status:true,info:'有效'});">有效</div>
			<div id="menu-status-hide" data-options="iconCls:'icon-status-hide'" onclick="$.ewcms.status({status:false,info:'无效'});">无效</div>
		</div>
		<div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">分组名称</td>
              			<td width="23%"><input type="text" name="LIKE_name" style="width:140px"/></td>
    					<td width="5%">类型</td>
    					<td width="23%">
    						<form:select id="type" name="EQ_type" path="types" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${types}" itemLabel="info"/>
							</form:select>
						</td>
						<td width="5%">有效</td>
    					<td width="23%">
    						<form:select id="show" name="EQ_show" path="booleanList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option label="------请选择------" value=""/>
					  			<form:options items="${booleanList}" itemLabel="info" itemValue="value"/>
							</form:select>
						</td>
						<td width="16%" colspan="2">
            				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
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
			url:'${ctx}/security/group/group/query',
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
					content: '<iframe src="${ctx}/security/group/groupRelation/' + rowData.id + '/index" frameborder="0" width="100%" height="315px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			}
		});
	});
</script>

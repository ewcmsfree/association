<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="jdbc数据源"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'name',width:100">名称</th>
				<th data-options="field:'driver',width:200">驱动名</th>
				<th data-options="field:'connUrl',width:300">数据库连接URL</th>
				<th data-options="field:'userName',width:80">用户名</th>
				<th data-options="field:'remarks',width:300">说明</th>
				<th data-options="field:'operation',width:120,align:'center',formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:650,height:295});" href="javascript:void(0);">新增</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:650,height:295});" href="javascript:void(0);">修改</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">名称</td>
              			<td width="23%"><input type="text" name="LIKE_name" style="width:140px;"/></td>
            			<td width="5%">驱动名</td>
              			<td width="23%"><input type="text" name="LIKE_driver" style="width:140px;"/></td>
            			<td width="5%">连接URL</td>
              			<td width="23%"><input type="text" name="LIKE_connUrl" style="width:140px;"/></td>
              			<td width="16%" colspan="2">
            				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();" href="javascript:void(0);">查询</a>
           					<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');" href="javascript:void(0);">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/system/externalds/jdbc/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			border:false,
			onLoadSuccess:function(row){
				$('.connect').linkbutton({text:'连接测试',plain:true,iconCls:'icon-connect'});
			}
		});
	});

	function formatOperation(val, row){
		return '<a class="connect" onclick="parent.isConnect(' + row.id + ')" href="javascript:void(0);">连接测试</a>';
	}
</script>
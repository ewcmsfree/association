<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'text',width:400">名称</th>
				<th data-options="field:'identity',width:200,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.identity;
						}">资源标识</th>
				<th data-options="field:'url',width:700,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.url;
						}">URL地址</th>
				<th data-options="field:'styleInfo',width:70,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.styleInfo;
						}">菜单风格</th>
				<th data-options="field:'show',width:60,
						formatter:function(val,row){
							if (row.attributes != null) return row.attributes.show ? '显示' : '不显示';
						}">状态</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({grid:'treegrid', title:'新增',src:'${ctx}/security/resource/table/save',width:490,height:259});">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({grid:'treegrid', title:'修改',src:'${ctx}/security/resource/table/save',width:490,height:259});">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({grid:'treegrid'})">删除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="$('#tt').treegrid('reload');">刷新</a>
			<a id="tb-weight" class="easyui-linkbutton" data-options="iconCls:'icon-weight',plain:true">优化排序</a>
			<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-status',iconCls:'icon-status'">状态</a>
		</div>
		<div id="menu-status" style="width:150px">
			<div id="menu-status-show" data-options="iconCls:'icon-status-show'" onclick="$.ewcms.status({status:true,info:'显示',grid:'treegrid'});">显示</div>
			<div id="menu-status-hide" data-options="iconCls:'icon-status-hide'" onclick="$.ewcms.status({status:false,info:'不显示',grid:'treegrid'});">不显示</div>
		</div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/treegrid-dnd.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').treegrid({
			toolbar:'#tb',
			fit:true,
			url:'${ctx}/security/resource/table',
			idField:'id',
			treeField:'text',
			animate:true,
			lines:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			onLoadSuccess:function(row){
				$(this).treegrid('enableDnd', row?row.id:null);
			},
			onDrop:function(targetRow,sourceRow,point){
				var parseUrl = '${ctx}/security/resource/' + sourceRow.id + '/' + targetRow.id + '/' + point + '/move';
				$.post(parseUrl,{},function(data){
			        if(data.success){
			        	$('#tt').treegrid('reload', targetRow.target);
			        } else {
			        	$('#tt').treegrid('reload');
			        }
			        $.messager.alert('提示', data.message, 'info');
				});
			}
		});
		
		$('#tb-weight').bind('click', function(){
			$.messager.confirm('提示', '确认要优化排序吗?', function(r){
				if (r){
					$.post('${ctx}/security/resource/reweight',{},function(data){
						if (data.success){
							$('#tt').treegrid('reload');
						}
						$.messager.alert('提示', data.message, 'info');
					});
				}
			});
		});
	});
</script>

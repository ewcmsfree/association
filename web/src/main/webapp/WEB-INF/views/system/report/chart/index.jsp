<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="图形报表"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'name',width:100">名称</th>
				<th data-options="field:'dsName',width:200,
						formatter:function(val,row){
							if (row.baseDs == null){
			    				return '默认数据源';
			    			}else{
			    				return row.baseDs.name;
			    			}
						}">数据源名称</th>
				<th data-options="field:'typeDescription',width:200">图型类型</th>
				<th data-options="field:'createDate',width:145">创建时间</th>
				<th data-options="field:'updateDate',width:145">更新时间</th>
				<th data-options="field:'remarks',width:300">说明</th>
				<th data-options="field:'operation',width:200,align:'center',formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'新增',width:740,height:510});" href="javascript:void(0);">新增</a>
			<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:740,height:510});" href="javascript:void(0);">修改</a>
			<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">名称</td>
              			<td width="23%"><input type="text" name="LIKE_name" style="width:140px;"/></td>
						<td width="5%">&nbsp;</td>
    					<td width="23">&nbsp;</td>
    					<td width="5%">&nbsp;</td>
    					<td width="23">&nbsp;</td>
              			<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
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
			url:'${ctx}/system/report/chart/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/system/report/parameter/chart/' + rowData.id + '/index" frameborder="0" width="100%" height="315px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			},
			onLoadSuccess:function(row){
				$('.previewCls').linkbutton({test:'预览',plain:true,iconCls:'icon-preview'});
				$('.schedulingCls').linkbutton({test:'定时设置',plain:true,iconCls:'icon-scheduling'});
			}
		});
	});
	function formatOperation(val, row){
		var operation = '<a class="previewCls" onclick="preview(' + row.id + ')" href="javascript:void(0);">预览</a>|';
		operation += '<a class="schedulingCls" onclick="scheduling(' + row.id + ')" href="javascript:void(0);">定时设置</a>';
		return operation;
	}
	function preview(id){
		$.ewcms.openWindow({
			windowId:'#edit-window',
			iframeId : '#editifr', 
			src : '${ctx}/system/report/show/chart/' + id + '/paraset', 
			width:550,
			height:200,
			title:'参数选择'
		});
	}
	function scheduling(id){
		$.ewcms.openWindow({
			windowId:'#edit-window',
			iframeId : '#editifr',
			src : '${ctx}/system/scheduling/jobinfo/false/edit?className=com.ewcms.system.scheduling.generate.job.report.entity.JobReport&objectId=' + id + '&reportType=chart',
			width : 1040,
			height : 560,
			title : '图形报表定时器设置'
		});
	}
</script>
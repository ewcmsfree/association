<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="报表存储"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'name',width:300">名称</th>
				<th data-options="field:'updateDate',width:145">更新时间</th>
				<th data-options="field:'publishDate',width:145">发布时间</th>
				<th data-options="field:'description',width:300">说明</th>
				<th data-options="field:'operation',width:70,align:'center',formatter:formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove',toggle:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
			<a id="tb-publish" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-publish',toggle:true" href="javascript:void(0);">发布</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">名称</td>
              			<td width="23%"><input type="text" name="LIKE_Name" style="width:140px;"/></td>
						<td width="5%">&nbsp;</td>
    					<td width="23">&nbsp;</td>
    					<td width="5%">&nbsp;</td>
    					<td width="23">&nbsp;</td>
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
			url:'${ctx}/system/report/repository/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			onLoadSuccess:function(row){
				$('.downloadCls').linkbutton({text:'下载',plain:true,iconCls:'icon-download'});
			}
		});
	});
	
	function formatOperation(val, row){
		return '<a class="downloadCls" onclick="download(' + row.id + ');" href="javascript:void(0);">下载</a>';
	}

	function download(id){
		window.open('${ctx}/system/report/repository/' + id + '/download','popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,scrollbars=yes,left=' + (window.screen.width - 1280)/ 2 + ',top=' + (window.screen.height - 700) / 2);
	}
	
	$('#tb-publish').bind('click', function(){
		var rows = $('#tt').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择发布的资源记录', 'info');
			return;
		}
		var parameter = '';
		var rows = $('#tt').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			parameter = parameter + '&selections=' + rows[i].id;
		}
		$.post('${ctx}/system/report/repository/publish', {}, function(data) {
			
		});
	});
</script>
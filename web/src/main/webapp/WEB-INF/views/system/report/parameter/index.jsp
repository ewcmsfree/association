<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="报表参数"/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/system/report/parameter/${reportType}/${reportId}/query',toolbar:'#tb',fit:true,nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'enName',width:200">参数名</th>
				<th data-options="field:'cnName',width:200">中文名</th>
				<th data-options="field:'typeDescription',width:100">数据输入方式</th>
				<th data-options="field:'value',width:200">辅助数据设置</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="$.ewcms.edit({title:'修改',width:700,height:240});" href="javascript:void(0);">修改</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">参数名</td>
              			<td width="23%"><input type="text" name="LIKE_enName" style="width:140px;"/></td>
						<td width="5%">中文名</td>
              			<td width="23%"><input type="text" name="LIKE_cnName" style="width:140px;"/></td>
            			<td width="5%">&nbsp;</td>
              			<td width="23%">&nbsp;</td>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="报表分组"/>
	<table id="tt"></table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="$.ewcms.add({title:'重设',width:700,height:200});" href="javascript:void(0);">重设</a>
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">名称</td>
              			<td width="23%"><input type="text" name="LIKE_name" style="width:140px;"/></td>
						<td width="5%">&nbsp;</td>
              			<td width="23%">&nbsp;</td>
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
<script type="text/javascript">
	$(function(){
		$('#tt').propertygrid({
			toolbar:'#tb',
			fit:true,
	        width:500,
	        url: '${ctx}/system/report/category/detail/${categoryId}/query',
	        showGroup:true,
	        scrollbarSize:0,
	        singleSelect:false,
	        frozenColumns:[[
				{field:"ck",checkbox:true}
			]],
	        columns:[[
	            {field:'name',title:'名称',width:150},
	            {field:"value",title:'描述',width:260}
	        ]]
		});
	});
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="发件箱"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',width:60">编号</th>
			    <th data-options="field:'userId',hidden:true">用户</th>
			    <th data-options="field:'title',width:400">标题</th>
			    <th data-options="field:'sendTime',width:145">发送时间</th>
			    <th data-options="field:'typeInfo',width:40">类型</th>
			    <th data-options="field:'userNames',width:300">接收用户</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<!-- <a id="menu-add" class="easyui-menubutton" data-options="menu:'#menu-addsub',iconCls:'icon-add',plain:true" href="javascript:void(0);">新增</a> -->
			<a id="menu-add-message" class="easyui-linkbutton" data-options="iconCls:'icon-subscription-add',plain:true" onclick="$.ewcms.add({width:650,height:300});" href="javascript:void(0);">发送信息</a>
			<a id="menu-remove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
		</div>
		<!-- 
		<div id="menu-addsub" style="width:80px;">
            <div id="menu-add-message" onclick="$.ewcms.add({title:'新增-消息',width:650,height:300});">信息</div>
            <div id="menu-add-subscription" data-options="iconCls:'icon-subscription-add'">订阅</div>
        </div>
         -->
        <div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
                		<td width="6%">名称：</td>
                		<td width="19%"><input type="text" name="LIKE_name" style="width:120px;"/></td>
                		<td width="6%">类型：</td>
                		<td width="19%">
                			<form:select id="type" name="EQ_type" path="sendTypeList" cssClass="easyui-combobox" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="-----请选择-----"/>
					  			<form:options items="${sendTypeList}" itemLabel="info"/>
							</form:select>
						</td>
                		<td width="6%">发送时间：</td>
                		<td width="19%"><input type="text" id="sendTimeStart" name="GTE_sendTime" class="easyui-datebox" data-options="editable:false" style="width:100px"/>&nbsp;至&nbsp;<input type="text" id="sendTimeEnd" name="LTE_sendTime" class="easyui-datebox" data-options="editable:false" style="width:100px"/></td>
						<td width="25%" colspan="2">
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
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/personal/message/send/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			singleSelect:false,
			striped:true,
			pageSize:20,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return detailGridData(rowData);
			},
			onExpandRow: function(index,row){  
				$('#tt').datagrid('fixDetailRowHeight',index);  
			}
		});
		$('#menu-add-subscription').bind('click', function(){
			var rows = $('#tt').datagrid("getSelections");
			if (rows.length == 0){
				$.messager.alert("提示","请选择新增订阅的记录","info");
				return;
			}
			if (rows.length > 1){
				$.messager.alert("提示","只能选择一个记录进行新增订阅","info");
				return;
			}
			if (rows[0].type == 'SUBSCRIPTION'){
				var url = '${ctx}/personal/message/content/edit/' + rows[0].id;
				$.ewcms.openWindow({windowId:'#edit-window', iframeId:'#editifr', src : url, width : 550,height : 200,title : '新增-订阅'});
			}else{
				$.messager.alert('提示','只能是订阅的记录才能再新增订阅内容','info');
			}
		});
	});
	
	function detailGridData(rowData){
		var htmls = [];
		if (rowData.msgContents.length == 0) {
			htmls.push('<div style="padding:5px 0">没有内容记录!</div>');
		} else {
			htmls.push('<div style="padding:5px 0;"><div class="datagrid-header" style="height:25px;">');
			htmls.push('<div style="float:left;display: block;">');
			htmls.push('<table cellspacing="0" cellpadding="0" border="0" style="height: 25px;">');
			htmls.push('<tr style="height: 25px">');
			htmls.push('<td><div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 20px; text-align: center;"><span></span><span class="datagrid-sort-icon">&nbsp;</span></div></td>');
			htmls.push('<td><div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 1000px; text-align: left;"><span>内容</span><span class="datagrid-sort-icon">&nbsp;</span></div></td>');
			if (rowData.type == 'SUBSCRIPTION'){
				htmls.push('<td><div class="datagrid-cell" style="width: 24px; text-align: center;"><span></span><span class="datagrid-sort-icon">&nbsp;</span></div></td>');
			}
			htmls.push('</tr>');
			htmls.push('</table>');
			htmls.push('</div>');
			htmls.push('</div>');
			htmls.push('<div class="datagrid-body">');
			for ( var i = 0; i < rowData.msgContents.length; i++) {
				htmls.push('<table cellspacing="0" cellpadding="0" border="0"><tr style="height: 21px">'
								+ '<td>'
								+ '<div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 20px; text-align: center;">'
								+ (rowData.msgContents.length - i)
								+ '</td>'
								+ '</div>'
								+ '<td>'
								+ '<div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 1000px; text-align: left;">'
								+ rowData.msgContents[i].detail
								+ '</div>'
								+ '</td>');
				if (rowData.type == 'SUBSCRIPTION'){
					htmls.push('<td><div class="datagrid-cell" style="width: 24px; text-align: center;"><span>'
								+ '<a href="javascript:void(0);" onclick="delSubscription(' + rowData.msgContents[i].id + ')" style="text-decoration:none;">删除</a>'
								+ '</span></div></td>');
				}
				htmls.push('</tr></table>');
			}
			htmls.push('</div></div>');
		}
		return htmls.join("");
	}
	
	function delSubscription(id){
		$.post('${ctx}/personal/message/send/delete', {'selections':id}, function(result){
			if (result.success){
				$('#tt').datagrid('reload');
			}
			$.messager.alert('提示', result.message, 'info');
		});
	}
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="公告/订阅"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',width:80">编号</th>
			    <th data-options="field:'userName',hidden:true">用户</th>
			    <th data-options="field:'title',width:400">标题 </th>
			    <th data-options="field:'sendTime',width:145">发送时间 </th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
		</div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
                		<td width="6%">名称：</td>
                		<td width="19%"><input type="text" name="LIKE_title" style="width:120px;"/></td>
                		<td width="6%">&nbsp;</td>
                		<td width="19%">&nbsp;</td>
                		<td width="6%">&nbsp;</td>
                		<td width="19%">&nbsp;</td>
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
			url:'${ctx}/personal/message/more/${type}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			singleSelect:false,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return detailGridData(rowData);
			},
			onExpandRow: function(index,row){  
				$('#tt').datagrid('fixDetailRowHeight',index);  
			}
		});
	});
	
	function detailGridData(rowData){
		var htmls = [];
		if (rowData.msgContents.length == 0) {
			htmls.push('<div style="padding:5px 0">没有内容记录!</div>');
		} else {
			htmls.push('<div style="padding:5px 0;"><div class="datagrid-header" style="height:22px;">');
			htmls.push('<div style="float:left;display: block;">');
			htmls.push('<table cellspacing="0" cellpadding="0" border="0" style="height: 23px;">');
			htmls.push('<tr style="height: 21px">');
			htmls.push('<td><div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 20px; text-align: center;"><span></span><span class="datagrid-sort-icon">&nbsp;</span></div></td>');
			htmls.push('<td><div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 1000px; text-align: left;"><span>内容</span><span class="datagrid-sort-icon">&nbsp;</span></div></td>');
			if (rowData.type == 'SUBSCRIPTION'){
				htmls.push('<td><div class="datagrid-cell" style="width: 24px; text-align: center;"><span></span><span class="datagrid-sort-icon"><a href="javascript:void(0);" onclick="subscribe(' + rowData.id + ');return false;" onfocus="this.blur();">订阅</a></span></div></td>');
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
								+ '</span></div></td>');
				}
				htmls.push('</tr></table>');
			}
			htmls.push('</div></div>');
		}
		return htmls.join("");
	}
	
	function subscribe(subscribeUrl, id){
		$.post('${ctx}/personal/message/detail/subscribe', {'id':id}, function(data) {
			if (data == 'own'){
				$.messager.alert('提示','您不能订阅自已发布的信息！','info');
				return;
			}
			if (data == 'exist'){
				$.messager.alert('提示','您已订阅了此信息，不需要再订阅！','info');
				return;
			}
			if (data == 'false'){
				$.messager.alert('提示','订阅信息失败！','info');
				return;
			}
			if (data == 'true'){
				$.messager.alert('提示','订阅成功！','info');
				return;
			}
		});
	}
</script>
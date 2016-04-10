<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="备忘录列表"/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/personal/memoranda/query',fit:true,toolbar:'#tb',nowrap:true,pagination:true,rownumbers:true,singleSelect:false,striped:true,pageSize:20">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',width:50,hidden:true">编号</th>
				<th data-options="field:'title',width:200">标题</th>
				<th data-options="field:'noteDate',width:70">日期</th>
				<th data-options="field:'warn',width:33,align:'center',
					    formatter:function(val, row){
					    	return val ? '是' : '否';
					    }">提醒</th>
				<th data-options="field:'warnTime',width:60">提醒时间</th>
				<th data-options="field:'frequencyInfo',width:80">提醒频率</th>
				<th data-options="field:'beforeInfo',width:80">提前时间</th>
				<th data-options="field:'fireTime',width:145">触发时间</th>
				<th data-options="field:'missRemind',width:60,align:'center',
					    formatter:function(val, row){
					    	return val ? '是' : '否';
					    }">错过提醒</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<!-- 
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:700,height:360});" href="javascript:void(0);">修改</a>
			 -->
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:parent.location.reload();parent.$('#edit-window').window('close');" href="javascript:void(0);" >关闭</a>
		</div>
		<div style="padding-left:5px;">
			<form id="queryform" style="padding:0;margin:0;">
				<table class="formtable">
					<tr>
						<td width="6%">标题：</td>
						<td width="19%"><input type="text" name="LIKE_title" style="width:120px;"/></td>
						<td width="6%">日期：</td>
						<td width="19%"><input type="text" id="noteDate" name="GTE_noteDate" class="easyui-datebox" style="width:100px"/>&nbsp;至&nbsp;<input type="text" id="noteDate" name="LTE_noteDate" class="easyui-datebox" style="width:100px"/></td>
						<td width="6%">提醒频率：</td>
						<td width="19%">
							<form:select id="frequency" name="EQ_frequency" path="frequencyList" cssClass="easyui-combobox" data-options="editable:false,panelHeight:'auto'">
			          			<form:option value="" label="------请选择------"/>
			          			<form:options items="${frequencyList}" itemLabel="info"/>
			        		</form:select>
						</td>
						<td width="25%" colspan="2">
            				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();" href="javascript:void(0);">查询</a>
           					<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');" href="javascript:void(0);">清除</a>
           					<a id="tb-more" class="easyui-linkbutton" href="javascript:void(0);"><span id="showHideLabel">更多...</span></a>
                		</td>
              		</tr>
              		<tr>
                		<td>提醒频率：</td>
                		<td>
                			<form:select id="before" name="EQ_before" path="beforeList" cssClass="easyui-combobox" data-options="editable:false,panelHeight:'auto'">
			          			<form:option value="" label="------请选择------"/>
			          			<form:options items="${beforeList}" itemLabel="info"/>
			        		</form:select>
			    		</td>
                		<td>是否提醒：</td>
                		<td>
                			<form:select id="warn" name="EQ_warn" path="booleanList" cssClass="easyui-combobox" data-options="editable:false,panelHeight:'auto'">
			          			<form:option value="" label="------请选择------"/>
			          			<form:options items="${booleanList}" itemLabel="info"/>
			        		</form:select>
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
		$("form table tr").next("tr").hide();
		$('#tb-more').bind('click', function(){
	       	var showHideLabel_value = $('#showHideLabel').text();
	    	$('form table tr').next('tr').toggle();
	     	if (showHideLabel_value == '收缩'){
	     		$('#showHideLabel').text('更多...');
	    	}else{
	    		$('#showHideLabel').text('收缩');
	    	}
	    	$('#tt').datagrid('resize');
	    });
	});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/security/user/online/query',toolbar:'#tb',fit:true,nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',width:240,sortable:true">用户会话ID</th>
				<th data-options="field:'userName',width:150,sortable:true">用户</th>
				<th data-options="field:'host',width:150,sortable:true">用户主机IP</th>
				<th data-options="field:'systemHost',width:150,sortable:true">系统主机IP</th>
				<th data-options="field:'startTimestamp',width:145,sortable:true">登录时间</th>
				<th data-options="field:'lastAccessTime',width:145,sortable:true">最后访问时间</th>
				<th data-options="field:'status',width:70,sortable:true,
						formatter:function(val, row){
							return row.statusInfo;
						}">状态</th>
				<th data-options="field:'userAgent',width:500,sortable:true">User-Agent</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="tb-forcelogout" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-force-logout',plain:true">强制退出</a>
		</div>
        <div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="6%">用户名</td>
              			<td width="19%">
							<form:select id="userId" name="EQ_userId" path="userList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${userList}" itemValue="id" itemLabel="usernameAndRealname"/>
							</form:select>
						</td>
    					<td width="6%">&nbsp;</td>
    					<td width="19%">&nbsp;</td>
    					<td width="6%">&nbsp;</td>
    					<td width="19%">&nbsp;</td>
						<td width="25%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tb-forcelogout').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    	
	    	if(rows.length == 0){
		        $.messager.alert('提示','请选择要强制退出的记录','info');
		        return;
		    }
		    
		    var parameter='';
		    $.each(rows,function(index,row){
		    	parameter += 'selections=' + row.id +'&';
		    });
		    
		    $.messager.confirm("提示","确定要强制退出所选记录吗?",function(r){
		        if (r){
		            $.post('${ctx}/security/user/online/forceLogout', parameter ,function(data){
		            	if (data.success){
	    					$('#tt').datagrid('clearSelections');
	    					$('#tt').datagrid("reload");
		            	}
    					$.messager.alert('强制退出', data.message, 'info');
		            });
		        }
		    });
		});
	});
</script>
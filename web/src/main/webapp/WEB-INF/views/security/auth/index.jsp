<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/security/auth/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20,fitColumns:true">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'typeInfo',width:120">类型</th>
			    <th data-options="field:'userId',hidden:true">用户编号</th>
			    <th data-options="field:'userName',width:150,
			    		formatter:function(val,row){
			    			return (row.type == 'user') ? val : '/';
			    		}">用户名</th>
			    <th data-options="field:'groupId',hidden:true">用户组编号</th>
			    <th data-options="field:'groupName',width:150,
			    		formatter:function(val,row){
			    			return (row.type == 'user_group') ? val : '/';
			    		}">用户组名称</th>
			    <th data-options="field:'organizationGroupName',width:150,
			    		formatter:function(val,row){
			    			return (row.type == 'organization_group') ? row.groupName : '/';
			    		}">组织机构组</th>
				<th data-options="field:'organizationId',hidden:true">组织机构编号</th>
				<th data-options="field:'organizationName',width:150,
						formatter:function(val,row){
							return (row.type == 'organization_job') ? val : '/';
						}">组织机构</th>
				<th data-options="field:'jobId',hidden:true">工作职务编号</th>				
				<th data-options="field:'jobName',width:100,
						formatter:function(val,row){
							return (row.type == 'organization_job') ? val : '/';
						}">工作职务</th>
				<th data-options="field:'roleNames',width:600">角色名称</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="tb-add" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:650,height:420});">新增</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:650,height:420});">修改</a>
			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
		</div>
        <div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="6%">用户编号：</td>
              			<td width="19%"><input type="text" name="EQ_userId" style="width:120px"/></td>
    					<td width="6%">用户组编号：</td>
    					<td width="19%"><input type="text" name="EQ_groupId" style="width:120px"/></td>
    					<td width="6%">状态：</td>
    					<td width="19%">
    						<form:select id="type" name="EQ_type" path="types" cssClass="easyui-combobox"  data-options="width:120,panelHeight:'auto',editable:false">
					  			<form:option label="所有" value=""/>
					  			<form:options items="${types}" itemLabel="info"/>
							</form:select>
						</td>
						<td width="25%" colspan="2">
            				<a href="javascript:void(0);" id="tb-query" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" id="tb-clear" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
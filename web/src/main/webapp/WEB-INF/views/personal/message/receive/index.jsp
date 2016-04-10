<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="收件箱"/>
	<table id="tt" class="easyui-datagrid" data-options="url:'${ctx}/personal/message/receive/query',toolbar:'#tb',fit:true,nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'userId',hidden:true">用户</th>
			    <th data-options="field:'title',width:800,formatter:formatTitle">标题 </th>
			    <th data-options="field:'read',width:40,formatter:formatRead">标记 </th>
			    <th data-options="field:'readTime',width:145">读取时间</th>
			    <!-- 
			    <th data-options="field:'subscription',width:40,formatter:formatSubscription">订阅</th>
			     -->
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="menu-markread" class="easyui-menubutton" data-options="menu:'#menu-markreadsub',iconCls:'icon-markread',plain:true"  href="javascript:void(0);">标记</a>
		  	<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
		</div>
		<div id="menu-markreadsub" style="width:80px;">
            <div id="menu-mark-read" onclick="$.ewcms.status({status:true,info:'已读'});">已读</div>
            <div id="menu-mark-unread" onclick="$.ewcms.status({status:false,info:'未读'});">未读</div>
        </div>
        <div  style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
                		<td width="6%">名称：</td>
                		<td width="19%"><input type="text" name="LIKE_name" style="width:120px;"/></td>
                		<!-- 
                		<td width="6%">是否订阅：</td>
                		<td width="19%">
                  			<form:select id="info" name="EQ_info" path="booleanList" cssClass="easyui-combobox" data-options="panelHeight:'auto',editable:false">
			        			<form:option value="" label="------请选择------"/>
			        			<form:options items="${booleanList}" itemLabel="info"/>
			      			</form:select>
						</td>
						 -->
                		<td width="6%">发送时间：</td>
                		<td width="19%"><input type="text" id="readTimeStart" name="GTE_readTime" class="easyui-datebox" data-options="editable:false" style="width:100px"/>&nbsp;至&nbsp;<input type="text" id="readTimeEnd" name="LTE_readTime" class="easyui-datebox" data-options="editable:false" style="width:100px"/></td>
                		<td width="%6">&nbsp;</td>
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
<script type="text/javascript">
	function formatRead(val, row){
		 if (val){
			 return "&nbsp;<img src='${ctx}/static/image/msg/msg_read.gif' width='13px' height='13px' title='接收消息，已读'/>";
		 }else{
			 return "&nbsp;<img src='${ctx}/static/image/msg/msg_unread.gif' width='13px' height='13px' title='接收消息，未读'/>";
		 }
	}
	function formatTitle(val, row){
		return row.msgContent ? '<a href="javascript:void(0);" onclick="showRecord(' + row.id + ')" onfocus="this.blur();">' + row.msgContent.title + '</a>' : '';
	}
	function formatSubscription(val, row){
		return (val) ? "&nbsp;<img src='${ctx}/static/theme/icons/ok.png' width='13px' height='13px'/>" : "";
	}
	
	function showRecord(id){
		$.ewcms.openWindow({windowId:'#edit-window',iframeId:'#editifr', src : '${ctx}/personal/message/detail/' + id + '/message/index',width:700,height:400,title:'内容'});
	}
</script>	

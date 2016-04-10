<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="ehcache监控 - 明细"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/monitor/ehcache/${cacheName}/detail/query',nowrap:false,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'key',width:200">key</th>
			    <th data-options="field:'objectValue',width:300">值</th>
			    <th data-options="field:'hitCount',width:60,halign:'left',align:'right'">命中次数</th>
			    <th data-options="field:'size',width:80,halign:'left',align:'right'">大小</th>
			    <th data-options="field:'latestOfCreationAndUpdateTime',width:160">最后创建/更新时间</th>
			    <th data-options="field:'lastAccessTime',width:145">最后访问时间</th>
			    <th data-options="field:'expirationTime',width:145">过期时间</th>
			    <th data-options="field:'timeToIdle',width:150,halign:'left',align:'right'">timeToIdle（秒）</th>
			    <th data-options="field:'timeToLive',width:150,halign:'left',align:'right'">timeToLive（秒）</th>
			    <th data-options="field:'version',width:100,halign:'left',align:'right'">version</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a id="tb-remove" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});">删除</a>
			<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true">清空</a>
		</div>
        <div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">key</td>
              			<td width="23%"><input type="text" name="CUSTOM_key" style="width:140px"/></td>
    					<td width="5%">&nbsp;</td>
    					<td width="23">&nbsp;</td>
    					<td width="5%">&nbsp;</td>
    					<td width="23">&nbsp;</td>
						<td width="16%" colspan="2">
            				<a href="javascript:void(0);" id="tb-query" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" id="tb-clear" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tb-clear').bind('click', function(){
			
		})
	});
</script>
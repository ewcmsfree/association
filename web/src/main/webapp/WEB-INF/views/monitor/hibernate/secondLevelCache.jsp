<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="Hibernate监控 "/>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',split:false,border:false" style="height:45%">
			<table id="ttProperty"></table>
		</div>
		<div data-options="region:'center',split:false" style="height:55%">
			<table id="ttData" class="easyui-datagrid" data-options="title:'详细',fit:true,url:'${ctx}/monitor/hibernate/secondLevelCache/queryData',nowrap:true,pagination:false,rownumbers:true,striped:true,fitColumns:true,border:false">
				<thead>
					<tr>
					    <th data-options="field:'regionName',width:500">名称</th>
					    <th data-options="field:'hitPercent',width:100,sortable:true">总命中率</th>
					    <th data-options="field:'hitCount',width:100,sortable:true">命中次数</th>
					    <th data-options="field:'missCount',width:100,sortable:true">失效次数</th>
					    <th data-options="field:'putCount',width:100,sortable:true">往缓存中Put总次数</th>
					    <th data-options="field:'sizeInMemory',width:100,sortable:true">所占内存大小</th>
					    <th data-options="field:'elementCountInMemory',width:100,sortable:true">内存中的实体数</th>
					    <th data-options="field:'elementCountOnDisk',width:100">磁盘中的实体数</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#ttProperty').propertygrid({
			title:'总体',
			fit:true,
	        url:'${ctx}/monitor/hibernate/secondLevelCache/queryProperty',
	        showGroup:true,
	        scrollbarSize:0,
	        singleSelect:true,
	        border:false,
	        nowrap:false,
	        columns:[[
	            {field:'name',title:'属性',width:400},
	            {field:'value',title:'值',width:1000}
	        ]]
	    });
	});
</script>
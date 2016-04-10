<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="Hibernate监控 "/>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:false" style="height:45%">
			<table id="ttProperty"></table>
		</div>
		<div data-options="region:'center',split:false" style="height:55%">
			<table id="ttData" class="easyui-datagrid" data-options="title:'详细',fit:true,url:'${ctx}/monitor/hibernate/queryCache/queryData',nowrap:false,pagination:false,rownumbers:true,striped:true,fitColumns:true,border:false">
				<thead>
					<tr>
					    <th data-options="field:'query',width:500" rowspan="2">名称</th>
					    <th data-options="field:'hitPercent',width:100" rowspan="2">总命中率</th>
					    <th data-options="field:'hitCount',width:100" rowspan="2">命中次数</th>
					    <th data-options="field:'missCount',width:100" rowspan="2">失效次数</th>
					    <th data-options="field:'putCount',width:120" rowspan="2">往缓存中Put总次数</th>
					    <th data-options="field:'executionCount',width:150" rowspan="2">执行次数（实际查数据库）</th>
					    <th data-options="field:'executionRowCount',width:100" rowspan="2">执行返回行数</th>
					    <th colspan="3">执行时间（毫秒）</th>
					</tr>
					<tr>
						<th data-options="field:'executionAvgTime',width:60">平均</th>
						<th data-options="field:'executionMaxTime',width:60">最长</th>
						<th data-options="field:'executionMinTime',width:60">最短</th>
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
	        url:'${ctx}/monitor/hibernate/queryCache/queryProperty',
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
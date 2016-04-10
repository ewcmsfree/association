<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="ehcache监控"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'cacheName',width:200">缓存名称</th>
			    <th data-options="field:'cacheHitPercent',width:200,halign:'left',align:'right'">总命中率</th>
			    <th data-options="field:'cacheHits',width:100,halign:'left',align:'right'">命中次数</th>
			    <th data-options="field:'cacheMisses',width:100,halign:'left',align:'right'">失效次数</th>
			    <th data-options="field:'objectCount',width:100,halign:'left',align:'right'">缓存总对象数</th>
			    <th data-options="field:'searchPerSecond',width:200,halign:'left',align:'right'">最后一秒查询完成的执行数</th>
			    <th data-options="field:'averageSearchTime',width:230,halign:'left',align:'right'">最后一秒采样的平均执行时间（毫秒）</th>
			    <th data-options="field:'averageGetTime',width:150,halign:'left',align:'right'">平均获取时间（毫秒）</th>
			</tr>
		</thead>
	</table>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/monitor/ehcache/query',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/monitor/ehcache/' + rowData.cacheName + '/detail/index" frameborder="0" width="100%" height="315px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			}
		});
	});
</script>
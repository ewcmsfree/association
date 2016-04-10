<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="Hibernate监控 "/>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',split:false,border:false" style="height:45%">
			<table id="ttProperty"></table>
		</div>
		<div data-options="region:'center',split:false,border:false" style="height:55%">
			<div class="easyui-layout" data-options="fit:true,border:false">
				<div data-options="region:'north',split:false,border:false" style="height:50%">
					<table id="ttEntityData" class="easyui-datagrid" data-options="title:'实体缓存详细',fit:true,url:'${ctx}/monitor/hibernate/entityCache/queryData',nowrap:true,pagination:false,rownumbers:true,striped:true,fitColumns:true,border:false">
						<thead>
							<tr>
							    <th data-options="field:'entityName',width:500">名称</th>
							    <th data-options="field:'deleteCount',width:100">实体delete次数</th>
							    <th data-options="field:'insertCount',width:100">实体insert次数</th>
							    <th data-options="field:'updateCount',width:100">实体update次数</th>
							    <th data-options="field:'loadCount',width:100">实体load次数</th>
							    <th data-options="field:'fetchCount',width:100">实体fetch次数</th>
							    <th data-options="field:'optimisticFailureCount',width:150">optimistic lock失败次数</th>
							</tr>
						</thead>
					</table>
				</div>
				<div data-options="region:'center',split:false,border:false" style="height:50%">
					<table id="ttCollectionData" class="easyui-datagrid" data-options="title:'集合缓存详细',fit:true,url:'${ctx}/monitor/hibernate/collectionCache/queryData',nowrap:true,pagination:false,rownumbers:true,striped:true,fitColumns:true,border:false">
						<thead>
							<tr>
							    <th data-options="field:'collectionRoleName',width:500">名称</th>
							    <th data-options="field:'removeCount',width:100">集合remove次数</th>
							    <th data-options="field:'updateCount',width:100">集合update次数</th>
							    <th data-options="field:'loadCount',width:100">集合load次数</th>
							    <th data-options="field:'fetchCount',width:100">集合fetch次数</th>
							    <th data-options="field:'recreateCount',width:100">集合recreated次数</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#ttProperty').propertygrid({
			title:'总体',
			fit:true,
	        url:'${ctx}/monitor/hibernate/entityAndCollectionCache/queryProperty',
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
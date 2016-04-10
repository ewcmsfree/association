<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="缓存控制"/>
	<table class="formtable">
		<tr>
			<td style="text-align:center;">
				<a id="tb-evictAll" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">失效整个二级缓存</a>
				<a id="tb-clearAll" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清空二级缓存，重新计算</a>
			</td>
		</tr>
	</table>
	<hr/>
	<h4>失效实体缓存：</h4>
	<table class="formtable">
		<tr>
			<td width="10%">实体编号：</td>
			<td width="90%"><input type="text" id="entityIds" name="entityIds" size="90" placeholder="多个之间用逗号分隔"></td>
		</tr>
		<tr>
			<td>实体名：</td>
			<td><input type="text" id="entityNames" name="entityNames"/></td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:center;">
				<a id="tb-entity-ok" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>
				<a id="tb-entity-all" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">失效所有实体缓存</a>
			</td>
		</tr>
	</table>
	<hr/>
	<h4>失效集合缓存：</h4>
	<table class="formtable">
		<tr>
			<td width="10%">集合所属实体编号：</td>
			<td width="90%"><input type="text" id="collectionEntityIds" name="collectionEntityIds" size="90" placeholder="多个之间用逗号分隔"></td>
		</tr>
		<tr>
			<td>集合名称：</td>
			<td><input type="text" id="collectionRoleNames" name="collectionRoleNames"/></td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:center;">
				<a id="tb-collection-ok" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>
				<a id="tb-collection-all" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">失效所有集合缓存</a>
			</td>
		</tr>
	</table>
	<hr/>
	<h4>失效查询缓存：</h4>
	<table class="formtable">
		<tr>
			<td width="10%">查询名称：</td>
			<td width="90%"><input type="text" id="queries" name="queries"/></td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:center;">
				<a id="tb-query-ok" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>
				<a id="tb-query-all" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">失效所有查询缓存</a>
			</td>
		</tr>
	</table>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tb-evictAll').bind('click', function(){
			$.messager.confirm('失效整个二级缓存', '确认失效整个二级缓存吗？', function(r){
				if (r){
					$.post('${ctx}/monitor/hibernate/evictAll',{}, function(result){
						$.messager.alert('失效整个二级缓存', result.message, 'info');
					});
				}
			});
		});
		
		$('#tb-clearAll').bind('click', function(){
			$.messager.confirm('清空整个二级缓存', '确认清空整个二级缓存,重新计算吗？', function(r){
				if (r){
					$.post('${ctx}/monitor/hibernate/clearAll',{}, function(result){
						$.messager.alert('清空整个二级缓存', result.message, 'info');
					});
				}
			});
		});
		
		$('#tb-entity-ok').bind('click', function(){
			$.messager.confirm('失效实体缓存', '确认被选择的实体缓存失效吗？', function(r){
				if (r){
					$.post('${ctx}/monitor/hibernate/evictEntity',{'entityIds':$('#entityIds').val(),'entityNames':$('#entityNames').combobox('getValues')},function(result){
						$('#entityIds').val('');
						$('#entityNames').combobox('reload');
						$.messager.alert('失效实体缓存', result.message, 'info');
						
					});
				}
			});
		});
		
		$('#tb-entity-all').bind('click', function(){
			$.messager.confirm('失效所有实体缓存', '确认失效所有实体缓存吗？', function(r){
				if (r){
					$.post('${ctx}/monitor/hibernate/evictEntity',{},function(result){
						$('#entityIds').val('');
						$('#entityNames').combobox('reload');
						$.messager.alert('失效所有实体缓存', result.message, 'info');
					});
				}
			});
		});
		
		$('#tb-collection-ok').bind('click', function(){
			$.messager.confirm('失效集合缓存', '确认被选择的集合缓存失效吗？', function(r){
				if (r){
					$.post('${ctx}/monitor/hibernate/evictCollection',{'collectionEntityIds':$('#collectionEntityIds').val(),'collectionRoleNames':$('#collectionRoleNames').combobox('getValues')},function(result){
						$('#collectionEntityIds').val('');
						$('#collectionRoleNames').combobox('reload');
						$.messager.alert('失效集合缓存', result.message, 'info');
					});
				}
			});
		});
		
		$('#tb-collection-all').bind('click', function(){
			$.messager.confirm('失效所有集合缓存', '确认失效所有集合缓存吗？', function(r){
				if (r){
					$.post('${ctx}/monitor/hibernate/evictCollection',{},function(result){
						$('#collectionEntityIds').val('');
						$('#collectionRoleNames').combobox('reload');
						$.messager.alert('失效所有集合缓存', result.message, 'info');
					});
				}
			});
		});
		
		$('#tb-query-ok').bind('click', function(){
			$.messager.confirm('失效查询缓存', '确认被选择的查询缓存失效吗？', function(r){
				if (r){
					$.post('${ctx}/monitor/hibernate/evictQuery',{'queries':$('#queries').combobox('getValues')},function(result){
						$('#queries').combobox('reload');
						$.messager.alert('失效查询缓存', result.message, 'info');
					});
				}
			});
		});
		
		$('#tb-query-all').bind('click', function(){
			$.messager.confirm('失效所有查询缓存', '确认失效所有查询缓存吗？', function(r){
				if (r){
					$.post('${ctx}/monitor/hibernate/evictQuery',{},function(result){
						$('#queries').combobox('reload');
						$.messager.alert('失效所有查询缓存', result.message, 'info');
					});
				}
			});
		});
		
		$('#entityNames').combobox({
			url:'${ctx}/monitor/hibernate/entityName/canUse',
			valueField:'id',
			textField:'text',
			editable:false,
			multiple:true,
			height:100,
			width:600,
			multiline:true
		});
		
		$('#collectionRoleNames').combobox({
			url:'${ctx}/monitor/hibernate/collectionRoleName/canUse',
			valueField:'id',
			textField:'text',
			editable:false,
			multiple:true,
			height:100,
			width:600,
			multiline:true
		});
		
		$('#queries').combobox({
			url:'${ctx}/monitor/hibernate/query/canUse',
			valueField:'id',
			textField:'text',
			editable:false,
			multiple:true,
			height:100,
			width:600,
			multiline:true
		});
	});
</script>
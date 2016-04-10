<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="执行SQL"/>
	<%@ include file="/WEB-INF/views/jspf/import-codemirror-css.jspf" %>
	<table id="tt"></table>
	<div id="tb" style="padding:8px;height:auto;">
		<form id="queryform" style="padding:0;margin:0;">
			<table style="width:100%">
				<tr>
					<td>请输入SQL（不支持DDL/DCL执行）<textarea id="CUSTOM_sql" name="CUSTOM_sql" class="validate[required]" style="resize:none;width:100%;height:200px;"></textarea></td>
				</tr>
				<tr>
					<td style="text-align:center;">
			        	<a href="javascript:void(0);" id="tb-query" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
<ewcms:footer/>
<%@ include file="/WEB-INF/views/jspf/import-codemirror-js.jspf" %>
<script type="text/javascript">
	$(function(){
		var editor = CodeMirror.fromTextArea(document.getElementById("CUSTOM_sql"), {
			mode: 'sql',
			lineNumbers: true,
			lineWrapping: true,
	        matchBrackets: true,
	        indentUnit: 4,
	        extraKeys: {"Ctrl-Q": function(cm){ cm.foldCode(cm.getCursor()); }},
	        foldGutter: true,
	        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
		});
		
		var validationEngine = $("#queryform").validationEngine({
			promptPosition:'bottomLeft',
			showOneMessage: true
		});
		
		$('#tt').datagrid({
			toolbar:'#tb',
			fit:true,
			url:null,
			nowrap:false,
			pagination:true,
			rownumbers:true,
			striped:true,
			fitColumns:true,
			striped:true,
			pageSize:20,
			onBeforeLoad:function(param){
				param['parameters']=$('#queryform').serializeObject();
			}
		});
		
		$('#tb-query').bind('click', function(){
			$.post('${ctx}/monitor/db/sqlShowColumn', {'sql' : editor.getValue()}, function(data){
				var options = {};
				if (data){
					options.columns = eval('[[' + data + ']]');
				}
				options.url = '${ctx}/monitor/db/sqlQuery';
				options.queryParams = {
					sql:editor.getValue()
				};
				$('#tt').datagrid(options);
				$('#tt').datagrid('reload');
			});
		})
		
	});
</script>
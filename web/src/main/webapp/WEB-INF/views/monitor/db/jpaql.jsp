<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="执行SQL"/>
	<%@ include file="/WEB-INF/views/jspf/import-codemirror-css.jspf" %>
	<table id="tt"></table>
	<div id="tb" style="padding:8px;height:auto;">
		<form id="queryform" style="padding:0;margin:0;">
			<table style="width:100%">
				<tr>
					<td>请输入QL<textarea id="jpaql" name="jpaql" class="validate[required]" style="resize:none;width:100%;height:200px;"></textarea></td>
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
		var editor = CodeMirror.fromTextArea(document.getElementById("jpaql"), {
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
			nowrap:false,
			pagination:true,
			rownumbers:false,
			striped:true,
			fitColumns:true,
			striped:true,
			pageSize:20,
			url:null
		});
		
		$('#tb-query').bind('click', function(){
			var gridOpts = $('#tt').datagrid('options');   
			$.post('${ctx}/monitor/db/jpaqlQuery', {'jpaql':editor.getValue(), 'pageNumber':gridOpts.pageNumber,'pageSize':gridOpts.pageSize}, function(result){
				if (result){
					gridOpts.columns = eval('[[' + result.columns + ']]');
					$('#tt').datagrid(gridOpts);
					$('#tt').datagrid('loadData', result.data);
					
					var pager = $('#tt').datagrid('getPager');
					pager.pagination({
						onSelectPage: function(pageNumber, pageSize){
							$.post('${ctx}/monitor/db/jpaqlQuery', {'jpaql':editor.getValue(), 'pageNumber':pageNumber,'pageSize':pageSize}, function(result){
								$(pager).pagination('loading');
						  		$('#tt').datagrid('loadData', result.data);
						  		$(pager).pagination('loaded');
							});
						}
					});
					
				}
			});
			
		})
		
	});
</script>
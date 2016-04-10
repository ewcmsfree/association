<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="Runtime监控 "/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',width:70">Thread ID</th>
			    <th data-options="field:'name',width:800">Thread Name</th>
			    <th data-options="field:'state',width:100">Thread State</th>
			    <th data-options="field:'lockName',width:800">Thread Lock Name</th>
			    <th data-options="field:'lockOwnerName',width:800">Thread Lock Owner Name</th>
			    <th data-options="field:'cpuTime',width:120">Thread CPU Time</th>
			    <th data-options="field:'depth',width:100">Thread Depth</th>
			</tr>
		</thead>	
	</table>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			fit:true,
			url:'${ctx}/monitor/jvm/thread/query',
			nowrap:false,
			pagination:false,
			rownumbers:true,
			striped:true,
			border:false,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div style="padding:2px"><table id="ddv-' + rowIndex + '"></table></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-'+rowIndex).datagrid({
					fitColumns:true,  
                    singleSelect:true,  
                    height:400,
                    columns:[[{field:'element',title:'element',width:800}]],
                    data:rowData.elements,
                    onResize:function(){  
                        $('#tt').datagrid('fixDetailRowHeight',rowIndex);  
                    },  
                    onLoadSuccess:function(){  
                        setTimeout(function(){  
                            $('#tt').datagrid('fixDetailRowHeight',index);  
                        },0);  
                    }  
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			}
		});
	});
</script>
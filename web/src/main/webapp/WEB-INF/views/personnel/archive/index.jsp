<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="用户"/>
	<table id="tt">
		<thead data-options="frozen:true">
			<tr>
			    <th data-options="field:'ck',checkbox:true"/>
			    <th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'name',width:150,formatter:formatName">姓名</th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th data-options="field:'sexInfo',width:35,sortable:true">性别</th>
				<th data-options="field:'birthday',width:80,sortable:true">出生日期</th>
				<th data-options="field:'mobilePhoneNumber',width:80">手机</th>
				<th data-options="field:'statusInfo',width:80,sortable:true">状态</th>
				<th data-options="field:'deleted',width:70,sortable:true,
						formatter:function(val,row){
							return val ? '是' : '否';
						}">禁止操作</th>
				<th data-options="field:'organizationNames',width:300">学会名称</th>
				<th data-options="field:'currentStateNames',width:160">目前情况</th>
				<th data-options="field:'workAddress',width:300">工作单位</th>
				<th data-options="field:'duties',width:200">职务</th>
				<th data-options="field:'acadOffice',width:100">学会任职</th>
				<th data-options="field:'titles',width:100">技术职称</th>
				<th data-options="field:'nowProfessional',width:200">现从事专业</th>
				<th data-options="field:'specialty',width:200">特长专业</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div style="margin-bottom:2px">
			<a id="tb-through" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-through',iconCls:'icon-status'">审核</a>
			<a id="tb-status" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-status',iconCls:'icon-operate'">操作</a>
			<a id="tb-print" href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#menu-print',iconCls:'icon-print'">打印</a>
		</div>
		<div id="menu-through" style="width:150px">
			<div id="menu-through-true" data-options="iconCls:'icon-ok'" onclick="$.ewcms.status({status:'true',info:'专家信息审核通过',prompt:false});">通过</div>
			<div id="menu-through-false" data-options="iconCls:'icon-cancel'" onclick="$.ewcms.status({status:'false',info:'专家信息审核不通过',prompt:true});">不通过</div>
		</div>
		<div id="menu-status" style="width:150px">
			<div id="menu-remove" data-options="iconCls:'icon-lock'" onclick="$.ewcms.remove({title:'禁止'});">禁止用户</div>
			<div id="menu-recycle" data-options="iconCls:'icon-recycle'">解封用户</div>
		</div>
		<div id="menu-print" style="width:150px">
			<div id="menu-print-archive" data-options="iconCls:''">登记表</div>
			<div id="menu-print-statistics" data-options="iconCls:''">汇总表</div>
		</div>
        <div style="margin-bottom:2px">
        	<form id="queryform">
        		<table class="formtable">
              		<tr>
              			<td width="5%">姓名</td>
              			<td width="23%"><input type="text" name="LIKE_name" style="width:140px"/></td>
    					<td width="5%">性别</td>
    					<td width="23">
							<form:select id="sex" name="EQ_sex" path="sexList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${sexList}" itemLabel="info"/>
							</form:select>
						</td>
						<td width="5%">状态</td>
    					<td width="23%">
							<form:select id="sex" name="EQ_status" path="statusList" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${statusList}" itemLabel="info"/>
							</form:select>
						</td>
						<td width="16%" colspan="2">
            				<a id="tb-query" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a id="tb-clear" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清除</a>
           					<a id="tb-more" href="javascript:void(0);" class="easyui-linkbutton"><span id="showHideLabel">更多...</span></a>
           				</td>
           			</tr>
           			<c:if test="${isAdmin}">
           			<tr>
           				<td>分类</td>
           				<td>
           					<form:select id="category" name="CUSTOM_category" path="categoryList">
           						<form:option value="" label="------请选择------"/>
					  			<form:options items="${categoryList}" itemLabel="name" itemValue="id"/>
           					</form:select>
           				</td>
           				<td>机构名称</td>
           				<td colspan="3">
           					<input id="organizationIds" name="CUSTOM_organizationsIds"/>
           				</td>
           			</tr>
           			</c:if>
           			<tr>
           				<td>出生日期</td>
           				<td><input type="text" id="birthday" name="GTE_birthday" class="easyui-datebox" style="width:100px" data-options="editable:false"/> 至 <input type="text" id="birthday" name="LTE_birthday" class="easyui-datebox" style="width:100px" data-options="editable:false"/></td>
           				<td>禁止操作</td>
           				<td>
           					<form:select id="deleted" name="EQ_deleted" path="booleanList" cssClass="easyui-combobox"  cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="------请选择------"/>
					  			<form:options items="${booleanList}" itemLabel="info"/>
							</form:select>
           				</td>
           				<td>现从事专业</td>
              			<td><input type="text" name="LIKE_nowProfessional" style="width:140px"/></td>
           				<td>专业特长</td>
              			<td><input type="text" name="LIKE_specialty" style="width:140px"/></td>
           			</tr>
           		</table>
          </form>
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/easyui/ext/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/personnel/archive/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			pagination:true,
			rownumbers:true,
			striped:true,
			pageSize:20,
			striped:true,
			view : detailview,
			detailFormatter : function(rowIndex, rowData) {
				return '<div id="ddv-' + rowIndex + '" style="padding:2px"></div>';
			},
			onExpandRow: function(rowIndex, rowData){
				$('#ddv-' + rowIndex).panel({
					border:false,
					cache:false,
					content: '<iframe src="${ctx}/personnel/archive/history/' + rowData.id + '/index" frameborder="0" width="100%" height="315px" scrolling="auto"></iframe>',
					onLoad:function(){
						$('#tt').datagrid('fixDetailRowHeight',rowIndex);
					}
				});
				$('#tt').datagrid('fixDetailRowHeight',rowIndex);
			}
		});
		
		$("form table tr").next("tr").hide();
		$('#tb-more').bind('click', function(){
	       	var showHideLabel_value = $('#showHideLabel').text();
	    	$('form table tr').next('tr').toggle();
	     	if (showHideLabel_value == '收缩'){
	     		$('#showHideLabel').text('更多...');
	    	}else{
	    		$('#showHideLabel').text('收缩');
	    	}
	    	$('#tt').datagrid('resize');
	    });
		
		$('#menu-recycle').bind('click', function(){
			var rows = $('#tt').datagrid('getSelections');
	    	
	    	if(rows.length == 0){
		        $.messager.alert('提示','请选择解封的记录','info');
		        return;
		    }
		    
		    var parameter='';
		    $.each(rows,function(index,row){
		    	parameter += 'selections=' + row.id +'&';
		    });
		    
			$.messager.confirm('提示', '确定要解封所选记录吗?', function(r){
				if (r){
					$.post('${ctx}/personnel/archive/recycle', parameter ,function(data){
						if (data.success){
	    					$('#tt').datagrid('clearSelections');
	    					$('#tt').datagrid("reload");
						}
						$.messager.alert('解封用户', data.message, 'info');
					});
				}
			});
		});
		
		$('#organizationIds').combobox({
			//multiple:true,
			panelHeight:200,
			editable:false,
			width:300,
			url:'${ctx}/personnel/acadCategory/canUse',
			method:'get',
			valueField:'id',
			textField:'name'
		});
		
		$('#category').combobox({
			panelHeight:'auto',
			editable:false,
			width:140,
			onSelect:function(record){
				var organizationUrl = '${ctx}/personnel/acadCategory/canUse';
				if (record.value > 0){
					organizationUrl += '?categoryId=' + record.value;
				}
				$('#organizationIds').combobox({
					url:organizationUrl
				});
			}
		});
		
		$('#tb-clear').bind('click', function(){
			$('#organizationId').combobox({
				url:'${ctx}/personnel/acadCategory/canUse'
			});
			$('#queryform').form('reset');
		});
		
		$('#menu-print-archive').bind('click', function(){
			window.open('${ctx}/personnel/archive/printArchive','popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,scrollbars=yes,left=' + (window.screen.width - 1280)/ 2 + ',top=' + (window.screen.height - 700) / 2);
		});
		
		$('#menu-print-statistics').bind('click', function(){
			window.open('${ctx}/personnel/archive/printSummary','popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,scrollbars=yes,left=' + (window.screen.width - 1280)/ 2 + ',top=' + (window.screen.height - 700) / 2);
		});
	});
	
	function formatName(val, row){
		return row.name + '(<a href="javascript:void(0);" onclick="$.ewcms.openWindow({src:\'${ctx}/personnel/archive/' + row.id + '/view\',width:1000,height:500,title:\'查看\'});">详细</a>)';
	}
</script>

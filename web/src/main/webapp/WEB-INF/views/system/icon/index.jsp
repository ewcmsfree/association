<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<%@ page import="com.ewcms.common.web.controller.entity.TreeIconCls"%>

<ewcms:head title="图标设置"/>
	<table id="tt" class="easyui-datagrid" data-options="toolbar:'#tb',fit:true,url:'${ctx}/system/icon/query',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:20">
		<thead>
			<tr>
			    <th data-options="field:'ck',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'identity',width:300">标识符</th>
			    <th data-options="field:'iconTypeInfo',width:80">类型</th>
			    <th data-options="field:'cssClass',width:100,formatter:formatCssClass">css类</th>
			    <th data-options="field:'icon',width:50,halign:'center',align:'center',formatter:formatIcon">图标</th>
			    <th data-options="field:'spriteSrc',width:400,formatter:formatSpriteSrc">图标文件</th>
			    <th data-options="field:'positon',width:100,formatter:formatPosition">位置(left×top)</th>
			    <th data-options="field:'size',width:125,formatter:formatSize">大小(width×height)</th>
			    <th data-options="field:'style',width:200,formatter:formatStyle">其他css属性</th>
			    <th data-options="field:'description',width:300">描述</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
		<div class="toolbar" style="margin-bottom:2px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$.ewcms.add({title:'新增',width:700,height:360});" href="javascript:void(0);">新增</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="$.ewcms.edit({title:'修改',width:700,height:360});" href="javascript:void(0);">修改</a>
			<a id="tb-gencss" class="easyui-linkbutton" data-options="iconCls:'icon-gen-css',plain:true" href="javascript:void(0);">重新生成</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="$.ewcms.remove({title:'删除'});" href="javascript:void(0);">删除</a>
		</div>
		<div style="padding-left:5px;">
        	<form id="queryform" style="padding:0;margin:0;">
        		<table class="formtable">
              		<tr>
              			<td width="5%">标识符</td>
              			<td width="23%"><input type="text" name="LIKE_identity" style="width:140px"/></td>
    					<td width="5%">类型</td>
    					<td width="23%">
							<form:select id="iconType" name="EQ_iconType" path="types" cssClass="easyui-combobox" cssStyle="width:140px;" data-options="panelHeight:'auto',editable:false">
					  			<form:option value="" label="所有"/>
					  			<form:options items="${types}" itemLabel="info"/>
							</form:select>
						</td>
    					<td width="5%">描述</td>
    					<td width="23%"><input type="text" name="LIKE_description" style="width:140px"/></td>
						<td width="16%" colspan="2">
            				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="$.ewcms.query();">查询</a>
           					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:$('#queryform').form('reset');">清除</a>
           				</td>
           			</tr>
           		</table>
        	</form>
        </div>
	</div>
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	function formatCssClass(val, row){
		if (row.iconType == 'css_class'){
			return row.cssClass ? row.cssClass : '无';
		} else {
			return '无';
		}
	}
	
	function formatIcon(val, row){
		if (row.iconType == 'css_class'){
			return '<i class="' + row.cssClass +'<%=TreeIconCls.tree_suffix%>"></i>';
		} else {
			return '<i class="' + row.identity + '<%=TreeIconCls.tree_suffix%>" title="生成后的图标"></i>'
		}
	}
	
	function formatSpriteSrc(val, row){
		if (row.iconType == 'css_sprite'){
			return (row.spriteSrc) ? row.spriteSrc : '无';
		} else {
			return '无';
		}
	}
	
	function formatPosition(val, row){
		if (row.iconType == 'css_sprite'){
			return (row.left && row.top) ? row.left + '×' + row.top : '无';
		} else {
			return '无';
		}
	}
	
	function formatSize(val, row){
		if (row.iconType == 'upload_file' || row.iconType == 'css_sprite'){
			return (row.width && row.height) ? row.width + '×' + row.height : '无';
		} else {
			return '无';
		}
	}
	
	function formatStyle(val, row){
		if (row.iconType == 'upload_file' || row.iconType == 'css_sprite'){
			return (row.style) ? row.style : '无';
		} else {
			return '无';
		}
	}
	
	$(function(){
		$('#tb-gencss').click(function(){
			 $.messager.confirm("提示","确定要重新生成CSS文件吗?",function(r){
			        if (r){
			            $.post('${ctx}/system/icon/genCssFile', {} ,function(data){
			            	$.messager.alert("提示", data.message, "info");
			            });
			        }
			 });
		})
	})
</script>
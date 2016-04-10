<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="组织机构 - 职务"/>
    <div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" style="width:180px;">
			<ul id="tree"></ul>
			<div id="treeopmenu" class="easyui-menu" style="width:150px; padding:4px 0 8px 0;">
				<div id="collapse-button">收缩
					<div>
						<div id="collapse-current-button">当前</div>
						<div id="collapse-all-button">所有</div>
					</div>
				</div>
				<div id="expand-button">展开
					<div>
						<div id="expand-current-button">当前</div>
						<div id="expand-all-button">所有</div>
					</div>
				</div>
				<div id="refresh-button" data-options="iconCls:'icon-reload'">刷新</div>
				<div id="weight-button" data-options="iconCls:'icon-weight'">优化排序</div>
				<div class="menu-sep"></div>
				<div id="add-button" data-options="iconCls:'icon-add'">创建</div>
				<div id="rename-button" data-options="iconCls:'icon-undo'">重命名</div>
				<div id="edit-button" data-options="iconCls:'icon-edit'">编辑</div>
				<div id="cut-button" data-options="iconCls:'icon-cut'"><span id="cut-text">剪切</span></div>
				<div id="parse-button" data-options="iconCls:'icon-paste'" style="display:none;">
					<span id="parse-text">粘贴</span>
					<div>
						<div id="parse-top-button">节点之前</div>
						<div id="parse-bottom-button">节点之后</div>
						<div id="parse-append-button">节点之内</div>
					</div>
				</div>
				<div class="menu-sep"></div>
				<div id="delete-button" data-options="iconCls:'icon-remove'">删除</div>
			</div>
		</div>
		<div data-options="region:'center'" style="overflow:hidden;">
			<iframe id="editifr"  name="editifr" class="editifr" width="100%" src="${ctx}/security/organization/job/description"></iframe>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/js/tree.js"></script>
<script type="text/javascript">
	var _tree = new Tree('#tree');
	$(function(){
		_tree.init({
			baseUrl : '${ctx}/security/organization/job/'
		});
	});
</script>

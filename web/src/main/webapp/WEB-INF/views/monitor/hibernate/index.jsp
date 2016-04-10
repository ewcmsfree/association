<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="Hibernate监控 - 标签页"/>
	<div id="tab-hibernate" class="easyui-tabs" data-options="fit:true,border:true,tabPosition:'right',headerWidth:120">
		<div title="总述" style="padding:2px;overflow:hidden;">
			<iframe id="edithibifr" name="edithibifr" class="editifr" src="${ctx}/monitor/hibernate/hibernate/index"></iframe>
		</div>			
		<div title="二级缓存统计" style="padding:2px;overflow:hidden;">
			<iframe id="editsecifr"  name="editsecifr" class="editifr" src="${ctx}/monitor/hibernate/secondLevelCache/index"></iframe> 
		</div>
		<div title="查询缓存统计" style="padding:2px;overflow:hidden;">
			<iframe id="editqueryifr"  name="editqueryifr" class="editifr" src="${ctx}/monitor/hibernate/queryCache/index"></iframe> 
		</div>
		<div title="实体&集合缓存统计" style="padding:2px;overflow:hidden;">
			<iframe id="editentifr"  name="editentifr" class="editifr" src="${ctx}/monitor/hibernate/entityAndCollectionCache/index"></iframe> 
		</div>
		<div title="缓存控制" style="padding:2px;overflow:hidden;">
			<iframe id="editconifr"  name="editconifr" class="editifr" src="${ctx}/monitor/hibernate/control"></iframe> 
		</div>
	</div>
<ewcms:footer/>
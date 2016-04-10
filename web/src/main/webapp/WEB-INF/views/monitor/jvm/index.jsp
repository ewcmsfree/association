<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="JVM监控 - 标签页"/>
	<div id="tab-jvm" class="easyui-tabs" data-options="fit:true,border:true,tabPosition:'right',headerWidth:120">
		<div title="JVM Base" style="padding:2px;overflow:hidden;">
			<iframe id="editjriifr" name="editjriifr" class="editifr" src="${ctx}/monitor/jvm/base/index"></iframe>
		</div>			
		<div title="Thread List" style="padding:2px;overflow:hidden;">
			<iframe id="editjtiifr"  name="editjtiifr" class="editifr" src="${ctx}/monitor/jvm/thread/index"></iframe> 
		</div>
	</div>
<ewcms:footer/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="Hibernate监控 "/>
	<table id="tt"></table>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').propertygrid({
			fit:true,
	        url:'${ctx}/monitor/hibernate/hibernate/query',
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
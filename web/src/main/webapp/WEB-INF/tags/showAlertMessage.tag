<%@ tag pageEncoding="UTF-8" description="显示操作成功/失败的消息，内容为:message/error" %>
<%@ attribute name="successMessage" type="java.lang.String" required="false" description="成功消息" %>
<%@ attribute name="errorMessage" type="java.lang.String" required="false" description="失败消息" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/black/easyui.css" title="black"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/bootstrap/easyui.css" title="bootstrap"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css" title="default"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/gray/easyui.css" title="gray"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/metro/easyui.css" title="metro"/>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
<!--[if lt IE 9]><script src="${ctx}/static/views/html5.js"></script><![endif]-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css"/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<c:if test="${not empty successMessage}">
    <c:set var="message" value="${successMessage}"/>
</c:if>
<c:if test="${not empty errorMessage}">
    <c:set var="error" value="${errorMessage}"/>
</c:if>
<c:if test="${not empty message}">
    <script type="text/javascript">$.messager.alert("提示","${message}", "info");</script>
</c:if>
<c:if test="${not empty error}">
    <script type="text/javascript">$.messager.alert("错误", "${error}", "error");</script>
</c:if>
<script type="text/javascript">
	$(function(){
		$('div[class="messager-body panel-body panel-body-noborder window-body"]').removeAttr("style");
	});
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>
<ewcms:head/>
<div class="easyui-panel" data-options="fit:true,border:false">
	<div id="error" style="margin-top:75px;padding-top:65px;width:520px;">
	    <ewcms:showMessage errorMessage="${error.message}"/>
	    <c:if test="${user.admin}">
		    <c:set var="stackTrace" value="${error.stackTrace}"/>
		    <%@include file="exceptionDetails.jsp"%>
	    </c:if>
	</div>
</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
    	$('#error').css({'position':'absolute','left':($(window).width()-520)/2});
		$(window).resize(function(){  
    		$('#error').css({'position':'absolute','left':($(window).width()-520)/2});
    	});
	});  
</script> 
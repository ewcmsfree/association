<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ewcms.security.resource.entity.ResourceStyle" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ewcms" tagdir="/WEB-INF/tags" %>

<c:set var="tree" value="<%=ResourceStyle.tree%>"/>
<c:set var="accordion" value="<%=ResourceStyle.accordion%>"/>
		
<c:forEach items="${menus}" var="m">
	<div id="menusub-${m.id}" title="${m.name}" style="overflow-x:hidden;overflow-y:auto;display:none;">
		<c:choose>
			<c:when test="${m.oneLevelStyle eq tree}">
				<ul class="easyui-tree" data-options="animate:true,lines:true">
					<c:forEach items="${m.children}" var="c">
						<ewcms:subMenu menu="${c}" style="${tree}"/>
					</c:forEach>
				</ul>
			</c:when>
			<c:otherwise>
				<c:forEach items="${m.children}" var="c">
					<ewcms:subMenu menu="${c}" style="${accordion}"/>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</c:forEach>

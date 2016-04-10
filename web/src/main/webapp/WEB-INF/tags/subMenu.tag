<%@ tag pageEncoding="UTF-8" description="构建子菜单" %>
<%@ tag import="com.ewcms.common.web.controller.entity.TreeIconCls" %>
<%@ tag import="com.ewcms.security.resource.entity.ResourceStyle" %>
<%@ attribute name="menu" type="com.ewcms.security.resource.entity.Menu" required="true" description="当前菜单" %>
<%@ attribute name="style" type="java.lang.String" required="true" description="显示风格"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ewcms" tagdir="/WEB-INF/tags" %>

<c:set var="tree" value="<%=ResourceStyle.tree%>"/>
<c:set var="treeSuffix" value="<%=TreeIconCls.tree_suffix%>"/>

<c:choose>
	<c:when test="${style eq tree}">
		<c:choose>
			<c:when test="${!menu.hasChildren}">
				<li data-options="iconCls:'${menu.icon}'"><span><a href="javascript:$.ewcms.openTab({id:'#tab-${menu.oneLevelId}', title:'${menu.name}',src:'<%=menuUrl(request, menu.getUrl())%>'})" title="${menu.name}">${menu.name}</a></span></li>
			</c:when>
			<c:otherwise>
				<li data-options="iconCls:'${menu.icon}'"><span><a href="javascript:void(0)" title="${menu.name}">${menu.name}</a></span>
					<ul>
						<c:forEach items="${menu.children}" var="menu2">
							<ewcms:subMenu menu="${menu2}" style="${style}"/>
						</c:forEach>
					</ul>
				</li>
			</c:otherwise>
		</c:choose>	
	</c:when>
	<c:otherwise>
		<c:if test="${not empty menu.icon}">
			<c:set var="iconLen" value="${menu.icon.lastIndexOf(treeSuffix)}" />
			<c:set var="iconCls" value="${fn:substring(menu.icon,0,iconLen)}"/>
		</c:if>
		<c:choose>
			<c:when test="${!menu.hasChildren}">
				<div class="nav-item">
					<a href="javascript:$.ewcms.openTab({id:'#tab-${menu.oneLevelId}', title:'${menu.name}',src:'<%=menuUrl(request, menu.getUrl())%>'})"  title="${menu.name}">
				        <img class="${iconCls}" style="border:0"/><br/>
				        <span>${menu.name}</span>
				    </a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="easyui-layout">
					<div class="easyui-accordion" data-options="border:false" style="width:163px;height:auto;">
				       	<div title="${menu.name}" data-options="iconcls:'${iconCls}'" style="overflow:auto;padding:10px;">
				        	<c:forEach items="${menu.children}" var="menu2">
				                <ewcms:subMenu menu="${menu2}" style="${style}"/>
				            </c:forEach>
				        </div>
				     </div>
				</div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<%!
    private static String menuUrl(HttpServletRequest request, String url) {
        if(url.startsWith("http")) {
            return url;
        }
        String ctx = request.getContextPath();

        if(url.startsWith(ctx) || url.startsWith("/" + ctx)) {
            return url;
        }

        if(!url.startsWith("/")) {
            url = url + "/";
        }
        return ctx + url;

    }
%>
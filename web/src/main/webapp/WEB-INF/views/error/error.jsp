<%@ page import="com.ewcms.common.utils.LogUtils" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="错误页面"/>

<div class="easyui-panel" data-options="fit:true,border:false">
    <%
        LogUtils.logPageError(request);

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        pageContext.setAttribute("statusCode", statusCode);

        String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
        String queryString = request.getQueryString();
        String url = uri + (queryString == null || queryString.length() == 0 ? "" : "?" + queryString);
        pageContext.setAttribute("url", url);

        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        request.setAttribute("exception", exception);

    %>
	<div style="width:600px;margin:0 auto;border:1px solid #d1d1d1;margin-top:120px;padding:20px;">
		<table cellSpacing="5" width="90%" align="center" border="0" cepadding="0" style="line-height: 25px;font-size:12px;">
			<tr>
				<td valign="top" align="middle"><img border="0" id="errimg" src="${ctx}/static/image/404.png"><td>
				<td>
					<h1 style="color: red;font-size:20px;">操作错误</h1> 
					<label style="color: #001150"> 
					HTTP 错误 <c:if test="${statusCode eq 404}">404</c:if><c:if test="${statusCode ne 404}">500</c:if>：<br />
					您正在搜索的页面可能暂时不可用,也可能您的访问权限不够, <br /> 
					或者您在系统的认证已经过期，无法继续使用系统。 
					</label>
					<hr style="color:#c1c1c1;">
					<p>
						☉ <b>请尝试以下操作：</b>
					</p>
					<ul style="list-style: outside none none;margin:0;padding:0">
						<li>确保登陆并且有访问该页面的权限成功。</li>
						<li>确保操作条件或内容的拼写和格式正确无误。</li>
						<li>如果操作出现未知错误，请与网站管理员联系。</li>
						<li>建议你尝试： 
						<a href="javascript:void(0);" onclick="window.history.go(-1);"> 
						<font color="green">返回</font></a>
						&nbsp;或&nbsp;
						<a href="javascript:void(0);" onclick="window.top.location.href = '${ctx}/logout';">
						<font color="green">重新登录</font>
						</a>
						</li>
					</ul>
					<hr style="color:#c1c1c1;">
					<p>
						☉ 如果您对系统有任何疑问、建议，请联系管理员。
						<c:if test="${user.admin}">
					        <c:if test="${not empty exception}">
					            <%
					                StringWriter stringWriter = new StringWriter();
					                PrintWriter printWriter = new PrintWriter(stringWriter);
					                exception.printStackTrace(printWriter);
					                pageContext.setAttribute("stackTrace", stringWriter.toString());
					            %>
					            <%@include file="exceptionDetails.jsp"%>
					        </c:if>
						</c:if>
					</p>
				</td>
			</tr>
		</table>
	</div>
</div>
<ewcms:footer/>
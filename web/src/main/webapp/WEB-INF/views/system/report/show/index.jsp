<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="报表显示"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',border:false">
		<fieldset>
			<table class="formtable">
		  		<c:forEach items="${categoryReportList}" var="categoryReport">
		  			<tr>
						<td colspan="4" bgcolor="#a9c9e2" height="20"><font color="#1E4176"><b>${categoryReport.name}-报表集</b></font></td>
		  			</tr>
		  			<c:choose>
		  				<c:when test="${not empty categoryReport.texts}">
		  					<tr>
								<td colspan="4"><b>文字报表：</b></td>
		  					</tr>
		  					<tr>
		    					<c:forEach items="${categoryReport.texts}" var="text" varStatus="textStatus">
 									<td width="20%"><a href="javascript:void(0);" onclick="setReportParameter('text', ${text.id});" style="text-decoration:none;" title="${text.remarks}"><span class="ellipsis">${text.name}</span></a></td>
		  							<c:choose>
		  								<c:when test="${textStatus.index%3==0 && !textStatus.first && !textStatus.last}">
		  									</tr>
		  									<tr>
		  								</c:when>
		  							</c:choose>
		    					</c:forEach>
		  					</tr>
		  				</c:when>
		  			</c:choose>
		 			<c:choose>
		  				<c:when test="${not empty categoryReport.charts}">
		  					<tr>
								<td colspan="4"><b>图型报表：</b></td>
		  					</tr>
		  					<tr>
		    					<c:forEach items="${categoryReport.charts}" var="chart" varStatus="chartStatus">
 									<td width="20%"><a href="javascript:void(0);" onclick="setReportParameter('chart', ${chart.id});" style="text-decoration:none;" title="${chart.remarks}"><span class="ellipsis">${chart.name}</span></a></td>
		  							<c:choose>
		  								<c:when test="${chartStatus.index%3==0 && !chartStatus.first && !chartStatus.last}">
		  									</tr>
		  									<tr>
		  								</c:when>
		  							</c:choose>
		    					</c:forEach>
		 					</tr>
		  				</c:when>
		  			</c:choose>
				</c:forEach>
			</table>
		</fieldset>
		</div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	function setReportParameter(type, id){
		$.ewcms.openWindow({
			windowId:"#edit-window",
			iframeId:'#editifr',
			src:'${ctx}/system/report/show/' + type + '/' + id + '/paraset',
			width:400,
			height:213,
			title:"参数选择"
		});
	}
</script>
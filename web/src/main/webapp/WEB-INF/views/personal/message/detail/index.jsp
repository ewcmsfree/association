<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="内容"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',border:false">	
			<table class="formtable" style="width:100%">
				<tr>
					<td width="20%">标题：</td>
					<td width="80%">${title}<c:if test="${not empty results}">&nbsp;&nbsp;<a id="tb-subscribe" href="javascript:void(0);" onclick="subscribe();return false;" onfocus="this.blur();">订阅</a></c:if></td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
						<c:choose>
							<c:when test="${not empty results}">
								<table class="formatable" style="width:100%">
				    				<c:forEach items="${results}" var="result" varStatus="rowstatus">
				      					<tr>
											<c:choose>
				        						<c:when test="${rowstatus.count%2==0}">
				          							<td width="100%" style="background: #EEEEFF">${result}</td>
				        						</c:when>
				        						<c:otherwise>
				          							<td width="100%">${result}</td>
				        						</c:otherwise>
				        					</c:choose>
				      					</tr>
				    				</c:forEach>
				    			</table>
				    		</c:when>
				    		<c:otherwise>
				    			${detail}
				    		</c:otherwise>
				    	</c:choose>
					</td>
			  	</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$('#tb-subscribe').bind('click', function(){
		$.post('${ctx}/content/message/detail/${id}/${type}/index', {}, function(data) {
			if (data == 'own'){
				$.messager.alert('提示','您不能订阅自已发布的信息！','info');
				return;
			}
			if (data == 'exist'){
				$.messager.alert('提示','您已订阅了此信息，不需要再订阅！','info');
				return;
			}
			if (data == 'false'){
				$.messager.alert('提示','订阅信息失败！','info');
				return;
			}
			if (data == 'true'){
				$.messager.alert('提示','订阅成功！','info');
				return;
			}
		});
	});
</script>
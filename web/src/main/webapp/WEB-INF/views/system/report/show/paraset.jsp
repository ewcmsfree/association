<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 查询参数设置"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/system/report/show/${reportType}/${reportId}/build" commandName="m"  class="form-horizontal" target="_blank">
	  			<table class="basetable">
					<tr>
		  				<td>
							<table style="width:100%">
			  					<c:forEach items="${pageShowParams}" var="parameter">
			  						<tr>
										<td class="texttd">
											<c:choose>
												<c:when test="${not empty parameter.cnName}">${parameter.cnName}</c:when>
												<c:otherwise>${parameter.enName}</c:otherwise>
											</c:choose>
										</td>
										<td class="inputtd">
				  							<c:if test="${parameter.type == 'TEXT'}">
				  								<input type="text" name="paramMap['${parameter.enName}']" value="${parameter.defaultValue}"/>
				  							</c:if>
				  							<c:if test="${parameter.type == 'BOOLEAN'}">
				  								<input type="checkbox" name="paramMap['${parameter.enName}']" value="${parameter.defaultValue}"/>
				  							</c:if>
				  							<c:if test="${parameter.type == 'LIST'}">
				  								<form:select path="paramMap['${parameter.enName}']" items="${parameter.paramMap}" cssClass="easyui-combobox" data-options="panelHeight:100"></form:select>
				  							</c:if>
				  							<c:if test="${parameter.type == 'CHECK'}">
				  								<form:checkboxes path="paramMap['${parameter.enName}']" items="${parameter.paramMap}" onclick="checkBoxValue('${parameter.enName}')"/>
				  							</c:if>
				  							<c:if test="${parameter.type == 'DATE'}">
				  								<input type="text" name="paramMap['${parameter.enName}']" class="easyui-datebox"/>
				  							</c:if>
				  							<c:if test="${parameter.type == 'SESSION'}">
				  								<input type="text" name="paramMap['${parameter.enName}']" value="<shiro:principal property="username"/>" readonly="readonly"/>
				  							</c:if>
				  							<c:if test="${parameter.type == 'SQL'}">
				  								<input type="text" name="paramMap['${parameter.enName}']" value="${parameter.defaultValue}"/>
				  							</c:if>
										</td>
			  						</tr>
			  					</c:forEach>
			  					<c:if test="${reportType=='text'}">
			  						<tr>
										<td class="texttd">报表文件类型：</td>
										<td class="inputtd">
				  							<form:select path="textType" items="${textReportTypeMap}" itemLabel="description" cssClass="easyui-combobox"/>
										</td>
			  						</tr>
			  					</c:if>
							</table>
		  				</td>
					</tr>
	  			</table>
			</form:form>
		</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="javascript:$('#editForm').submit();" href="javascript:void(0);" >提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="javascript:$('#editForm').form('reset');" href="javascript:void(0);" >重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:parent.$('#edit-window').window('close');" href="javascript:void(0);">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		if ($('select:not(#textType)')){
			$('select:not(#textType)').combobox('setValue', '');
		}
	});
	
	function checkBoxValue(name){
		var strValue = '';
		var list = document.getElementsByName(name);
		for (var i = 0; i < list.length; i++){
			if (list[i].type == 'checkbox'){
				if (list[i].checked == true) {
					listValue = list[i].value;
					if(strValue != '')strValue += ',';
					if (isNumber(listValue)){
						strValue += listValue;
					}
					else{
						strValue += "'" + listValue + "'";
					}
				}
			}
		}
		obName = "paramMap['" + name + "']";
		document.all[obName].value = strValue;
	}
		
	function isNumber(str){
	  var patrn=/^\d*$/;    
	  if(patrn.test(str))   {  
	  	return true;    
	  }else{  
	  	return false;  
	  }   
	}
</script>

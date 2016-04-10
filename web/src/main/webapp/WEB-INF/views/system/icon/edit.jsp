<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 图标"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true,bordar:false">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" action="${ctx}/system/icon/save" method="post" commandName="m"  class="form-horizontal" enctype="multipart/form-data">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
			  	<table class="formtable">
		        	<tr>
				  		<td width="20%"><form:label path="identity">标识符：</form:label></td>
				  		<td width="80%"><form:input path="identity" cssClass="validate[required,custom[identity],ajax[ajaxIdentityCall]]" size="50" placeholder="用于表示图标的唯一标识"/></td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="iconType"></form:label>类型：</td>
			    		<td><form:select path="iconType" items="${types}" itemLabel="info" data-options="width:150,panelHeight:'auto',editable:false"/></td>
			    	</tr>
				    <tr id="tr-cssclass">
				    	<td><form:label path="cssClass">css类：</form:label></td>
				    	<td><form:input path="cssClass" cssClass="validate[required]" placeholder="图标css类" size="50" /></td>
				    </tr>
				    <tr id="tr-uploadfile-1" style="display:none;">
				    	<c:if test="${not empty m.imgSrc}">
					    <td>原图标：</td>
					    <td>
					    	<i class="${m.identity}" title="生成后的图标"></i>
					    	<form:hidden path="imgSrc"/>
					    </td>
				    	</c:if>
				    </tr>
				    <tr id="tr-uploadfile-2" style="display:none;">
				    	<td>图片文件：</td>
				    	<td><input id="file" type="file" name="file"/></td>
				    </tr>
				    <tr id="tr-csssprite-1" style="display:none;">
				    	<td>原图标：</td>
				    	<td><i class="${m.identity}" title="生成后的图标"></i></td>
				    </tr>
				    <tr id="tr-csssprite-2" style="display:none;">
				    	<td><form:label path="spriteSrc">图标文件：</form:label></td>
				    	<td><form:input path="spriteSrc" placeholder="绝对路径：如http://www.ewcms.com，相对路径不要加上下文" size="60" /></td>
				    </tr>
				    <tr id="tr-csssprite-3" style="display:none;">
				    	<td>位置：</td>
				    	<td>
				    		<form:input path="left" cssClass="validate[required, custom[integer]] input-small"
                                    placeholder="距离左边" data-toggle="tooltip" data-placement="bottom" title="距离左边" size="8"/>
                       		 <form:input path="top" cssClass="validate[required, custom[integer]] input-small"
                                    placeholder="距离上边"  data-toggle="tooltip" data-placement="bottom" title="距离上边" size="8"/>
				    	</td>
				    </tr>
				    <tr id="tr-upload-sprite-1" style="display:none;">
				    	<td>大小：</td>
				    	<td>
				    		<form:input path="width" cssClass="validate[required, custom[integer]] input-small"
                                    placeholder="宽度" data-toggle="tooltip" data-placement="bottom" title="宽度" size="4"/>
                        	<form:input path="height" cssClass="validate[required, custom[integer]] input-small"
                                    placeholder="高度" data-toggle="tooltip" data-placement="bottom" title="高度"  size="4"/>
				    	</td>
				    </tr>
				    <tr id="tr-upload-sprite-2" style="display:none;">
				    	<td><form:label path="style">其他css属性：</form:label></td>
				    	<td><form:input path="style" size="50"/></td>
				    </tr>
			    	<tr>
			    		<td><form:label path="description">描述：</form:label></td>
			    		<td><form:input path="description" placeholder="此图标的简介" size="50" /></td>
			    	</tr>
			  		</table>
		  	</form:form>
    	</div>
		<div data-options="region:'south'" style="text-align:center;height:30px;border:0">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
	    <c:choose>
	    	<c:when test="${close}">
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
	            $.validationEngineLanguage.allRules.ajaxIdentityCall= {
	                "url": "${ctx}/system/icon/validate",
	                extraDataDynamic: ['#id'],
	                "alertTextLoad": "* 正在验证，请稍等。。。"
	            };
	
	            $.validationEngineLanguage.allRules.identity = {
	                "regex": /^[a-zA-Z][a-zA-Z-_0-9]*$/,
	                "alertText": "* 必须以字母开头"
	            };
	    		var validationEngine = $("#editForm").validationEngine({
	    			promptPosition:'bottomRight',
	    			showOneMessage: true
	    		});
	        	<ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
	    </c:choose>
	    
		$('#iconType').combobox({
			onSelect:function(record){
				typeSelect(record.value)
			}
		});
		
		typeSelect($('#iconType').combobox('getValue'));
	});
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});

	function typeSelect(value){
		if (value == 'css_class'){
			$('#tr-cssclass').css('display', '');
			$('tr[id^="tr-uploadfile-"]').css('display', 'none');
			$('tr[id^="tr-csssprite-"]').css('display', 'none');
			$('tr[id^="tr-upload-sprite-"]').css('display', 'none');
		} else if (value == 'upload_file'){
			$('#tr-cssclass').css('display', 'none');
			$('tr[id^="tr-uploadfile-"]').css('display', '');
			$('tr[id^="tr-csssprite-"]').css('display', 'none');
			$('tr[id^="tr-upload-sprite-"]').css('display', '');
		} else {
			$('#tr-cssclass').css('display', 'none');
			$('tr[id^="tr-uploadfile-"]').css('display', 'none');
			$('tr[id^="tr-csssprite-"]').css('display', '');
			$('tr[id^="tr-upload-sprite-"]').css('display', '');
		}
	}
</script>
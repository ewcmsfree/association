<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="上传 - 图片"/>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
		 	<form:form id="editForm" method="post" action="${ctx}/personnel/photo/personal/save" commandName="m"  class="form-horizontal" enctype="multipart/form-data">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
				
			  	<table class="formtable">
		        	<tr>
						<td width="20%">照片：</td>
						<td width="80%"><input type="file" name="myUpload" id="myUpload" accept="image/*" class="validate[required]"></td>
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
	    	parent.$('#img-photo').attr('src', '${ctx}/archive/photo/${m.userId}?_=' + (new Date()).getTime());
	    		parent.$('#edit-window').window('close');
	    	</c:when>
	    	<c:otherwise>
			    var validationEngine = $("#editForm").validationEngine({
			    	promptPosition:'bottomRight',
			    	showOneMessage: true
			    });
			    <ewcms:showFieldError commandName="m"/>
	    	</c:otherwise>
		</c:choose>
	});
</script>
	
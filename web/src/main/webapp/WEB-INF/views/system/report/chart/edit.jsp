<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 文字报表"/>
	<%@ include file="/WEB-INF/views/jspf/import-codemirror-css.jspf" %>
	<style type="text/css">
	  .CodeMirror {height: 105px; width: 500px;}
    </style>
	<div id="edit-from" class="easyui-layout" data-options="fit:true,border:false">
		<ewcms:showMessage/>
		<div data-options="region:'center',border:false">	
			<form:form id="editForm" action="${ctx}/system/report/chart/save" method="post" modelAttribute="m" class="form-horizontal">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<c:forEach var="selection" items="${selections}">
	  				<input type="hidden" name="selections" value="${selection}" />
				</c:forEach>
				<table class="formtable" >
					<tr>
						<td width="15%"><form:label path="name">报表名：</form:label></td>
						<td width="35%"><form:input path="name" cssClass="validate[required, max[20]]" placeholder="报表名称"/></td>
						<td width="15%">数据源类型：</td>
						<td width="35%"><form:select path="baseDs.id" items="${baseDsList}" itemLabel="name" itemValue="id" cssClass="easyui-combobox" data-options="editable:false"/></td>
					</tr>
					<tr>
						<td><form:label path="chartSql">SQL表达式：</form:label><a id="regexHelp" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" onclick="sqlhelp();"></a></td>
						<td colspan="3"><form:textarea path="chartSql" cssClass="validate[required]" placeholder="SQL语句"/></td>
					</tr>
					<tr>
						<td><form:label path="type">图表类型：</form:label></td>
						<td><form:select path="type" items="${typeMap}" itemLabel="description" cssClass="easyui-combobox" data-options="editable:false"/></td>
						<td><form:label path="showTooltips">工具提示：</form:label></td>
						<td><form:checkbox path="showTooltips"/></td>
					</tr>
					<tr>
						<td><form:label path="chartTitle">图表标题：</form:label></td>
						<td><form:input path="chartTitle" cssClass="validate[required]"/></td>
						<td>图表标题字体：</td>
		  				<td>
							<form:select path="fontName" items="${fontNameMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
							<form:select path="fontStyle" items="${fontStyleMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
							<form:select path="fontSize" items="${fontSizeMap}" cssClass="easyui-combobox" data-options="editable:false"/>
		  				</td>
					</tr>
					<tr>
		  				<td><form:label path="horizAxisLabel">横坐标轴标题：</form:label></td>
		  				<td><form:input path="horizAxisLabel" cssClass="validate[required]"/></td>
		  				<td><form:label path="vertAxisLabel">纵坐标轴标题：</form:label></td>
		  				<td><form:input path="vertAxisLabel" cssClass="validate[required]"/></td>
					</tr>
					<tr>
		  				<td>数据字体：</td>
		  				<td>
		  					<form:select path="dataFontName" items="${fontNameMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
		  					<form:select path="dataFontStyle" items="${fontStyleMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
		  					<form:select path="dataFontSize" items="${fontSizeMap}" cssClass="easyui-combobox" data-options="editable:false"/>
		  				</td>
		  				<td>坐标轴字体：</td>
		  				<td>
		  					<form:select path="axisFontName" items="${fontNameMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
		  					<form:select path="axisFontStyle" items="${fontStyleMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
		  					<form:select path="axisFontSize" items="${fontSizeMap}" cssClass="easyui-combobox" data-options="editable:false"/>
		  				</td>
					</tr>
					<tr>
		  				<td>坐标轴尺值字体：</td>
		  				<td>
							<form:select path="axisTickFontName" items="${fontNameMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
							<form:select path="axisTickFontStyle" items="${fontStyleMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
							<form:select path="axisTickFontSize" items="${fontSizeMap}" cssClass="easyui-combobox" data-options="editable:false"/>
		  				</td>
		  				<td><form:label path="tickLabelRotate">数据轴标签角度：</form:label></td>
		  				<td><form:select path="tickLabelRotate" items="${rotateMap}" cssClass="easyui-combobox" data-options="editable:false"/></td>
					</tr>
					<tr>
		  				<td><form:label path="showLegend">图示说明：</form:label></td>
		  				<td><form:checkbox path="showLegend" /></td>
		  				<td><form:label path="legendPosition">图示位置：</form:label></td>
		  				<td><form:select path="legendPosition" items="${positionMap}" cssClass="easyui-combobox" data-options="editable:false"/></td>
					</tr>
					<tr>
		  				<td>图示字体：</td>
		  				<td>
		    				<form:select path="legendFontName" items="${fontNameMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
		    				<form:select path="legendFontStyle" items="${fontStyleMap}" cssClass="easyui-combobox" data-options="editable:false"/>&nbsp;
		    				<form:select path="legendFontSize" items="${fontSizeMap}" cssClass="easyui-combobox" data-options="editable:false"/>
		  				</td>
		  				<td><form:label path="chartHeight">图表高度：</form:label></td>
		  				<td><form:input path="chartHeight" cssClass="validate[custom[integer]"/></td>
					</tr>
					<tr>
		  				<td><form:label path="chartWidth">图表宽度：</form:label></td>
		 				<td><form:input path="chartWidth" cssClass="validate[custom[integer]"/></td>
		  				<td>RGB背景色：</td>
		  				<td>
							<form:input path="bgColorR" size="3" cssClass="validate[custom[integer],maxSize[3],min[0],max[255]]"/>&nbsp;&nbsp;
							<form:input path="bgColorG" size="3" cssClass="validate[custom[integer],maxSize[3],min[0],max[255]]"/>&nbsp;&nbsp;
							<form:input path="bgColorB" size="3" cssClass="validate[custom[integer],maxSize[3],min[0],max[255]]"/>
		  				</td>
					</tr>
					<tr>
		  				<td><form:label path="remarks">备注：</form:label></td>
		  				<td colspan="3"><form:textarea path="remarks" cols="46" cssStyle="resize:none" placeholder="简要说明"/></td>
					</tr>
	  			</table>
			</form:form>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;height:30px">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">提交</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<%@ include file="/WEB-INF/views/jspf/import-codemirror-js.jspf" %>
<script type="text/javascript">
	$(function(){
		<c:choose>
	    	<c:when test="${close}">
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
	
	$.ewcms.refresh({operate : '${operate}', data : '${lastM}'});

	var editor = CodeMirror.fromTextArea(document.getElementById("chartSql"), {
		mode: "text/x-plsql",
		lineNumbers: true,
    	lineWrapping: true,
        matchBrackets: true,
        indentUnit: 4,
        extraKeys: {"Ctrl-Q": function(cm){ cm.foldCode(cm.getCursor()); }},
        foldGutter: true,
        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
    });
	
	function sqlhelp(){
		window.open('${ctx}/system/report/chart/sqlHelp','popup','width=900,height=500,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,scrollbars=yes,status=no,left=' + (window.screen.width - 900)/ 2 + ',top=' + (window.screen.height - 500) / 2);
	}
</script>
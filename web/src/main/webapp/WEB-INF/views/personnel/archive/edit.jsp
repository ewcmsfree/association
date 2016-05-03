<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="修改 - 个人基本信息"/>
	<div id="edit-form" class="easyui-layout" data-options="fit:true" style="border:0;">
		<ewcms:showAlertMessage/>
		<div data-options="region:'north',border:false,maxHeight:65" style="text-align:center;height:60px;">
			<h1 class="title" style="text-align: center;">
				<table style="width:100%;">
					<tr style="vertical-align:bottom;">
						<td style="width:50%;text-align:right;font-size:24pt;">专家信息登记表</td>
						<td style="width:50%;color:red;text-align:right;font-size:9pt;">标记 * 为必填字段</td>
					</tr>
				</table>
			</h1>
		</div>
		<div id="divOverflown" data-options="region:'center',border:false" >	
		 	<form:form id="editForm" method="post" action="${ctx}/personnel/archive/save" commandName="m" class="form-horizontal" style="position:relative; margin-left:10px;">
		    	<ewcms:showGlobalError commandName="m"/>
		    	<form:hidden path="id"/>
		    	<form:hidden path="userId"/>
		    	<form:hidden path="deleted"/>
		  		<table style="width:99%;baborder: #B7D8ED 1px solid;" class="formtable">
					<tr>
						<td width="92%" style="border: 0px solid; padding:0px;">
				  			<table style="width:100%;border: 0px solid;">
				  				<tr>
				  					<td>目前状态</td>
				  					<td colspan="5">
				  						<span id="status" style="color: red;"></span>
				  						<c:if test="${m.status == 'through'}">
				  							<a id="tb-print" class="easyui-linkbutton" data-options="iconCls:'icon-print'" href="javascript:void(0);">打印</a>
				  						</c:if>
				  					</td>
				  				</tr>
				  				<tr>
				  					<td>学会（院校科协）名称<label style="color: red;">&nbsp;*</label></td>
				  					<td colspan="5">
										<c:choose>
								  			<c:when test="${empty(m.organizationIds)}">
								  				<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree"/>
								  			</c:when>
								  			<c:otherwise>
								  				<c:set var="organizationUrl" value="${ctx}/security/organization/organization/tree/${m.organizationIds}/multipleChecked"/>
								  			</c:otherwise>
										</c:choose>
				  						<form:input path="organizationNames" id="organizationNames" cssClass="validate[required]" cssStyle="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
										<input name="organizations" id="organizations" style="margin-left:0px;z-index:1;position:absolute;"/>
									</td>
				  				</tr>
					        	<tr>
							  		<td width="11%"><form:label path="name">姓名</form:label><label style="color: red;">&nbsp;*</label></td>
							  		<td width="23%"><form:input path="name" cssClass="validate[required]" size="25"/></td>
							  		<td width="10%"><form:label path="sex">性别</form:label><label style="color: red;">&nbsp;*</label></td>
							  		<td width="23%"><form:radiobuttons path="sex" items="${sexList}" itemLabel="info" delimiter="&nbsp;"/></td>
							  		<td width="10%"><form:label path="birthday">出生日期</form:label><label style="color: red;">&nbsp;*</label></td>
						    		<td width="23%">
						    			<input type="text" id="birthday_show" class="validate[required, custom[date], past[2010-01-01]]" value="${m.birthday}" style="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
						    			<form:input path="birthday" cssStyle="margin-left:0px;z-index:1;position:absolute;"/>
						    		</td>
						    	</tr>
						    	<tr>
						    		<td><form:label path="located">籍贯</form:label><label style="color: red;">&nbsp;*</label></td>
						    		<td><form:input path="located" cssClass="validate[required]" size="25"/></td>
						    		<td><form:label path="nation.id">民族</form:label><label style="color: red;">&nbsp;*</label></td>
						    		<td>
						    			<form:input path="nation.name" id="nationName" cssClass="validate[required]" cssStyle="width:0px;height:0px;z-index:0;position:absolute;margin-top:5px;margin-left:5px;" size="0" readonly="readonly"/>
						    			<form:input path="nation.id" id="nationId" cssStyle="margin-left:0px;z-index:1;position:absolute;"/>
						    		</td>
						    		<td><form:label path="political">政治面貌</form:label><label style="color: red;">&nbsp;*</label></td>
						    		<td><form:input path="political" cssClass="validate[required]" size="25"/></td>
						    	</tr>	
						    	<tr>
						    		<td><form:label path="workAddress">工作单位</form:label></td>
						    		<td><form:input path="workAddress" size="25"/></td>
						    		<td><form:label path="duties">职务</form:label></td>
						    		<td><form:input path="duties" size="25"/></td>
						    		<td><form:label path="acadOffice">学会任职</form:label></td>
						    		<td><form:input path="acadOffice" size="25"/></td>
						    	</tr>
						    	<tr>
						    		<td><form:label path="titles">技术职称</form:label><label style="color: red;">&nbsp;*</label></td>
						    		<td><form:input path="titles" cssClass="validate[required]" size="25"/></td>
						    		<td>是否硕、博导</td>
						    		<td><form:checkbox path="isShuoDao" label="硕导"/>&nbsp;&nbsp;<form:checkbox path="isBoDao" label="博导"/></td>
						    		<td>目前情况<label style="color: red;">&nbsp;*</label></td>
						    		<td><form:checkboxes path="currentStates"  cssClass="validate[required]" items="${currentStateList}" itemLabel="name" itemValue="id" delimiter="&nbsp;&nbsp;"/></td>
						    	</tr>
						    	<tr>
						    		<td>手机</td>
						    		<td>${m.mobilePhoneNumber}</td>
						    		<td>E-mail</td>
						    		<td>${m.email}</td>
						    		<td>&nbsp;</td>
						    		<td>&nbsp;</td>
						    	</tr>
					    	</table>
				    	</td>
				    	<td width="8%" style="border: 0px solid; padding:0px;">
				    		<div align="center">
				    			<img id="img-photo" width="90" height="126" src="${ctx}/archive/photo/${m.userId}">
				    			<c:if test="${!m.deleted}">
				    			<br/>
				    			<c:if test="${m.status!='submitthrough'}">
				    			<a id="tb-photo" class="easyui-linkbutton" data-options="iconCls:''" href="javascript:void(0);">上传图片</a>
				    			</c:if>
				    			</c:if>
				    		</div>
				    	</td>
			    	</tr>
			    	<tr>
				    	<td colspan="2" style="border: 0px solid; padding:0px;">
					    	<table style="border: 0px solid; padding:0px;" class="formtable">
						  		<tr>
						    		<td width="10%"><form:label path="postalAddress">通讯地址</form:label><label style="color: red;">&nbsp;*</label></td>
						    		<td width="40%"><form:input path="postalAddress" size="60" cssClass="validate[required]"/></td>
						    		<td width="10%"><form:label path="zipCode">邮政编码</form:label><label style="color: red;">&nbsp;*</label></td>
						    		<td width="40%"><form:input path="zipCode" size="6" maxlength="6" cssClass="validate[required,custom[number]]"/></td>
						    	</tr>
						    	<tr>
						    		<td><form:label path="officePhone">办公电话</form:label></td>
						    		<td><form:input path="officePhone" size="60"/></td>
						    		<td><form:label path="fax">传真</form:label></td>
						    		<td><form:input path="fax" size="30"/></td>
						    	</tr>
						    	<tr>
						    		<td colspan="4" style="height:300px;">
						    			<c:choose>
						    				<c:when test="${(not empty view && view) || m.status=='submitthrough'}">
						    					<c:set var="educationExperienceUrl" value="${ctx}/personnel/archive/educationExperience/${m.userId}/view"/>
						    				</c:when>
						    				<c:otherwise>
						    					<c:set var="educationExperienceUrl" value="${ctx}/personnel/archive/educationExperience/${m.userId}/index"/>
						    				</c:otherwise>
						    			</c:choose>
						    			<iframe id="ifrmex" class="editifr" style="width:100%;height:100%;border:0;overflow:auto;" src="${educationExperienceUrl}"></iframe>
						    		</td>
						    	</tr>
						    	<tr>
						    		<td>现从事专业<label style="color: red;">&nbsp;*</label></td>
						    		<td><form:textarea path="nowProfessional"  cssClass="validate[required]" cssStyle="resize:none;width:98%;height:60px;"/></td>
						    		<td>特长专业<label style="color: red;">&nbsp;*</label></td>
						    		<td><form:textarea path="specialty"  cssClass="validate[required]" cssStyle="resize:none;width:98%;height:60px;"/></td>
						    	</tr>
						    	<tr>
						    		<td>进修情况（国内、国外）</td>
						    		<td colspan="3"><form:textarea path="learningSituation" cssStyle="resize:none;width:99%;height:60px;"/></td>
						    	</tr>
						    	<tr>
						    		<td>参加全国、国际学术组织（机构）及任职情况</td>
						    		<td colspan="3"><form:textarea path="jobStatus" cssStyle="resize:none; width:99%;height:100px;"/></td>
						    	</tr>
						    	<tr>
						    		<td>是否享受远航工程资助</td>
						    		<td><form:checkbox id="isVoyage" path="isVoyage"/>&nbsp;&nbsp;<div id="div-voyage" style="display:inline-block;">（<form:input path="voyageYear" cssClass="validate[required,custom[number]]" size="4" maxlength="4"/>年）</div></td>
						    	</tr>
						    	<tr>
						    		<td>享受政府津贴情况</td>
						    		<td><form:select id="isAllowance" path="isAllowance" items="${allowanceEnumList}" itemLabel="info"/>&nbsp;&nbsp;&nbsp;<div id="div-allowances" style="display:inline-block;"><form:checkboxes path="allowances" items="${allowanceList}"  itemLabel="name" itemValue="id" cssClass="validate[required]" delimiter="&nbsp;&nbsp;&nbsp;&nbsp;"/></div></td>
						    		<td>熟悉何种外语及熟练程度</td>
						    		<td><form:input path="foreignLevel" size="30"/></td>
						    	</tr>
						    	<tr>
						    		<td>工作简历<label style="color: red;">&nbsp;*</label></td>
						    		<td colspan="3"><form:textarea path="jobResume"  cssClass="validate[required]" cssStyle="resize:none; width:99%;height:300px;"/></td>
						    	</tr>
						    	<tr>
						    		<td>主要科研成果、论著、论文及获奖情况</td>
						    		<td colspan="3"><form:textarea path="situationResume" cssStyle="resize:none; width:99%;height:300px;"/></td>
						    	</tr>
					  		</table>
				  		</td>
			    	</tr>
				</table>
			</form:form>
		</div>
		<div id="tb-toolbar" data-options="region:'south',border:false" style="text-align:center;height:30px;">
			<c:if test="${!m.deleted}">
			<c:if test="${m.status != 'submitthrough'}">
		  	<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0);" onclick="javascript:$('#editForm').submit();">保存</a>
		  	<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0);" onclick="javascript:$('#editForm').form('reset');">重置</a>
		  	</c:if>
		  	<c:if test="${not empty m.id && m.status == 'useredit'}">
		  	<a id="tb-through" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0);">提交审核</a>
		  	</c:if>
	  		</c:if>
		</div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#isVoyage').bind('change', function(){
			if ($('#isVoyage').prop('checked')){
				$('#div-voyage').show();
			} else {
				$('#div-voyage').hide();
			}
		}).trigger('change');
		
		$('#birthday').datebox({
			onSelect:function(date){
				$('#birthday_show').val(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
			}
		});
		
	    $('#organizations').combotree({
	    	url:'${organizationUrl}',
	    	width:500,
	    	panelWidth:500,
	    	panelHeight:330,
	    	multiple:true,
	    	editable:false,
	    	onlyLeafCheck:true,
	    	lines:true,
	    	onCheck:function(node, checked){
	    		if (checked){
	    			$('#organizationNames').val(node.text + $('#organizationNames').val());
	    		} else {
	    			$('#organizationNames').val($('#organizationNames').val().replace(node.text, ''));
	    		}
	    		$('#organizationNames').val($('#organizationNames').val().replace(',', ''));
	    	},
	    	onBeforeCheck : function(node, checked){
				if (node.attributes.root || node.attributes.isParent){
					return false;
				}
			}
		});
	    
		$('#nationId').combobox({
			panelWidth:150,
			panelHeight:130,
			url:'${ctx}/personnel/nation/canUse',
			method:'get',
			valueField:'id',
			textField:'name',
			editable:false,
			onSelect:function(record){
				$('#nationName').val(record.name);
			}
		});
		
		$('#tb-photo').bind('click', function(){
			$.ewcms.openWindow({
	    		src:'${ctx}/personnel/photo/personal/save',
	    		width:380,
	    		height:160,
	    		title:'上传照片'
	    	});
		})
		
		$('#isAllowance').combobox({
			width:80,
			panelHeight:'auto',
			editable:false,
			onSelect:function(record){
				if (record.value == 'TRUE'){
					$('#div-allowances').show();
				} else {
					$('#div-allowances').hide();
				}
			},
			onLoadSuccess:function(){
				if ($('#isAllowance').combobox('getValue') == 'TRUE'){
					$('#div-allowances').show();
				} else {
					$('#div-allowances').hide();
				}
			}
		});
		
		$('#tb-through').bind('click', function(){
			$.messager.confirm('提示', '你确定提交信息进行审核吗？', function(r){
				if (r){
					$.post('${ctx}/personnel/archive/through', {}, function(data){
						if (data.success){
	            			$('#tb-photo').hide();
	            			$('#tb-toolbar').hide();
	            			$('#ifrmex').attr('src', '${ctx}/personnel/archive/educationExperience/${m.userId}/view');
	            		}
						$('#status').text(data.message);
	            		$.messager.alert('审核', data.message, 'info');
					});
				}
			});
		});
		
		$('#tb-print').bind('click', function(){
			window.open('${ctx}/personnel/archive/printCurrentUser','popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,scrollbars=yes,left=' + (window.screen.width - 1280)/ 2 + ',top=' + (window.screen.height - 700) / 2);
		});
		
		var validationEngine = $("#editForm").validationEngine({
	    	relative: true,
	        overflownDIV: '#divOverflown',
	    	promptPosition:'topLeft',
	    	showOneMessage: true
	    });
	    <ewcms:showFieldError commandName="m"/>
	    
		<c:choose>
	    	<c:when test="${m.deleted && empty view}">
	    		$('#status').text('个人信息已被禁止操作，请联系管理员！');
	    	</c:when>
	    	<c:otherwise>
			    $.post('${ctx}/personnel/archive/history/last?archiveId=${m.id}', {}, function(data) {
					var status = '信息未保存，说明：请填写完个人信息'
					if (data != null){
						status = (data.statusInfo == undefined ? '个人信息未保存' : data.statusInfo) + '，说明：' + (data.reason == undefined ? '个人信息未保存' : data.reason);
					}
					$('#status').text(status);
				});
			 </c:otherwise>
		 </c:choose>
	});
</script>
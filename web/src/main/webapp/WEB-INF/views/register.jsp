<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="注册 - 学会用户"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/register.css"/>
	<ewcms:showMessage/>
	<div class="w1000 clearfix p_relative">
  		<div class="">
			<div class="form_area">
     			<h1 class="title" style="text-align: center;font-size:200%">注册学会会员</h1>
		 		<form:form id="registerForm" name="registerForm" action="${ctx}/register" method="post" commandName="m">
		    		<ewcms:showGlobalError commandName="m"/>
		    		<form:hidden path="id"/>
		    		<form:hidden path="createDate"/>
		    		<form:hidden path="salt"/>
			     	<div class="box">
			       		<div class="bd form_list">
			        		<div class="item indexput clearfix">
			          			<label class="txt_label">用户名</label>
			          			<span class="input_wrap">
			          				<form:input path="username" cssClass="txt_input txt_270 validate[required,custom[username],ajax[ajaxCall]]"/>
			          			</span>
			          			<div style="display:block" class="txt_tips txt_tips_double">5到20个汉字、字母、数字或<br>下划线</div>
			        		</div>
			        		<div class="item clearfix">
			        			<label class="txt_label">密码</label>
			        			<span class="input_wrap">
			        				<form:password path="password" cssClass="txt_input txt_270 validate[required,minSize[6],maxSize[20]]"/>
			        			</span>
			        			<div style="display:block;" class="txt_tips txt_tips_double">6-20位字符，不能使用空格、<br>注册帐号</div>
			        		</div>
			        		<div class="item clearfix">
			        			<label class="txt_label">确认密码</label>
			        			<span class="input_wrap">
			        				<input type="password" class="txt_input txt_270 validate[condRequired[password],equals[password]]">
			        			</span>
			        		</div>
			        		<div class="item clearfix">
			        			<label class="txt_label">电子邮箱</label>
			        			<span class="input_wrap">
			        				<form:input path="email" cssClass="txt_input txt_270 validate[required,custom[email],ajax[ajaxCall]]"/>
			        			</span>
			        			<div style="display:block" class="txt_tips">请输入您常用的邮箱</div>
			        		</div>
			        		<div class="item clearfix">
			        			<label class="txt_label">手机号</label>
			        			<span class="input_wrap">
			        				<form:input path="mobilePhoneNumber" cssClass="txt_input txt_270 validate[required,custom[mobilePhoneNumber],ajax[ajaxCall]]"/>
			        			</span>
			        			<div style="display:block" class="txt_tips">请输入您使用的手机号码</div>
			        		</div>
			        		<div class="item clearfix code">
			          			<label class="txt_label">验证码</label>
			          			<span class="input_wrap">
			          				<input type="text"  name="jcaptchaCode" id="jcaptchaCode" class="txt_input txt_124 validate[required,ajax[ajaxJcaptchaCall]]">
			          			</span>
			          			<img height="30" width="62" class="code_img" src="${ctx}/jcaptcha.jpg" id="id_checkcode">
			         			<a class="code_change" href="javascript:void(0);" id="findCodeRefresh">换一个</a>
			          			<div style="display:block" class="txt_tips" id="findCodeTips">请输入验证码</div>
			        		</div>
			       			<div class="item clearfix pt_10 mb_0">
	            				<input type="checkbox" class="txt_check" name="agreement" id="agreement" checked="checked">
	           					<label class="txt_check_label gray" for="agreement">我已阅读并同意<a target="_blank" href="#">《XXXX使用协议》</a></label>
	          				</div>
			       			<div class="item clearfix btn_area">
	           					<input type="submit" value="提交注册" class="btn btn01" id="reg-submit">
	           					<input type="button" value="返回登录" class="btn btn01" id="retrun-submit">
	         				</div>
		        			<div class="warn_text"></div>
				       </div>
				       <div class="bot"></div>
			     	</div>
			</form:form>
			</div>
	  	</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function() {
		$("#findCodeRefresh").bind('click', function(){
            var img = $('#id_checkcode');
            var imageSrc = img.attr('src');
            if(imageSrc.indexOf('?') > 0) {
                imageSrc = imageSrc.substr(0, imageSrc.indexOf('?'));
            }
            imageSrc = imageSrc + '?' + new Date().getTime();
            img.attr('src', imageSrc);
        });
		
		$('#retrun-submit').bind('click', function(){
			window.location.href = '${ctx}/login';
		})
				
		var ajaxCall = {
        	'url': '${ctx}/register/validate',
            'alertTextLoad': '* 正在验证，请稍等。。。'
        };
        $.validationEngineLanguage.allRules.ajaxCall = ajaxCall;
        
        $.validationEngineLanguage.allRules.username = {
            'regex': /^[\u4E00-\u9FA5\uf900-\ufa2d_a-zA-Z][\u4E00-\u9FA5\uf900-\ufa2d\w]{1,19}$/,
            'alertText': '* 2到20个汉字、字母、数字或下划线组成，且必须以非数字开头'
        };
        
        $.validationEngineLanguage.allRules.mobilePhoneNumber = {
            'regex': /^0{0,1}(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$/,
            'alertText': '* 手机号错误'
        };
        $.validationEngineLanguage.allRules.ajaxJcaptchaCall = {
            'url': '${ctx}/jcaptcha-validate',
            'alertTextLoad': '* 正在验证，请稍等。。。'
        };
	        
        $('#registerForm').validationEngine({
        	scroll:false,
        	promptPosition: 'centerRight',
            maxErrorsPerField: 1,
            showOneMessage: true
        });
	});
</script>

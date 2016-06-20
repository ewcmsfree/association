<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html>
    <head>
    	<title>用户登录 - 江西省科协专家库平台管理系统</title>
    	<script src="${ctx}/static/js/loading.js" charset="utf-8" type="text/javascript"></script>
    	<link href="${ctx}/static/jQuery-Validation-Engine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css"/>
    	<link href="${ctx}/static/css/login.css"  type="text/css" rel="stylesheet"/>
    	<script src="${ctx}/static/jquery/jquery-1.11.1.min.js" charset="utf-8" type="text/javascript"></script>
    	<script src="${ctx}/static/jQuery-Validation-Engine/js/jquery.validationEngine.min-ciaoca.js" charset="utf-8" type="text/javascript"></script>
		<script src="${ctx}/static/jQuery-Validation-Engine/js/languages/jquery.validationEngine-zh_CN-ciaoca.js" charset="utf-8" type="text/javascript"></script>
    	<script src="${ctx}/static/js/login.js" charset="utf-8" type="text/javascript"></script>
		<script type="text/javascript">
			$(function() {
				$("#id_checkcode").click(function() {
		            var img = $("#id_checkcode");
	            	var imageSrc = img.attr("src");
	            	if(imageSrc.indexOf("?") > 0) {
		                imageSrc = imageSrc.substr(0, imageSrc.indexOf("?"));
	            	}
	            	imageSrc = imageSrc + "?" + new Date().getTime();
	            	img.attr("src", imageSrc);
	        	});
	        	$.validationEngineLanguage.allRules.ajaxJcaptchaCall={
		            "url": "${ctx}/jcaptcha-validate",
	            	"alertTextLoad": "* 正在验证，请稍等。。。"
	        	};
	        	$("#loginForm").validationEngine({scroll:false});
        	});
		</script>
	</head>
	<body id="userlogin_body">
		<div id="user_login">
			<dl>
				<dd id="user_bottom">
				  	<ul>
				    	<li class="user_title_l">江西省科协专家库平台管理系统</li>
					</ul>
				</dd>
		  		<dd id="user_top">
			  		<ul>
			    		<li class="user_top_l"></li>
			    		<li class="user_top_c"></li>
			    		<li class="user_top_r"></li>
			    	</ul>
		    	</dd>
				<form:form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
			  		<dd id="user_main">
				  		<ul>
				    		<li class="user_main_l"></li>
				    		<li class="user_main_c">
				    			<div class="user_main_box">
				    				<ul>
				      					<li class="user_main_text">用户名：</li>
				      					<li class="user_main_input"><input type="text" id="username" name="username" value="${username}" class="txtusernamecssclass validate[required] span2" /></li>
				      				</ul>
				    				<ul>
				      					<li class="user_main_text">密&nbsp;&nbsp;&nbsp;&nbsp;码：</li>
				      					<li class="user_main_input"><input type="password" id="password"  name="password" class="txtpasswordcssclass validate[required] span2"/></li>
				      				</ul>
				      				<c:if test="${jcaptchaEbabled}">
				    				<ul>
				      					<li class="user_main_text">验证码：</li>
				      					<li class="user_main_input">
				      						<input type="text" id="jcaptchaCode" name="jcaptchaCode" class="txtvalidatecodecssclass validate[required,ajax[ajaxJcaptchaCall]] span2"/>
				        				</li>
				        				<li>
	                            			<img id="id_checkcode" width="65" height="20" src="${ctx}/jcaptcha.jpg" title="点击更换验证码" style="padding-left: 5px;"/>
	                            		</li>
				        			</ul>
				        			</c:if>
				    				<ul>
				      					<li class="user_main_text"></li>
				      					<li class="user_main_input">
				      						<label class="checkbox inline" for="rememberMe"> <input type="checkbox" id="rememberMe" name="rememberMe"/> 下次自动登录</label>
				        				</li>
				        			</ul>
				        			<ul>
				        				<li class="user_main_text"></li>
				        				<li class="user_main_input"><a id="register" href="${ctx}/register" style="text-decoration:none;">注册用户</a></li>
				        				<li>
				        					<span class="error" style="padding-left: 20px;"><ewcms:showMessage/></span>
				        				</li>
				        			</ul>
			        			</div>
			        		</li>
			    			<li class="user_main_r">
			    				<input class="ibtnentercssclass" id="ibtnenter" style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; border-right-width: 0px" type="image" src="${ctx}/static/image/login/user_botton.gif" name="ibtnenter"/> 
			    			</li>
			    		</ul>
		    		</dd>
		    	</form:form>
			    <dd id="user_bottom">
				  	<ul>
				    	<li class="user_bottom_l"></li>
				    	<li class="user_bottom_c"></li>
					    <li class="user_bottom_r"></li>
					</ul>
				</dd>
			</dl>
		</div>
	</body>
</html>
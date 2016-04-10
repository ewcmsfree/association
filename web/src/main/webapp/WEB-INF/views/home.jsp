<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="EWCMS 江西省科协管理平台" index="true"/>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:false" class="head">
			<%@ include file="home/head.jsp" %>
		</div>
		<div data-options="region:'center'" style="overflow:auto;">
			<div id="index" class="easyui-layout" data-options="fit:true,border:false">  
				<%@ include file="home/body.jsp" %>
			</div>
			<div id="center" class="easyui-layout" data-options="fit:true">  
				<div id="west" data-options="region:'west',split:false" title="子菜单项" style="width:163px;">
					<%@ include file="home/menu.jsp" %>
				</div>
				<div data-options="region:'center',border:false" style="overflow:auto;">
					<c:forEach items="${menus}" var="m">
						<div class="easyui-tabs" id="tab-${m.id}" data-options="fit:true,border:false" style="display:none;"></div>
					</c:forEach>
        		</div>
        		<%@ include file="home/tabsMenu.jsp" %>
			</div>
	    </div>
    </div>   
    <ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript" src="${ctx}/static/js/clock.js"></script>
<script type="text/javascript" src="${ctx}/static/js/polling.js"></script>
<script type="text/javascript">
	$(function(){
		var clock = new Clock();
		clock.display(document.getElementById('clock'));
		
	    $('#center').hide();
	    
	    $('#button-main').bind('click',function(){
	        $('#mm').menu('show',{
	            left:$(this).offset().left - 80,
	            top:$(this).offset().top + 18
	        });
	    }).hover(function(){
	        $(this).addClass('l-btn l-btn-plain m-btn-plain-active');
	    },function(){
	        $(this).removeClass('l-btn l-btn-plain m-btn-plain-active');
	    });
	    
	    $('#user-menu').bind('click',function(){
	    	$.ewcms.openWindow({
	    		windowId:'#edit-window',
	    		iframeId:'#editifr',
	    		src:'${ctx}/security/user/loginUser/updateInfo',
	    		width:550,
	    		height:500,
	    		title:'修改用户信息'
	    	});
	    });
	    $('#password-menu').bind('click',function(){
	    	$.ewcms.openWindow({
	    		windowId:'#edit-window',
	    		iframeId:'#editifr',
	    		src:'${ctx}/security/user/loginUser/changePassword',
	    		width:550,
	    		height:240,
	    		title:'修改密码'
	    	});
	    });
	    
	    $('#exit-menu').bind('click',function(){
	        window.location.href = '${ctx}/logout';
	    });
	        
	    $('li[id^="menu-"]').bind('click', function(){
			var id = $(this).get(0).id.substring(5);
			
			$('li[id^="menu-"]').removeClass('tabs-selected');
			$('div[id^="menusub-"]').hide();
			$('div[id^="tab-"]').hide();
			
			$('#menu-' + id).addClass('tabs-selected');
			$('#menusub-' + id).show();
			$('#tab-' + id).show();
			
			if (id == 'index'){
				$('#index').show();
				$('#center').hide();
			}else{
				$('#index').hide();
				$('#center').show();
				$('#west').panel('setTitle', $(this).text());
				$('#tab-' + id).tabs('resize');
			}
			$('.easyui-accordion').accordion('resize');
		});
		
		var currentDate = new Date();
        var poll = new Poll();
	});
</script>
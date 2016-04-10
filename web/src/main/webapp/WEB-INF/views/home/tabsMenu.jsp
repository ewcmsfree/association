<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	
<div id="tabsMenu" class="easyui-menu" style="display:none;">
    <div id="closecur">关闭当前</div>
    <div id="closeall">关闭全部</div>
	<div id="closeother">关闭其他</div>
	<div class="menu-sep"></div>
	<div id="closeright">关闭右侧</div>
    <div id="closeleft">关闭左侧</div>
</div>
<script type="text/javascript">
	$(function(){
		//关闭当前tab
		$("#closecur").bind("click",function(){
			var currtab_title = $('#tabsMenu').data("tabTitle");
			var parenttab_id = $('#tabsMenu').data("parentTabId");
			$(parenttab_id).tabs('close', currtab_title);
		});
		
		//关闭所有tab
		$("#closeall").bind("click",function(){
			var parenttab_id = $('#tabsMenu').data("parentTabId");
			$('.tabs-inner span').each(function(i,n){
		        var t = $(n).text();
		        $(parenttab_id).tabs('close',t);
		    });
		});
		
		//关闭非当前tab
		$("#closeother").bind("click",function(){
			var currtab_title = $('#tabsMenu').data("tabTitle");
			var parenttab_id = $('#tabsMenu').data("parentTabId");
		    $('.tabs-inner span').each(function(i,n){
		        var t = $(n).text();
		        if(t != currtab_title)
		        	$(parenttab_id).tabs('close',t);
		    });
		});
		
		//关闭当前标签页右侧tab
		$("#closeright").bind("click",function(){
			var currtab_title = $('#tabsMenu').data("tabTitle");
			var parenttab_id = $('#tabsMenu').data("parentTabId");
			
		    var tablist = $(parenttab_id).tabs('tabs');
		    var tab = $(parenttab_id).tabs('getTab', currtab_title);
		    var index = $(parenttab_id).tabs('getTabIndex',tab);
		    for(var i = tablist.length-1;i>index;i--){
		        $(parenttab_id).tabs('close',i);
		    }
		});
		
		//关闭当前标签页左侧tab
		$("#closeleft").bind("click",function(){
			var currtab_title = $('#tabsMenu').data("tabTitle");
			var parenttab_id = $('#tabsMenu').data("parentTabId");
			var tab = $(parenttab_id).tabs('getTab', currtab_title);
			var index = $(parenttab_id).tabs('getTabIndex',tab);
		    var num = index-1;
		    for(var i=0;i<=num;i++){
		        $(parenttab_id).tabs('close',0);
		    }
		});
	});
</script>
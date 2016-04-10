<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="个人中心 - 消息"/>
	<div id="tab-message" class="easyui-tabs" data-options="border:false,fit:true,tabPosition:'right',headerWidth:75">
		<div title="收件箱" style="overflow:hidden;">
			<iframe id="receive"  name="receive" class="editifr" src="${ctx}/personal/message/receive/index"></iframe>	
		</div>			
		<div title="发件箱"  style="overflow:hidden;">
			<iframe id="send" name="send" class="editifr" src=""></iframe>
		</div>
	</div>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tab-message').tabs({
			onSelect:function(title){
				if(title == '发件箱' && $('#send').attr('src') == ''){
					$('#send').attr('src','${ctx}/personal/message/send/index');
				}
			}
		});
	});
</script>
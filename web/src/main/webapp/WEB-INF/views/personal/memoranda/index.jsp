<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="个人备忘录"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal/memoranda.css"></link>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:false" style="padding: 2px;">
      		<table style="width:100%;" cellspacing="0" cellpadding="6" border="0">
 				<tr>
 					<td width="40%" align="left" valign="middle"><a id="tb-today" class="easyui-linkbutton" data-options="iconCls:'icon-memoranda-today'" href="javascript:void(0);">今天</a>&nbsp;&nbsp;&nbsp;${toDayLunar}</td>
 					<td width="20%" align="center" valign="middle"><a id="tb-notedetial" class="easyui-linkbutton" data-options="iconCls:'icon-memoranda-list'" href="javascript:void(0);">备忘录列表</a></td>
 					<td width="40%" align="right" valign="middle">
 						<a id="tb-prevMonth" class="easyui-linkbutton" data-options="iconCls:'icon-memoranda-prev'" href="javascript:void(0);">上一个月</a>&nbsp;
						<input type="text" id="year" name="year" size="3" style="background-color:transparent;border:0;" readonly="readonly" value="${year}"/>年 
						<input type="text" id="month" name="month" size="1" style="background-color:transparent;border:0;" readonly="readonly" value="${month}"/>月&nbsp;
						<a id="tb-nextMonth" class="easyui-linkbutton" data-options="iconCls:'icon-memoranda-next'" href="javascript:void(0);">下一个月</a>
 					</td>
				</tr>
        		<tr>
          			<td colspan="4">
            			<table id="result" style="width:100%;" cellspacing="0" cellpadding="0" bordercolor="#aaccee" border="1">${result}</table>
	      			</td>
        		</tr>
      		</table>
    	</div>
    </div>
<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('.a_memoranda_value').not('span.span_title').draggable({
			revert:true,
			proxy:'clone'
		});
		
		$('.div_memoranda').not('span.span_title').droppable({
			onDragEnter:function(e, source){},
		    onDragLeave:function(e, source){},
		    onDrop:function(e, source){
		    	$(this).append(source);
			    var divMemoId = $(source).attr('id');    
			    var targetDivId = $(source).parents('div:first').attr('id');
			    var memorandaId = divMemoId.split('_')[3];
			    var dropDay = targetDivId.split('_')[2];
			    $.post('${ctx}/personal/memoranda/'  + memorandaId + '/' + $('#year').val() + '/' + $('#month').val() + '/' + dropDay + '/drop',{},function(result){
			    	//$.messager.alert('提示', result.message, 'info');
			    });
		     }
		 });
		
		$('#tb-today').bind('click', function(){
			$.post('${ctx}/personal/memoranda/${currentYear}/${currentMonth}/toDay',{},function(result) {
				if (result.success){
		    		$('tr').remove('.memoranda_tr');
		    		$('#result').append(result.message);
				} else {
					$.messager.alert('提示', result.message, 'info');
				}
		    });
		});
		
		$('#tb-notedetial').bind('click', function(){
			$.ewcms.openWindow({windowId:'#edit-window',iframeId:'#editifr', src: '${ctx}/personal/memoranda/list', closable:false, width:1300,height:500,title:'备访录列表'});
		});
		
		var monthSelect = function(year, month, weight){
			loadingEnable();
			var monthValue = parseInt(month) + weight;
		    if (monthValue >= 13){
		        year = parseInt(year) + 1;
		        monthValue = monthValue - 12;
		    }else if (monthValue <= 0){
		        year = parseInt(year) - 1;
		        monthValue = monthValue + 12;
		    }
		    $('#year').attr('value',year);
		    $('#month').attr('value',monthValue);
		    $.post('${ctx}/personal/memoranda/' + year + '/' + monthValue + '/changeDate',{},function(result) {
		        if (result.success){
		    		$('tr').remove('.memoranda_tr');
		        	$('#result').append(result.message);
		        } else {
		        	$.messager.alert('提示', result.message, 'info');
		        }
		    });
		    loadingDisable();
		};
		
		$('#tb-prevMonth').bind('click', function(){
			monthSelect($('#year').val(), $('#month').val(), -1);
		});
		
		$('#tb-nextMonth').bind('click', function(){
			monthSelect($('#year').val(), $('#month').val(), 1);
		});
	});
	
	function addMemoranda(day){
		var title = '新增备忘(' + $('#year').val() + '年' + $('#month').val() + '月' + day + '日)';
		$('#bntRemove').linkbutton('disable');
		$.ewcms.openWindow({windowId:'#edit-window',iframeId:'#editifr',src:'${ctx}/personal/memoranda/' + $('#year').val() + '/' + $('#month').val() + '/' + day + '/save',width:700,height:360,title:title});
	};

	function editMemoranda(id){
		$('#bntRemove').linkbutton('enable');
		$.ewcms.openWindow({windowId:'#edit-window',iframeId:'#editifr',src:'${ctx}/personal/memoranda/save?selections=' + id, width:700,height:360,title:'修改备忘'});
	};
	
	function loadingEnable(){
		$('<div class="datagrid-mask"></div>').css({display:'block',width:'100%',height:$(window).height()}).appendTo('body');
		$('<div class="datagrid-mask-msg"></div>').html('<font size="9">正在处理，请稍候。。。</font>').appendTo('body').css({display:'block',left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	};
	 
	function loadingDisable(){
	    $('.datagrid-mask-msg').remove();
	    $('.datagrid-mask').remove();
	};
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

		<div data-options="region:'north',border:false" style="height:60px;overflow:no;">
		    <h2 align="center">欢迎使用江西省科协管理平台系统</h2>
		</div>
		<div data-options="region:'center',border:false" style="width:35%;overflow:auto;">
			<table style="width:100%;padding:0;">
		    	<tr>
		    		<td width="1%"></td>
		    		<td class="portal-column-td" width="98%">
		    			<div class="panel" style="margin-bottom:2px;">
		                 	<div class="panel-header">
		                   		<div class="panel-title">单位填报情况</div>
		                   		<div class="panel-tool">
		                    		<a href="javascript:void(0);" style="display:inline;"></a>
		                   		</div>
		                 	</div>
		                 	<div style="height:382px; padding: 5px;" title="" id="tip" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		                 		<div class="t-list">
									<table class="easyui-datagrid" data-options="height:382,url:'${ctx}/personnel/archive/querySummary',nowrap:true,pagination:true,rownumbers:true,striped:true,pageSize:10">
										<thead>
											<tr>
											    <th data-options="field:'organizationId',hidden:true">编号</th>
											    <th data-options="field:'organizationName',width:300">组织名称</th>
											    <th data-options="field:'total',width:100">登记人数</th>
											    <th data-options="field:'throughCount',width:100">已审核人数</th>
											</tr>
										</thead>
									</table>
		                 		</div>
		                 	</div>
		               	</div>
		    		</td>
		    		<td width="1%"></td>
		    	</tr>
		    </table>
		</div>
		<div data-options="region:'west',border:false" style="width:65%;overflow: auto;">
		    <table style="width:100%;padding:0;">
		    	<tr>
		    		<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title">提示栏</div>
		                   			<div class="panel-tool">
		                     			<a href="javascript:void(0);" style="display:inline;"></a>
		                   			</div>
		                 		</div>
		                 		<div style="height: 170px; padding: 5px;" title="" id="tip" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		                 			<div class="t-list">
		                 				<table style="width:100%;">
		                 					<tr>	
			                 					<td>
			                 						1、现已登记人数：<span id="totalArchive"></span>人
			                 					</td>
		                 					</tr>
		                 					<tr>
		                 						<td>
		                 							2、目前在线人数：<span id="onlineCount"></span>人
		                 						</td>
		                 					</tr>
		                 				</table>
		                 			</div>
		                 		</div>
		               		</div>
		             	</div>
		           	</td>
		           	<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title panel-with-icon">图表栏</div>
		                   			<div class="panel-icon icon-visit-analysis"></div>
		                   			<div class="panel-tool">
		                     			<a href="javascript:void(0);" class="icon-reload panel-tool-a" onclick="archiveChart();" title="刷新"></a>
		                   			</div>
		                 		</div>
			             		<div style="height: 170px; padding: 5px;" title="" class="portal-p panel-body" data-options="closable:true,collapsible:false">
		        					<div id="pieDiv" align="center"></div>
				    			</div>
				    		</div>
				    	</div>
					</td>
		           	<td width="1%"></td>
		    	</tr>
		    	<tr>
		        	<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title">公告栏</div>
		                   			<div class="panel-tool">
		                     			<a href="javascript:void(0);" style="display:inline;"></a>
		                   			</div>
		                 		</div>
		                 		<div style="height: 170px; padding: 5px;" title="" id="notice" class="portal-p panel-body" data-options="closable:true,collapsible:false"></div>
		               		</div>
		             	</div>
		           	</td>
		           	<td width="1%"></td>
		           	<td class="portal-column-td" width="48%">
		            	<div style="overflow:hidden;padding:0 0 0 0">
		               		<div class="panel" style="margin-bottom:2px;">
		                 		<div class="panel-header">
		                   			<div class="panel-title">待办事栏</div>
		                   			<div class="panel-tool">
		                     			<a href="javascript:void(0);" onclick=""></a>
		                   			</div>
		                 		</div>
		                 		<div id="todo" style="height: 170px; padding: 5px;" class="portal-p panel-body" data-options="closable:true,collapsible:false"></div>
		               		</div>
		             	</div>
					</td>
		           	<td width="1%"></td>
				</tr>
			</table>
		</div>

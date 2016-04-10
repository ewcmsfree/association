<%@tag pageEncoding="UTF-8"%>
	<div id="edit-from" class="easyui-layout" data-options="fit:true" style="border:0;">
		<div data-options="region:'center',fit:true" style="border:0;">	
			<h1 class="title">操作说明：</h1>
			<fieldset>
			  	<table class="formtable">
		        	<tr>
		        		<td>1.</td>
		        		<td>对任意节点点击鼠标右键都会出现如下选项：<br/>
							<img alt="树型菜单说明" src="${ctx}/static/image/tree/tree_menu_description.jpg" width="35%">
						</td>
					</tr>
					<tr>
			  			<td>2.</td>
			  			<td>菜单“重命名”只会修改节点的名称。</td>
			  		</tr>
			  		<tr>
			  			<td>3.</td>
			  			<td>其中每个节点还能进行拖放进行重排结构，与菜单中的“剪切”和“粘贴”功能相同。</td>
			  		</tr>
			  		<tr>
			  			<td>4.</td>
			  			<td>对节点点击鼠标左键可以对节点相关属性进行修改，与菜单中的“编辑”功能相同。</td>
			  		</tr>
				</table>
			</fieldset>
		</div>
	</div>
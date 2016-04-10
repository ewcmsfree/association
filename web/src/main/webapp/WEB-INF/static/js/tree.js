var cutNode, parentNode, beforeNode;

var Tree = function(tree, editFrame){
	this._tree = tree || '#tree';
	this._editFrame = editFrame || '#editifr';
};

Tree.prototype.init = function(options){
	var tree = this._tree;
	var editFrame = this._editFrame;
	
	var isTabs = false;
	if (options.isTabs){
		isTabs = options.isTabs;
	}
	
	$(tree).tree({
		checkbox: false,
		url : options.baseUrl + 'tree',
		dnd: true,
		formatter : function(node){
			var s = node.text;
			if (node.children){
				s +='&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
			}
			return s
		},
		onClick : function(node){
			var url = options.baseUrl + node.id;
			if (isTabs){
				url += '/tabs';
			} else {
				url += '/edit';
			}
			$(editFrame).attr('src', url);
		},
		onContextMenu : function(e, node){
			e.preventDefault();
			var rootnode = $(tree).tree('getRoot');
			if(rootnode.id == node.id){
				$('#parse-top-button').css('display', 'none');;
				$('#parse-bottom-button').css('display', 'none');;
			}else{
				$('#parse-top-button').css('display', '');;
				$('#parse-bottom-button').css('display', '');;
			}
			$(editFrame).attr('src', options.baseUrl + 'description');
			$(this).tree('select', node.target);
			$('#treeopmenu').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		},
		onBeforeDrag : function(node){
			$(editFrame).attr('src', options.baseUrl + 'description');
		},
		onBeforeDrop : function(target, source, point){
			var targetNode = $(tree).tree('getNode', target);
			var rootNode = $(tree).tree('getRoot');
			if (targetNode.id == rootNode.id && point != 'append'){
				$.messager.alert('提示', '不能移动到与根目录平级节点', 'info');
				return false;
			}
			if (targetNode.attributes.fileType){
				if (targetNode.attributes.fileType == 'FILE' && point == 'append') {
					$.messager.alert('提示', '不能移动到文件节点之内', 'info');
					return false;
				}
			}
			
		},
		onDrop : function(target, source, point){
		    var targetNode = $(tree).tree('getNode', target);
			var parseUrl = options.baseUrl + source.id + '/' + targetNode.id + '/' + point + '/move';
			
			$.post(parseUrl,{},function(data){
		        if(data.success){
		        	$(tree).tree('reload', targetNode.target);
		        }
		        $.messager.alert('提示', data.message, 'info');
			});
		}
	});
	
	$('#refresh-button').bind('click', function(){
		var node = Tree.getSelectNode(tree);
		$(tree).tree('reload', node.target);
		Tree.clearCut();
	});
	
	$('#weight-button').bind('click', function(){
		$.messager.confirm('提示', '确认要优化排序吗?', function(r){
			if (r){
				$.post(options.baseUrl + 'reweight',{},function(data){
					if (data.success){
						$(tree).tree('reload');
					}
					$.messager.alert('提示', data.message, 'info');
				});
			}
		});
	});
	
	$('#collapse-current-button, #collapse-all-button').bind('click', function(){
		if (this.id == 'collapse-all-button'){
			$(tree).tree('collapseAll');
		}else if (this.id == 'collapse-current-button'){
			var node = Tree.getSelectNode(tree);
			$(tree).tree('collapse', node.target);
		}
	});
	
	$('#expand-current-button, #expand-all-button').bind('click', function(){
		if (this.id == 'expand-all-button'){
			$(tree).tree('expandAll');
		}else if (this.id == 'expand-current-button'){
			var node = Tree.getSelectNode(tree);
			$(tree).tree('expand', node.target);
		}
	});
	
	$('#add-button').bind('click', function(){
		var node = Tree.getSelectNode(tree);
		
		//添加子节点			
		$.messager.prompt(node.text, '请输入子节点名称', function(r){
			if (r){
	            $.post(options.baseUrl + Tree.getNodeId(node) + '/appendChild',{'name':r},function(result){
		            if(result == null){
	    	    		$.messager.alert('提示','添加子节点失败','info');
	    	    		return;
		            }
					$(tree).tree('append',{
						parent: node.target,
						data:[{
							id:result.id,
							iconCls:"icon-channel-node",
							text:result.name,
							attributes:{
								isParent:false,
								root:false,
								pId:result.parentId
							}
						}]
					});	
					$(tree).tree('expand', node.target);	
	    	    });						
			}
		});
	});
	
	$('#rename-button').bind('click', function(){
		//判断是否选择了节点
		var node = Tree.getSelectNode(tree);

		//重命名节点 			
		$.messager.prompt('重命名', '请输入新的节点名称', function(r){
			if (r){
	            $.post(options.baseUrl + node.id + '/rename',{'name':r},function(data){
		            if(data.success){
		            	node.text = data.message;
						$(tree).tree('update', node);
						$.messager.alert('提示', '重命名节点成功', 'info');
		            } else {
		            	$.messager.alert('提示', data.message, 'info');
		            }
	    	    });						
			}
			$('.messager-input').val(node.text);
		});
	});
	
	$('#edit-button').bind('click', function(){
		//判断是节点择了操作节点
		var node = Tree.getSelectNode(tree);
		var url = options.baseUrl + node.id;
		if (isTabs){
			url += '/tabs';
		} else {
			url += '/edit';
		}
		$(editFrame).attr('src', url);
	});
	
	$('#delete-button').bind('click', function(){
		//判断是否选择了节点
		var node = Tree.getSelectNode(tree);
		$.messager.confirm('提示', '确认要删除『' + node.text + '』节点?', function(r){
			if (r){
    	    	//删除节点
	            $.post(options.baseUrl + node.id + '/delete',{},function(data){
		            if(data.success){
		            	if (node.attributes.root){
		            		$(tree).tree('reload', node.target);
		            	} else {
		            		$(tree).tree('remove', node.target);
		            	}
		            }
		            $.messager.alert('提示', data.message, 'info');
	    	    });	
			}
		}); 
	});
	
	$('#cut-button').bind('click', function(){
		if(!cutNode){
			//判断是否选择了操作
			var node = Tree.getSelectNode(tree);
			
			var rootnode = $(tree).tree('getRoot');
			if(rootnode.id == node.id){
				$.messager.alert('提示','不允许剪切根节点','info');
				return;
			}         			
			$.messager.confirm('提示', '确认要剪切『' + node.text + '』节点吗?', function(r){
				if (r){
	    			parentNode = $(tree).tree('getParent',node.target);
	    			var children = $(tree).tree('getChildren', parentNode.target);
	    			
	    			beforeNode = null;
	    			if (children){
	    				if (children.length > 1){
	    					for (var i = 0; i < children.length; i++) {
	    	                	if (node == children[i] && i < children.length - 1){
	    	                		beforeNode = children[i + 1];
	    	                    	break;
	    	                	}
	    	                }
	    				}
	    			}
	    			cutNode = $(tree).tree('pop', node.target);
	    			$('#parse-button').css('display', '');
	    			$('#parse-text').html('粘贴『' + node.text + '』');
	    			$('#cut-text').html('撤消『' + node.text + '』');
				}
			}); 
		} else {
			if (beforeNode){
				$(tree).tree('insert',{
					before : beforeNode.target,
					data: [cutNode]
				});
			} else {
				$(tree).tree('append',{
					parent: parentNode.target,
					data:[cutNode]
				});
			}
			Tree.clearCut();
		}	
	});
	
	$('#parse-top-button,#parse-bottom-button,#parse-append-button').bind('click', function(){
		if(!cutNode){
    		$.messager.alert('提示','请先剪切节点','info');		    	    			    	    		
    		return;
		}
		
		var buttonId = this.id;
		var point = buttonId.split('-');
	    
		var node = Tree.getSelectNode(tree);
		
	    var parseUrl = options.baseUrl + Tree.getNodeId(cutNode) + '/' + Tree.getNodeId(node) + '/' + point[1] + '/move';
	    
        $.post(parseUrl,{},function(data){
	        if(data.success){
	        	try{
	        		$(tree).tree('reload', cutNode.target);
	        	}catch(e){
	        	}
	        	if (point[1] == 'top'){
		            $(tree).tree('insert',{
						before: node.target,
						data:[cutNode]
					});	
	        	} else if (point[1] == 'bottom'){
	        		$(tree).tree('insert',{
	        			after: node.target,
						data:[cutNode]
					});	
	        	} else if (point[1] == 'append'){
	        		$(tree).tree('append',{
						parent: node.target,
						data:[cutNode]
					});	
					$(tree).tree('expand', node.target);	
	        	}
				Tree.clearCut();
	        }
	        $.messager.alert('提示', data.message, 'info');
    	});			
	});
};

Tree.getNodeId = function(node){
	if (node){
		return node.id == null ? '' : node.id;
	} else {
		$.messager.alert('提示', '选择的节点不已存在，请刷新后重试', 'info');
		return;
	}
};

Tree.getSelectNode = function(tree){
	var node = $(tree).tree('getSelected');
	if(node){
		return node;
	} else {
		$.messager.alert('提示', '请选择需要操作的节点', 'info');
		return;
	}
};

Tree.clearCut = function(){
	$('#parse-button').css('display', 'none');
	$('#parse-text').html('粘贴');
	$('#cut-text').html('剪切');
	cutNode = null;
	parentNode = null;
	beforeNode = null;
};
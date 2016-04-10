var ParentQuery = function(id, grid){
	this._id = id || '#tt';
	this._grid = grid || 'datagrid';
};

ParentQuery.prototype.init = function(operate, data){
	var id = this._id;
	var grid = this._grid;
	
	if (data == null || data == '') return;
	
	if (operate == 'add'){
		$.ewcms.query({grid:grid, gridId:id});
	} else if (operate == 'update'){
		var rowData = eval('(' + data + ')');
		if (grid == 'datagrid'){
			var rows = $(id).datagrid('getSelections');
			$.each(rows,function(index,row){
		    	if (row.id == rowData.id){
		    		$(id).datagrid('updateRow', {
						index : $(id).datagrid('getRowIndex', row),
						row : rowData
					});
		    		return;
		    	}
		    });
		} else if (grid == 'treegrid'){
			var rows = $(id).treegrid('getSelections');
			$.each(rows,function(index,row){
		    	if (row.id == rowData.id){
					$(id).treegrid('update', {
						id : row.id,
						row : rowData
					});
		    		return;
		    	}
			});
		}
		
	}
};

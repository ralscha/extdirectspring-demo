Ext.define('Todo.model.TodoItem', {
	extend: 'Ext.data.Model',

	fields: [ 'id', 'text', 'complete' ],
	sorters: [ {
		property: "id",
		direction: "DESC"
	} ],
	proxy: {
		type: 'direct',
		enablePagingParams: false,
		directFn: 'todoService.read'
	}

});
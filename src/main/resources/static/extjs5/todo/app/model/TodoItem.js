Ext.define('Todo.model.TodoItem', {
	extend: 'Ext.data.Model',

	identifier: 'negative',
	fields: [ 'id', 'text', 'complete' ],

	proxy: {
		type: 'direct',
		api: {
			create: 'todoService.create',
			read: 'todoService.read',
			update: 'todoService.update',
			destroy: 'todoService.destroy'
		},
		writer: {
			writeAllFields: true
		}
	}
});
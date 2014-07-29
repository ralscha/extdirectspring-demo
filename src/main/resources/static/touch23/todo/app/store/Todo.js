Ext.define('Todo.store.Todo', {
	extend: 'Ext.data.Store',
	config: {
		autoLoad: true,
		remoteSort: false,
		remoteFilter: false,
		model: 'Todo.model.TodoItem'		
	}
});
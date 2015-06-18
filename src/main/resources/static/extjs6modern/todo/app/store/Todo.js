Ext.define('Todo.store.Todo', {
	extend: 'Ext.data.Store',
	requires: ['Todo.model.TodoItem'],
	autoLoad: true,
	remoteSort: false,
	remoteFilter: false,
	model: 'Todo.model.TodoItem'

});
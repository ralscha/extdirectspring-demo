Ext.define('Todo.view.grid.GridModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Todo.model.TodoItem' ],
	
	data: {
		editTodo: false
	},
	
	formulas: {
		selectedTodo: function(get) {
			return get('todoGrid.selection');
		},
		todoSelected: function(get) {
			return !!get('selectedTodo');
		}
	},	
	
	stores: {
		todos: {
			model: 'Todo.model.TodoItem',
			autoLoad: true,
			remoteSort: true,
			remoteFilter: true,
			pageSize: 0
		}
	}

});
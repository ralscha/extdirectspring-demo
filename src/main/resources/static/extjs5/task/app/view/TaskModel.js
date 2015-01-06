Ext.define('Task.view.TaskModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Task.model.Task' ],

	data: {
		detailTask: false
	},

	formulas: {
		selectedTask: function(get) {
			return get('taskGrid.selection');
		}
	},

	stores: {
		tasks: {
			model: 'Task.model.Task',
			autoLoad: true,
			pageSize: 0
		},
		priorities: {
			autoLoad: true,
			data: [ {
				id: 'HIGH',
				priority: 'HIGH'
			}, {
				id: 'NORMAL',
				priority: 'NORMAL'
			}, {
				id: 'LOW',
				priority: 'LOW'
			} ],
			fields: [ 'id', 'priority' ]
		}
	}

});
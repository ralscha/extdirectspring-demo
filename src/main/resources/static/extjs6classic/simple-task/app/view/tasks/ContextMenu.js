Ext.define('SimpleTasks.view.tasks.ContextMenu', {
	extend: 'Ext.menu.Menu',
	xtype: 'tasksContextMenu',
	items: [ {
		text: 'Edit',
		id: 'edit-task-item'
	}, '-', {
		text: 'Mark Complete',
		iconCls: 'tasks-mark-complete',
		id: 'mark-complete-item'
	}, {
		text: 'Mark Active',
		iconCls: 'tasks-mark-active',
		id: 'mark-active-item'
	}, {
		text: 'Delete',
		iconCls: 'tasks-delete-task',
		id: 'delete-task-item'
	} ],

	setTask: function(task) {
		this.task = task;
	},

	getTask: function() {
		return this.task;
	}

});
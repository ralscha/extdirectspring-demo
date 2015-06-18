Ext.define('SimpleTasks.view.lists.ContextMenu', {
	extend: 'Ext.menu.Menu',
	xtype: 'listsContextMenu',
	items: [ {
		text: 'New List',
		iconCls: 'tasks-new-list',
		id: 'new-list-item'
	}, {
		text: 'New Folder',
		iconCls: 'tasks-new-folder',
		id: 'new-folder-item'
	}, {
		text: 'New Task',
		iconCls: 'tasks-new',
		id: 'new-task-item'
	}, '-', {
		text: 'Delete',
		iconCls: 'tasks-delete-folder',
		id: 'delete-folder-item'
	}, {
		text: 'Delete',
		iconCls: 'tasks-delete-list',
		id: 'delete-list-item'
	} ],

	setList: function(list) {
		this.list = list;
	},

	getList: function() {
		return this.list;
	}

});
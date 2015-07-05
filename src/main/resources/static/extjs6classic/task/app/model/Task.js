Ext.define('Task.model.Task', {
	extend: 'Ext.data.Model',
	identifier: 'negative',
	fields: [ {
		name: 'id'
	}, {
		name: 'title'
	}, {
		name: 'description'
	}, {
		name: 'dueDate',
		type: 'date',
		dateFormat: 'Y-m-d'
	}, {
		name: 'priority'
	} ],

	proxy: {
		type: 'direct',
		api: {
			create: 'taskService.create',
			read: 'taskService.read',
			update: 'taskService.update',
			destroy: 'taskService.destroy'
		},
		writer: {
			writeAllFields: true
		}
	}
});
Ext.define('SimpleTasks.model.Task', {
	extend: 'Ext.data.Model',

	identifier: 'negative',

	fields: [ {
		name: 'id',
		type: 'int'
	}, {
		name: 'title'
	}, {
		name: 'list_id',
		type: 'int'
	}, {
		name: 'due',
		type: 'date',
		dateFormat: 'Y-m-d'
	}, {
		name: 'reminder',
		type: 'date',
		dateFormat: 'c'
	}, {
		name: 'done',
		type: 'boolean',
		defaultValue: false
	}, {
		name: 'note'
	} ],

	proxy: {
		type: 'direct',
		api: {
			create: simpleTaskService.create,
			read: simpleTaskService.read,
			update: simpleTaskService.update,
			destroy: simpleTaskService.destroy
		}
	}

});
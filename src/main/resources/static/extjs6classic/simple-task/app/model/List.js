Ext.define('SimpleTasks.model.List', {
	extend: 'Ext.data.TreeModel',

	identifier: 'negative',

	fields: [ {
		name: 'id',
		type: 'int'
	}, {
		name: 'name'
	} ],

	proxy: {
		type: 'direct',
		api: {
			create: listService.create,
			read: listService.read,
			update: listService.update,
			destroy: listService.destroy
		}
	}
});
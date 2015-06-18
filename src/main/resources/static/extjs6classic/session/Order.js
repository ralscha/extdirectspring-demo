Ext.define('Order', {
	extend: 'Ext.data.Model',
	identifier: 'negative',
	
	fields: [ 'id', {
		name: 'date',
		type: 'date',
		dateFormat: 'Y-m-d'
	}, 'shipped', {
		name: 'customerId',
		reference: {
			parent: 'Customer'
		}
	} ],

	proxy: {
		type: 'direct',
		api: {
			create: sessionService.orderCreate,
			read: sessionService.orderRead,
			update: sessionService.orderUpdate,
			destroy: sessionService.orderDestroy
		}
	}
});

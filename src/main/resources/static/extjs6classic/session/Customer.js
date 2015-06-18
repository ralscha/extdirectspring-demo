Ext.define('Customer', {
	extend: 'Ext.data.Model',
	identifier: 'negative',
	
	fields: [ 'id', 'name', {
		name: 'phone',
		type: 'phonenumber'
	} ],

	proxy: {
		type: 'direct',
		api: {
			create: sessionService.customerCreate,
			read: sessionService.customerRead,
			update: sessionService.customerUpdate,
			destroy: sessionService.customerDestroy
		}
	},

	validators: {
		name: 'presence'
	}
});

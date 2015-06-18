Ext.define('BigDataStore', {
	extend: 'Ext.data.Store',
	storeId: 'BigDataStore',
	model: 'Employee',

	groupField: 'department',

	proxy: {
		type: 'direct',
		api: {
		    read: 'employeeService.read',
		    update: 'employeeService.update'
		},
		writer : {
	        writeAllFields : true
	    }
	},
	
	autoLoad: true,
	autoSync : true
});
Ext.define('Company', {
	extend: 'Ext.data.Model',

	idProperty: 'coId',
	fields: [ {
		name: 'coId'
	}, {
		name: 'company'
	}, {
		name: 'price'
	}, {
		name: 'change'
	}, {
		name: 'pctChange',
	}, {
		name: 'lastChange',
		type: 'date',
		dateFormat: 'c'
	} ],

	hasMany: {
        model: 'History', 
        name: 'history',
        foreignKey: 'customerId'
    },
	
	proxy: {
		type: 'direct',
		directFn: 'areService.readWithHistory'
	}

});
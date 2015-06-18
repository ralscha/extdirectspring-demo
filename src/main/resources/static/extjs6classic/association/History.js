Ext.define('History', {
	extend: 'Ext.data.Model',

	fields: [ {
		name: 'id'
	}, {
		name: 'date',
		type: 'date',
		dateFormat: 'c'
	}, {
		name: 'text'
	}, {
		name: 'companyId',
		reference: 'Company'
	} ]

});
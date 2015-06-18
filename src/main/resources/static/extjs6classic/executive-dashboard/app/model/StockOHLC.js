Ext.define('ExecDashboard.model.StockOHLC', {
	extend: 'ExecDashboard.model.Base',

	fields: [ 'company', {
		name: 'time',
		type: 'date',
		dateFormat: 'Y-m-d'
	}, 'open', 'high', 'low', 'close' ]
});

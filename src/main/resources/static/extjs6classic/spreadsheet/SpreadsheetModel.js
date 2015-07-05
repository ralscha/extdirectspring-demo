Ext.define('SpreadsheetModel', {
	extend: 'Ext.app.ViewModel',

	stores: {
		monthlySales: {
			model: 'MonthlySales',
			autoLoad: true,
			autoSync: true,
			pageSize: 0
		}
	}

});
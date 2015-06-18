Ext.define('ExecDashboard.view.quarterly.QuarterlyModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.quarterly',

	requires: [ 'ExecDashboard.model.StockOHLC' ],

	data: {
		// Set by the router
		company: null,
		stockRange: '2Y'
	},

	stores: {
		stocks: {
			model: 'ExecDashboard.model.StockOHLC',
			autoLoad: true,
			remoteFilter: true,
			remoteSort: true,
			filters: {
				property: 'company',
				value: '{company}'
			},
			proxy: {
				type: 'direct',
				directFn: execDashboardService.readStockOHLC
			},
		},

		statements: {
			fields: [ 'name', 'thumb', 'url', 'type' ],
			proxy: {
				type: 'direct',
				directFn: execDashboardService.readStatements
			},
			autoLoad: true
		}
	}
});

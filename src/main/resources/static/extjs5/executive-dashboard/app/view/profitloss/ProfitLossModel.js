Ext.define('ExecDashboard.view.profitloss.ProfitLossModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.profitloss',

	requires: [ 'ExecDashboard.model.FullProfitloss' ],

	stores: {
		profitloss: {
			model: 'ExecDashboard.model.FullProfitloss',
			autoLoad: false,
			remoteFilter: false,
			remoteSort: false,
			groupField: 'account',
			proxy: {
				type: 'direct',
				directFn: execDashboardService.readProfitloss
			}
		}
	}
});

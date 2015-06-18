Ext.define('ExecDashboard.view.kpi.KpiModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.kpi',

    requires: [
        'ExecDashboard.model.Kpi'
    ],

    data: {
        // This property is placed in the ViewModel by routing
        // kpiCategory: null
    },

    stores: {
        kpiMain: {
        	model: 'ExecDashboard.model.Kpi',
            autoLoad: true,
            pageSize: 0,
            remoteFilter: true,
            filters: {
                property: 'category',
                value: '{kpiCategory}'
            },
			proxy: {
				type: 'direct',
				directFn: execDashboardService.readKpi
			},
        }
    }
});

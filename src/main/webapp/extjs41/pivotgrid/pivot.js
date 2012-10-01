Ext.onReady(function() {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

	Ext.tip.QuickTipManager.init();

	var grid = Ext.create('Ext.ux.grid.mzPivotGrid', {
		renderTo: Ext.getBody(),
		title: 'Pivot grid',
		height: 600,
		width: '100%',
		enableLocking: true,
		enableGrouping: true,
		viewConfig: {
			trackOver: true,
			stripeRows: false
		},

		tbar: [ {
			xtype: 'button',
			text: 'Refresh',
			handler: function() {
				grid.refresh();
			}
		} ],

		store: Ext.create('Ext.data.Store', {
			autoLoad: true,
			fields: [ {
				name: 'economy',
				mapping: 'e'
			}, {
				name: 'region',
				mapping: 'r'
			}, {
				name: 'year',
				mapping: 'y'
			}, {
				name: 'procedures',
				type: 'int',
				mapping: 'p'
			}, {
				name: 'time',
				type: 'int',
				mapping: 't'
			} ],
			proxy: {
				type: 'direct',
				directFn: pivotService.getEconomiesData
			}
		}),

		aggregate: [ {
			measure: 'time',
			header: 'Time',
			aggregator: 'sum',
			align: 'right',
			width: 45,
			renderer: Ext.util.Format.numberRenderer('0')
		}, {
			measure: 'procedures',
			header: 'Procedures',
			aggregator: 'sum',
			align: 'right',
			width: 65,
			renderer: Ext.util.Format.numberRenderer('0')
		} ],

		leftAxisTitle: 'Some report',
		leftAxis: [ {
			width: 90,
			dataIndex: 'region',
			header: 'Region',
			direction: 'ASC'
		}, {
			width: 120,
			dataIndex: 'economy',
			header: 'Country',
			direction: 'DESC'
		} ],

		topAxis: [ {
			dataIndex: 'year',
			width: 80,
			direction: 'DESC'
		} ]
	});

});

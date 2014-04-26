Ext.onReady(function() {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

	Ext.create('Ext.ux.grid.mzPivotGrid', {
		title: 'PivotGrid example',
		width: '100%',
		height: 600,
		renderTo: 'docbody',
		store: Ext.create('Ext.data.Store', {
			proxy: {
				type: 'direct',
				directFn: salesAction.load
			},
			autoLoad: true,
			fields: [ {
				name: 'id',
				type: 'int'
			}, {
				name: 'product',
				type: 'string'
			}, {
				name: 'person',
				type: 'string'
			}, {
				name: 'city',
				type: 'string'
			}, {
				name: 'month',
				type: 'int'
			}, {
				name: 'quarter',
				type: 'int'
			}, {
				name: 'year',
				type: 'int'
			}, {
				name: 'quantity',
				type: 'int'
			}, {
				name: 'value',
				type: 'int'
			} ]
		}),

		enableLocking: true,
		enableGrouping: true,
		
		viewConfig: {
			trackOver: true,
			stripeRows: false
		},

		features: [ {
			ftype: 'mzpivotsummary'
		} ],

		aggregate: [ {
			measure: 'value',
			header: 'Value',
			aggregator: 'sum',
			align: 'right',
			renderer: Ext.util.Format.numberRenderer('0')
		}, {
			measure: 'quantity',
			header: 'Qnt',
			aggregator: 'sum',
			align: 'right',
			renderer: Ext.util.Format.numberRenderer('0')
		} ],

		leftAxisTitle: 'Sales Report',
		leftAxis: [ {
			width: 80,
			dataIndex: 'person',
			header: 'Person'
		}, {
			width: 90,
			dataIndex: 'product',
			header: 'Product'
		}, {
			width: 90,
			dataIndex: 'month',
			header: 'Month',
			direction: 'DESC'
		} ],

		topAxis: [ {
			dataIndex: 'year',
			header: 'Year'
		}, {
			dataIndex: 'city',
			header: 'City'
		} ]
	});
});
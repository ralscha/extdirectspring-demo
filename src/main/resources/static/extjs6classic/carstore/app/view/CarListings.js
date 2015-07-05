Ext.define('Carstore.view.CarListings', {
	extend: 'Ext.container.Viewport',
	requires: [ 'Carstore.view.CarController', 'Carstore.view.CarModel' ],

	controller: {
		xclass: 'Carstore.view.CarController'
	},
	
	viewModel: {
		xclass: 'Carstore.view.CarModel'
	},
		
	renderTo: Ext.getBody(),
	layout: 'border',

	items: [{
		region: 'west',
		width: 500,
		xtype: 'panel',
		layout: {
			align: 'stretch',
			type: 'vbox'
		},
		autoScroll: true,
		frame: true,
		title: 'Car Listings',
		items: [ {
			xtype: 'gridpanel',
			bind: {
				store: '{carStore}'
			},
			reference: 'carGrid',
			columns: [ {
				xtype: 'gridcolumn',
				dataIndex: 'manufacturer',
				text: 'Manufacturer'
			}, {
				xtype: 'gridcolumn',
				dataIndex: 'model',
				text: 'Model'
			}, {
				xtype: 'numbercolumn',
				dataIndex: 'price',
				text: 'Price',
				align: 'right',
				flex: 1
			} ],
			listeners: {
				selectionchange: 'onGridSelectionChange'
			}
		}, {
			xtype: 'panel',
			reference: 'carDetail',
			tpl: [ '<div style="padding: 10px">',
			         '<img src="data/{img}" style="float: right" />',
					  'Manufacturer: {manufacturer}<br />', 
					  'Model: <a href="{wiki}" target="_blank">{model}</a><br />',
					  'Price: {price:usMoney}<br />',
					'</div>' ],				
			height: 170,
			margins: '5, 0, 0, 0'
		}, {
			xclass: 'Ext.chart.CartesianChart',
			flex: 1,
			height: 300,
			margins: '5, 0, 0, 0',				
			animate: true,
			insetPadding: 20,
			bind: {
				store: '{chartStore}'
			},		
			axes: [ {
				type: 'category',
				fields: [ 'name' ],
				position: 'bottom',
				title: 'Quality'
			}, {
				type: 'numeric',
				fields: [ 'rating' ],
				majorTickSteps: 5,
				position: 'left',
				title: 'Score',
				maximum: 5,
				minimum: 0
			} ],
			series: [ {
				type: 'bar',
				label: {
					display: 'insideEnd',
					field: 'hit',
					color: '#333'
				},
				xField: 'name',
				yField: 'rating'
			} ]

		} ]
	}, {
		xtype: 'panel',
		region: 'center',
		title: 'Wikipedia',	
		reference: 'wikiFrame',
		items: {
			xtype: 'uxiframe',
			height: '100%',
			reference: 'wikipediaIFrame'
		}
	}]
});
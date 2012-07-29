Ext.define('carstore.view.CarListings', {
	extend: 'Ext.container.Viewport',
	mixins: [ 'Deft.mixin.Controllable', 'Deft.mixin.Injectable' ],
	inject: [ 'carChart', 'carData' ],
	controller: 'carstore.controller.CarListingsViewController',

	renderTo: Ext.getBody(),
	layout: 'border',

	initComponent: function() {

		this.items = [ {
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
				itemId: 'carGrid',
				store: this.carData,
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
				} ]
			}, {
				xtype: 'panel',
				itemId: 'carDetail',
				tpl: [ '<div style="padding: 10px">',
				         '<img src="data/{img}" style="float: right" />',
						  'Manufacturer: {manufacturer}<br />', 
						  'Model: <a href="{wiki}" target="_blank">{model}</a><br />',
						  'Price: {price:usMoney}<br />',
						'</div>' ],				
				height: 170,
				margins: '5, 0, 0, 0'
			}, {
				xtype: 'panel',
				itemId: 'carChartPanel',
				layout: {
					type: 'fit'
				},
				flex: 1,
				height: 300,
				margins: '5, 0, 0, 0',
				items: [ {
					xtype: 'chart',
					itemId: 'carChart',
					animate: true,
					insetPadding: 20,
					store: this.carChart,
					axes: [ {
						type: 'Category',
						fields: [ 'name' ],
						position: 'bottom',
						title: 'Quality'
					}, {
						type: 'Numeric',
						fields: [ 'rating' ],
						majorTickSteps: 5,
						position: 'left',
						title: 'Score',
						maximum: 5,
						minimum: 0
					} ],
					series: [ {
						type: 'column',
						label: {
							display: 'insideEnd',
							field: 'rating',
							color: '#333',
							'text-anchor': 'middle'
						},
						xField: 'name',
						yField: [ 'rating' ]
					} ]
				} ]
			} ]
		}, {
			xtype: 'panel',
			region: 'center',
			title: 'Wikipedia',
			items: {
				xtype: 'uxiframe',
				itemId: 'wikipediaIFrame'
			}
		} ];

		this.callParent(arguments);
	}

});
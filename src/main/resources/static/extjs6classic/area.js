Ext.onReady(function() {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

	Ext.define('AreaData', {
		extend: 'Ext.data.Model',
		fields: [ 'name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data8', 'data9' ]
	});

	var store = Ext.create('Ext.data.Store', {
		model: 'AreaData',
		proxy: {
			type: 'direct',
			directFn: chartService.getAreaData
		}
	});

	Ext.create('Ext.Window', {
		width: 800,
		height: 600,
		hidden: false,
		shadow: false,
		maximizable: true,
		title: 'Area Chart',
		renderTo: Ext.getBody(),
		layout: 'fit',
		tbar: [ {
			text: 'Reload Data',
			handler: function() {
				store.load();
			}
		}, {
			enableToggle: true,
			pressed: true,
			text: 'Animate',
			toggleHandler: function(btn, pressed) {
				var chart = Ext.getCmp('chartCmp');
				chart.setAnimation(pressed ? {
					easing: 'ease',
					duration: 500
				} : false);
			}
		} ],
		items: {
			id: 'chartCmp',
			xtype: 'chart',
			style: 'background:#fff',
			animate: true,
			store: store,
			axes: [ {
				type: 'numeric',
				grid: true,
				position: 'left',
				fields: [ 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data8', 'data9' ],
				title: 'Number of Hits'
			}, {
				type: 'category',
				position: 'bottom',
				fields: 'name',
				title: 'Month of the Year',
				grid: true,
				label: {
					rotate: {
						degrees: -45
					}
				}
			} ],
			series: [ {
				type: 'area',
				highlight: true,
				axis: 'left',
				xField: 'name',
				yField: [ 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data8', 'data9' ],
				style: {
					opacity: 0.93
				}
			} ]
		}
	});

	store.load();
});
Ext.define("Friend", {
	extend: 'Ext.data.Model',
	config: {
		idProperty: 'id',
		fields: [ {
			name: 'id',
			type: 'integer'
		}, {
			name: 'name',
			type: 'string'
		}, {
			name: 'registered',
			type: 'date',
			dateFormat: 'F jS, Y'
		}, {
			name: 'age',
			type: 'integer'
		}, {
			name: 'rating',
			type: 'integer'
		}, {
			name: 'postCount',
			type: 'integer'
		}, {
			name: 'friendCount',
			type: 'integer'
		} ],
		proxy: {
			type: 'direct',
			api: {
				read: 'friendService.read'
			}
		}
	}
});

Ext.application({

	requires: [ 'Ext.data.Store', 'Ext.grid.Grid', 'Ext.grid.HeaderGroup', 'Ext.grid.plugin.ViewOptions', 'Ext.grid.plugin.ColumnResizing',
			'Ext.grid.plugin.SummaryRow', 'Ext.grid.plugin.PagingToolbar' ],

	launch: function() {
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

		var store = Ext.create('Ext.data.Store', {
			model: 'Friend',
			autoLoad: true
		});

		var grid = Ext.create('Ext.grid.Grid', {
			store: store,
			title: 'Friends',
			plugins: [ {
				type: 'gridviewoptions'
			}, {
				type: 'gridcolumnresizing'
			}, {
				type: 'gridsummaryrow'
			}, {
				type: 'gridpagingtoolbar'
			} ],
			columns: [ {
				text: 'Name',
				dataIndex: 'name',
				width: 200,
				editable: true,
				summaryType: 'count',
				summaryRenderer: function(value) {
					return value + ' Friends';
				}
			}, {
				text: 'Registered',
				dataIndex: 'registered',
				width: 120,
				xtype: 'datecolumn'
			}, {
				text: 'Miscellaneous',
				xtype: 'gridheadergroup',
				items: [ {
					text: 'Age',
					align: 'center',
					width: 150,
					dataIndex: 'age',
					summaryType: 'average',
					summaryRenderer: function(value) {
						return 'Avg: ' + Math.round(value) + '';
					}
				}, {
					text: 'Rating',
					align: 'center',
					width: 150,
					dataIndex: 'rating',
					summaryType: 'min',
					summaryRenderer: function(value) {
						return 'Min: ' + value + '';
					}
				}, {
					text: '# Posts',
					tpl: '{age} years',
					align: 'center',
					width: 150,
					dataIndex: 'postCount',
					summaryType: 'max',
					summaryRenderer: function(value) {
						return 'Max: ' + Math.round(value) + '';
					}
				}, {
					text: '# Friends',
					align: 'center',
					width: 150,
					dataIndex: 'friendCount',
					summaryType: 'average',
					summaryRenderer: function(value) {
						return 'Avg: ' + Math.round(value) + '';
					}
				} ]
			} ]
		});

		Ext.Viewport.add(grid);
	}
});

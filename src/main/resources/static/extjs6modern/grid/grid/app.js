Ext.define("Contact", {
	extend: 'Ext.data.Model',
	config: {
		idProperty: 'id',
		fields: [ {
			name: 'id',
			type: 'integer'
		}, {
			name: 'guid',
			type: 'string'
		}, {
			name: 'picture',
			type: 'string'
		}, {
			name: 'name',
			type: 'string'
		}, {
			name: 'gender',
			type: 'string'
		}, {
			name: 'age',
			type: 'integer'
		}, {
			name: 'company',
			type: 'string'
		}, {
			name: 'email',
			type: 'string'
		}, {
			name: 'address',
			type: 'string'
		}, {
			name: 'about',
			type: 'string'
		}, {
			name: 'registered',
			type: 'date'
		} ],
		proxy: {
			type: 'direct',
			api: {
				create: 'gridContactService.update',
				read: 'gridContactService.read',
				update: 'gridContactService.update',
				destroy: 'gridContactService.destroy'
			},
			reader: {
				rootProperty: 'records'
			}
		}
	}
});

// define the application
Ext.application({

	// require any components/classes what we will use in our example
	requires: [ 'Ext.data.Store', 'Ext.grid.Grid', 'Ext.grid.HeaderGroup', 'Ext.grid.plugin.Editable', 'Ext.grid.plugin.ViewOptions',
			'Ext.grid.plugin.MultiSelection', 'Ext.grid.plugin.PagingToolbar', 'Ext.grid.plugin.ColumnResizing', 'Ext.grid.plugin.SummaryRow' ],
	/**
	 * The launch method is called when the browser is ready, and the application can launch.
	 * 
	 * Inside our launch method we create the list and show in in the viewport. We get the lists configuration using the getListConfiguration method
	 * which we defined below.
	 * 
	 * If the user is not on a phone, we wrap the list inside a panel which is centered on the page.
	 */
	launch: function() {
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

		var store = Ext.create('Ext.data.Store', {
			model: 'Contact',
			autoLoad: true
		});

		var grid = Ext.create('Ext.grid.Grid', {
			store: store,
			title: 'Sample Grid',
			plugins: [ {
				type: 'grideditable'
			}, {
				type: 'gridviewoptions'
			}, {
				type: 'gridmultiselection'
			}, {
				type: 'gridpagingtoolbar'
			}, {
				type: 'gridcolumnresizing'
			}, {
				type: 'gridsummaryrow'
			} ],
			columns: [ {
				text: 'Name',
				dataIndex: 'name',
				width: 200,
				editable: true,
				summaryType: 'count',
				summaryRenderer: function(value) {
					return value + ' Users';
				}
			}, {
				text: 'Miscellaneous',
				xtype: 'gridheadergroup',
				items: [ {
					text: 'Age',
					tpl: '{age} years',
					align: 'center',
					width: 110,
					xtype: 'templatecolumn',
					dataIndex: 'age',
					summaryType: 'average',
					summaryRenderer: function(value) {
						return value.toFixed(2) + ' years';
					}
				}, {
					text: 'Gender',
					dataIndex: 'gender',
					width: 120,
					align: 'center',
					editable: true,
					editor: {
						xtype: 'selectfield',
						options: [ {
							text: 'Male',
							value: 'Male'
						}, {
							text: 'Female',
							value: 'Female'
						} ]
					},
					summaryType: function(records, field) {
						var ln = records.length, femaleCount = 0, i, record, value;

						for (i = 0; i < ln; i++) {
							record = records[i];
							value = record.get(field);
							if (value.toLowerCase() === 'female') {
								femaleCount++;
							}
						}

						return (ln ? Math.round((femaleCount / ln) * 100) : 0) + '% female';
					}
				} ]
			}, {
				text: 'Identifiers',
				xtype: 'gridheadergroup',
				items: [ {
					text: 'Guid',
					dataIndex: 'guid',
					width: 300,
					editable: true
				}, {
					text: 'Email',
					dataIndex: 'email',
					width: 300
				} ]
			}, {
				text: 'Address',
				dataIndex: 'address',
				width: 300
			}, {
				text: 'About',
				dataIndex: 'about',
				width: 200
			}, {
				text: 'Company',
				dataIndex: 'company',
				width: 200
			}, {
				text: 'Registered',
				dataIndex: 'registered',
				width: 120,
				xtype: 'datecolumn',
				format: 'd-m-Y'
			} ]
		});

		Ext.Viewport.add(grid);
	}
});

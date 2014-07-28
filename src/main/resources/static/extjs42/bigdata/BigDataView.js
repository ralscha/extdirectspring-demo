Ext.define('BigDataView', {
	extend: 'Ext.grid.Panel',
	requires: 'Ext.ux.grid.FiltersFeature',
	store: 'BigDataStore',
	columnLines: true,
	title: 'Editable Big Data Grid',

	plugins: [ 'bufferedrenderer', {
		xclass: 'Ext.grid.plugin.RowEditing',
		clicksToMoveEditor: 1,
		autoCancel: false
	}
	],
	features: [ {
		ftype: 'groupingsummary',
		groupHeaderTpl: '{name}',
		hideGroupedHeader: false,
		enableGroupingMenu: false
	}, {
		ftype: 'filters',
		local: true
	}, {
		ftype: 'summary',
		dock: 'bottom'
	} ],

	selType: 'checkboxmodel',

	columns: [ {
		xtype: 'rownumberer',
		width: 40,
		sortable: false,
		locked: true
	}, {
		text: 'Id',
		sortable: true,
		dataIndex: 'employeeNo',
		groupable: false,
		width: 70,
		locked: true
	}, {
		text: 'Name (Filter)',
		sortable: true,
		dataIndex: 'name',
		groupable: false,
		width: 120,
		locked: true,
		renderer: function(v, cellValues, rec) {
			return rec.get('forename') + ' ' + rec.get('surname');
		},
		editor: {
			xtype: 'textfield'
		},
		items: {
			xtype: 'textfield',
			flex: 1,
			margin: 2,
			enableKeyEvents: true,
			listeners: {
				keyup: function() {
					var store = this.up('tablepanel').store;
					store.clearFilter();
					if (this.value) {
						store.filter({
							property: 'name',
							value: this.value,
							anyMatch: true,
							caseSensitive: false
						});
					}
				},
				buffer: 500
			}
		}
	}, {
		text: 'Date of birth',
		dataIndex: 'dob',
		xtype: 'datecolumn',
		groupable: false,
		width: 115,
		filter: {

		},
		editor: {
			xtype: 'datefield'
		}
	}, {
		text: 'Join date',
		dataIndex: 'joinDate',
		xtype: 'datecolumn',
		groupable: false,
		width: 115,
		filter: {

		},
		editor: {
			xtype: 'datefield'
		}
	}, {
		text: 'Notice period',
		dataIndex: 'noticePeriod',
		groupable: false,
		filter: {
			type: 'list'
		},
		editor: {
			xtype: 'combobox',
			initComponent: function() {
				this.store = this.column.up('tablepanel').store.collect(this.column.dataIndex, false, true);
				Ext.form.field.ComboBox.prototype.initComponent.apply(this, arguments);
			}
		}
	}, {
		text: 'Email address',
		dataIndex: 'email',
		width: 200,
		groupable: false,
		renderer: function(v) {
			return '<a href="mailto:' + v + '">' + v + '</a>';
		},
		editor: {
			xtype: 'textfield'
		},
		filter: {

		}
	}, {
		text: 'Department',
		dataIndex: 'department',
		hidden: true,
		hideable: false,
		filter: {
			type: 'list'
		}
	}, {
		text: 'Absences',
		columns: [ {
			text: 'Illness',
			dataIndex: 'sickDays',
			// Size column to title text
			width: null,
			groupable: false,
			summaryType: 'sum',
			summaryRenderer: Ext.util.Format.numberRenderer('0'),
			filter: {

			},
			editor: {
				xtype: 'numberfield',
				decimalPrecision: 0
			}
		}, {
			text: 'Holidays',
			dataIndex: 'holidayDays',
			// Size column to title text
			width: null,
			groupable: false,
			summaryType: 'sum',
			summaryRenderer: Ext.util.Format.numberRenderer('0'),
			filter: {

			},
			editor: {
				xtype: 'numberfield',
				decimalPrecision: 0
			}
		}, {
			text: 'Holday Allowance',
			dataIndex: 'holidayAllowance',
			// Size column to title text
			width: null,
			groupable: false,
			filter: {

			},
			editor: {
				xtype: 'numberfield',
				decimalPrecision: 0
			}
		} ]
	}, {
		text: 'Rating',
		width: 140,
		sortable: true,
		dataIndex: 'rating',
		groupable: false
	}, {
		text: 'Salary',
		width: 110,
		sortable: true,
		dataIndex: 'salary',
		align: 'right',
		renderer: Ext.util.Format.usMoney,
		groupable: false,
		summaryType: 'average',
		summaryRenderer: Ext.util.Format.usMoney,
		filter: {

		},
		editor: {
			xtype: 'numberfield',
			decimalPrecision: 2
		}
	} ],

	viewConfig: {
		stripeRows: true
	}
});
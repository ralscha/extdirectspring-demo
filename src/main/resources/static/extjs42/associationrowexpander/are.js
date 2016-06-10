Ext.Loader.setConfig({	
	paths: {
		'Ext.ux': 'https://static.rasc.ch/ext-4.2.6.1787/examples/ux'
	}
});

Ext.require([ 'Ux.grid.plugin.AssociationRowExpander' ]);


Ext.onReady(function() {

	var grid1 = new Ext.grid.Panel({
		frame: true,
		store: new Ext.data.Store({
			model: 'Are.Company',
			autoLoad: true
			}
		),
		renderTo: 'hasManyGrid',
		width: 600,
		height: 300,
		columns: [ {
			text: 'Company',
			dataIndex: 'company',
			flex: 1
		}, {
			text: 'Price',
			dataIndex: 'price',
			renderer: Ext.util.Format.usMoney
		}, {
			text: 'Change',
			dataIndex: 'change'
		}, {
			text: '% Change',
			dataIndex: 'pctChange'
		}, {
			text: 'Last Updated',
			dataIndex: 'lastChange',
			renderer: Ext.util.Format.dateRenderer('m/d/Y')
		} ],
		plugins: [ {
			ptype: 'associationrowexpander',
			getterName: 'history',
			gridConfig: {
				height: 120,
				title: 'History',
				columns: [ {
					header: 'Text',
					dataIndex: 'text',
					flex: 1
				}, {
					header: 'Date',
					dataIndex: 'date',
					width: 200,
					renderer: Ext.util.Format.dateRenderer('n/j g:ia')
				} ]
			}
		} ]
	});

	var grid2 = new Ext.grid.Panel({
		frame: true,
		store: new Ext.data.Store({
			model: 'Are.Company',
			autoLoad: true
		}),
		renderTo: 'hasManyView',
		width: 600,
		height: 300,
		columns: [ {
			text: 'Company',
			dataIndex: 'company',
			flex: 1
		}, {
			text: 'Price',
			dataIndex: 'price',
			renderer: Ext.util.Format.usMoney
		}, {
			text: 'Change',
			dataIndex: 'change'
		}, {
			text: '% Change',
			dataIndex: 'pctChange'
		}, {
			text: 'Last Updated',
			dataIndex: 'lastChange',
			renderer: Ext.util.Format.dateRenderer('m/d/Y')
		} ],
		plugins: [ {
			ptype: 'associationrowexpander',
			getterName: 'history',
			viewConfig: {
				itemSelector: 'div.history-text',
				emptyText: 'There is no history',
				tpl: new Ext.XTemplate('<div><b>History</b></div>', '<tpl for=".">',
						'<div class="history-text">{text} ({date:date("n/j g:ia")})</div>', '</tpl>')
			}
		} ]
	});

	var grid3 = new Ext.grid.Panel({
		frame: true,
		store: new Ext.data.Store({
			model: 'Are.History',
			autoLoad: true
		}),
		renderTo: 'belongsTo',
		width: 600,
		height: 300,
		columns: [ {
			text: 'Text',
			dataIndex: 'text',
			flex: 1
		}, {
			text: 'Date',
			dataIndex: 'date',
			renderer: Ext.util.Format.dateRenderer('m/d/Y')
		} ],
		plugins: [ {
			ptype: 'associationrowexpander',
			type: 'belongsTo',
			getterName: 'getCompany',
			rowBodyTpl: new Ext.XTemplate('<div><b>Company Details:</b></div>',
					'<div>{company} - {[this.colorVal(values.price, true)]}</div>',
					'<div>{lastChange:date("n/j g:ia")}</div>',
					'<div>{[this.colorVal(values.change, true)]} {[this.colorVal(values.pctChange, false)]}</div>', {
						colorVal: function(value, money) {
							var color = value === 0 ? '000' : (value > 0 ? '093' : 'F00');

							if (money) {
								value = Ext.util.Format.usMoney(value);
							} else {
								value += '%';
							}

							return '<span style="color: #' + color + ';">' + value + '</span>';
						}
					})
		} ]
	});

	var grid4 = new Ext.grid.Panel({
		frame: true,
		store: new Ext.data.Store({
			model: 'Are.History',
			autoLoad: true
		}),
		renderTo: 'hasOne',
		width: 600,
		height: 300,
		columns: [ {
			text: 'Text',
			dataIndex: 'text',
			flex: 1
		}, {
			text: 'Date',
			dataIndex: 'date',
			renderer: Ext.util.Format.dateRenderer('m/d/Y')
		} ],
		plugins: [ {
			ptype: 'associationrowexpander',
			type: 'hasOne',
			getterName: 'getCompanyOne',
			rowBodyTpl: new Ext.XTemplate('<div><b>Company Details:</b></div>',
					'<div>{company} - {[this.colorVal(values.price, true)]}</div>',
					'<div>{lastChange:date("n/j g:ia")}</div>',
					'<div>{[this.colorVal(values.change, true)]} {[this.colorVal(values.pctChange, false)]}</div>', {
						colorVal: function(value, money) {
							var color = value === 0 ? '000' : (value > 0 ? '093' : 'F00');

							if (money) {
								value = Ext.util.Format.usMoney(value);
							} else {
								value += '%';
							}

							return '<span style="color: #' + color + ';">' + value + '</span>';
						}
					})
		} ]
	});
});

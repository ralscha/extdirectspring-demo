Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.define('CustomerGrid', {
	extend: 'Ext.grid.Panel',

	title: 'Customers',
	width: 700,
	height: 400,

	store: new Ext.data.Store({
		model: 'Company',
		autoLoad: true
	}),

	plugins: {
		ptype: 'subtable',
		association: 'history',
		headerWidth: 24,
		columns: [ {
			text: 'Text',
			dataIndex: 'text',
			width: 100
		}, {
			xtype: 'datecolumn',
			format: 'm/d/Y',
			width: 120,
			text: 'Date',
			dataIndex: 'date'
		} ]
	},

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
		xtype: 'datecolumn',
		text: 'Last Updated',
		dataIndex: 'lastChange',
		format: 'm/d/Y'	
	} ]
});
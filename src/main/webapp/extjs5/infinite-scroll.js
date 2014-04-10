Ext.require([ 'Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.grid.plugin.BufferedRenderer' ]);

Ext.onReady(function() {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

	Ext.define('Address', {
		extend: 'Ext.data.Model',
		fields: [ 'lastName', 'firstName', 'id', 'street', 'city', 'state', 'zip' ],
		proxy: {
			type: 'direct',
			directFn: personAction.loadWithPaging,
			reader: {
				rootProperty: 'records'
			}
		},
	});

	var store = Ext.create('Ext.data.BufferedStore', {
		model: 'Address',
		sorters: [ {
			property: 'lastName',
			direction: 'ASC'
		} ],
		autoLoad: true,
		leadingBufferZone: 300,
		pageSize: 100
	});

	Ext.create('Ext.grid.Panel', {
		width: 800,
		height: 500,
		title: 'Some random data',
		store: store,
		loadMask: true,
		selModel: {
			pruneRemoved: false
		},
		columns: [ {
			xtype: 'rownumberer',
			width: 50,
			sortable: false
		}, {
			text: "Last Name",
			dataIndex: 'lastName',
			sortable: true,
			flex: 1
		}, {
			text: "First Name",
			dataIndex: 'firstName',
			sortable: true,
			flex: 1
		}, {
			text: "Street Address",
			dataIndex: 'street',
			sortable: true,
			flex: 1
		}, {
			text: "City",
			dataIndex: 'city',
			sortable: true,
			flex: 1
		}, {
			text: "State",
			dataIndex: 'state',
			sortable: true,
			width: 85
		}, {
			text: "Zip Code",
			dataIndex: 'zip',
			sortable: true,
			width: 105
		} ],
		renderTo: Ext.getBody()
	});
});
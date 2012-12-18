Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.grid.plugin.BufferedRenderer'
]);


Ext.onReady(function(){
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

	Ext.define('Address', {
		extend: 'Ext.data.Model',
		fields: [ 'lastName', 'firstName', 'id', 'street', 'city', 'state', 'zip' ],
		proxy: {
			type: 'direct',
			directFn: personAction.loadWithPaging,
			reader: {
				root: 'records'
			}
		},
	});

    var store = Ext.create('Ext.data.Store', {
        model: 'Address',
        pageSize: 40,        
        sorters: [ {
			property: 'lastName',
			direction: 'ASC'
		}],
        autoLoad: true,
        buffered: true
    });

    var grid = Ext.create('Ext.grid.Panel', {
        width: 700,
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
			sortable: true
		}, {
			text: "First Name",
			dataIndex: 'firstName',
			sortable: true
		}, {
			text: "Street Address",
			dataIndex: 'street',
			sortable: true
		}, {
			text: "City",
			dataIndex: 'city',
			sortable: true
		}, {
			text: "State",
			dataIndex: 'state',
			sortable: true
		}, {
			text: "Zip Code",
			dataIndex: 'zip',
			sortable: true
		} ],
        renderTo: Ext.getBody()
    });
});
Ext.onReady(function() {

	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

	var listStore = Ext.create('Ext.data.ArrayStore', {
		fields: [ {
			name: 'list'
		}, {
			name: 'id'
		} ],
		data: [ [ 'Persons with FullName', 1 ],
				[ 'Persons with FullName and City', 2 ],
				[ 'Persons with everything', 3 ] ]
	});

	var directStore = new Ext.data.DirectStore({
		autoLoad: true,
		fields: [],
		directFn: personAction.loadPersonFullName
	});

	var pagingToolbar = Ext.create('Ext.toolbar.Paging', {
		store: directStore,
		displayInfo: true
	});

	var combo = Ext.create('Ext.form.field.ComboBox', {
		fieldLabel: 'List',
		mode: 'local',
		store: listStore,
		displayField: 'list',
		forceSelection: true,
		triggerAction: 'all',
		width: 400,
		valueField: 'list',
		listeners: {
			select: function(cmb, record, eOpts) {				
				var id = record.id;
				if (id === 1) {
					directStore.setRemoteSort(false);
					directStore.getProxy().setDirectFn(personAction.loadPersonFullName);
				} else if (id === 2) {
					directStore.setRemoteSort(false);
					directStore.getProxy().setDirectFn(personAction.loadPersonFullNameCity);
				} else if (id === 3) {
					directStore.setRemoteSort(true);
					directStore.getProxy().setDirectFn(personAction.loadPersonEverything);
				}
				directStore.sortInfo = null;
				pagingToolbar.moveFirst();
			}
		}
	});

	var grid = Ext.create('Ext.grid.Panel', {
		store: directStore,
		height: 350,
		width: 700,
		bbar: pagingToolbar,
		stripeRows: true,
		columns: [],
		forceFit: true,
		title: 'Grid with Metadata'
	});

	directStore.on({
		metachange: function(store, meta) {

			var columns = [];
			var fields = meta.fields;
			for (var i = 0; i < fields.length; i++) {
				columns.push(Ext.apply({}, fields[i], {
					dataIndex: fields[i].name
				}));
			}
			if (Ext.isDefined(meta.limit)) {
				directStore.pageSize = meta.limit;
			}

			grid.reconfigure(directStore, columns);
		}
	});

	new Ext.Panel({
		renderTo: Ext.getBody(),
		border: false,
		frame: false,
		items: [ combo, grid ]
	});
	
	combo.setValue(listStore.first());

});

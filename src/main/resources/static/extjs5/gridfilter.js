Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.define('Product', {
	extend: 'Ext.data.Model',
	fields: [ {
		name: 'id',
		type: 'int'
	}, {
		name: 'company'
	}, {
		name: 'price',
		type: 'float'
	}, {
		name: 'date',
		type: 'date',
		dateFormat: 'Y-m-d'
	}, {
		name: 'visible',
		type: 'boolean'
	}, {
		name: 'size'
	} ],
	proxy: {
		type: 'direct',
		directFn: filterActionImplementation.load,
		reader: {
			root: 'records'
		}
	}
});

Ext.onReady(function() {

	Ext.QuickTips.init();

	var store = Ext.create('Ext.data.Store', {
		autoDestroy: true,
		model: 'Product',
		autoLoad: false,
		remoteSort: true,
		pageSize: 50
	});

	var createColumns = function() {

		var columns = [ {
			dataIndex: 'id',
			text: 'Id',
			width: 60,
			filter: 'number'
		}, {
			dataIndex: 'company',
			text: 'Company',
			id: 'company',
			flex: 1,
			filter: {
	            type: 'string',
	            itemDefaults: {
	                emptyText: 'Search for...'
	            }
	        }
		}, {
			dataIndex: 'price',
			text: 'Price',
			width: 80,
			filter: 'number'
		}, {
			dataIndex: 'size',
			text: 'Size',
			filter: {
				type: 'list',
				options: [ 'small', 'medium', 'large', 'extra large' ]
			}
		}, {
			dataIndex: 'date',
			text: 'Date',
			filter: true,
			renderer: Ext.util.Format.dateRenderer('m/d/Y')
		}, {
			dataIndex: 'visible',
			text: 'Visible',
			filter: {
				type: 'boolean',
				noText: 'false',
				yesText: 'true'
			}
		} ];

		return columns;
	};

	var grid = Ext.create('Ext.grid.Panel', {
		border: false,
		store: store,
		columns: createColumns(),
		loadMask: true,
		plugins: 'gridfilters',
		dockedItems: [ Ext.create('Ext.toolbar.Paging', {
			dock: 'bottom',
			store: store
		}) ]
	});

	grid.child('[dock=bottom]').add([ '->', {
		text: 'Show Filters...',
		tooltip: 'Get Filter Data for Grid',
		handler: function() {
			var data = [];

	        // The actual record filters are placed on the Store.
	        grid.store.getFilters().each(function (filter) {
	            data.push(filter.serialize());
	        });

	        // Pretty it up for presentation
	        data = Ext.JSON.encodeValue(data, '\n').replace(/^[ ]+/gm, function (s) {
	            for (var r = '', i = s.length; i--; ) {
	                r += '&#160;';
	            }
	            return r;
	        });
	        data = data.replace(/\n/g, '<br>');

	        Ext.Msg.alert('Filter Data', data);
		}
	}, {
		text: 'Clear Filter Data',
		handler: function() {
			grid.filters.clearFilters();
		}
	} ]);

	Ext.create('Ext.Window', {
		title: 'Grid Filters Example',
		height: 400,
		width: 800,
		layout: 'fit',
		items: grid
	}).show();

	store.load({
		params: {
			dRif: '01/07/2011'
		},
		scope: this,
		callback: function(records, operation, success) {
			// console.log(records);
		}
	});
});
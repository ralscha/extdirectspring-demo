Ext.require([ 'Ext.data.*', 'Ext.form.*' ]);

Ext.onReady(function() {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

	Ext.define('DeliveryTime', {
		extend: 'Ext.data.Model',
		fields: [ 'label', 'value' ],
		proxy: {
			type: 'direct',
			directFn: deliveryTimeService.getDeliveryTimes
		}
	});

	var store = Ext.create('Ext.data.Store', {
		autoLoad: true,
		model: 'DeliveryTime'
	});

	Ext.create('Ext.form.field.ComboBox', {
		fieldLabel: 'Select a delivery method (queryMode: local)',
		renderTo: Ext.getBody(),
		displayField: 'label',
		valueField: 'value',
		width: 500,
		labelWidth: 130,
		store: store,
		queryMode: 'local',
		typeAhead: true,
		listeners: {
			change: function(cb, newValue) {
				if (!Ext.isEmpty(newValue)) {
					cb.getTrigger('clear').show();
				}
				else {
					cb.getTrigger('clear').hide();
				}
			}
		},
		triggers: {
			clear: {
				cls: 'x-form-clear-trigger',
				weight: -1,
				hidden: true,
				handler: function(cb) {
					cb.clearValue();
				}
			}
		}
	});

	var actressStore = Ext.create('Ext.data.Store', {
		fields: [ 'actress' ],
		pageSize: 0,
		proxy: {
			type: 'direct',
			directFn: deliveryTimeService.readActresses
		}
	});

	Ext.create('Ext.form.field.ComboBox', {
		fieldLabel: 'Select an actress  (queryMode: remote)',
		renderTo: Ext.getBody(),
		displayField: 'actress',
		valueField: 'actress',
		minChars: 1,
		width: 500,
		labelWidth: 130,
		store: actressStore,
		queryMode: 'remote'
	});

	Ext.create('Ext.container.Container', {
		width: 400,
		height: 100,
		layout: 'fit',
		renderTo: Ext.getBody(),

		items: [ {
			xtype: 'multiselector',
			fieldLabel: 'Select an actress',
			renderTo: Ext.getBody(),
			fieldName: 'actress',
			emptyText: 'No actresses selected',
			search: {
				field: 'actress',
				store: {
					remoteFilter: true,
					autoLoad: false,
					fields: [ 'actress' ],
					pageSize: 0,
					proxy: {
						type: 'direct',
						directFn: deliveryTimeService.readActresses
					}
	            }
			}
		} ]
	});
	
	var forumStore = Ext.create('Ext.data.Store', {
		fields: [ 'id', 'title', 'author', 'link', 'excerpt', 'pubDate' ],		
		pageSize: 0,
		proxy: {
			type: 'direct',
			directFn: deliveryTimeService.readSenchaForum
		}
	});	

	Ext.create('Ext.panel.Panel', {	    
	    title: 'Search the Ext Forums. Live search requires a minimum of 4 characters.',
	    width: 700,
	    layout: 'vbox',
	    height: 500,
	    renderTo: Ext.getBody(),

	    items: [{
	    	width: '100%',
	        xtype: 'combo',
	        store: forumStore,
	        displayField: 'title',
	        typeAhead: false,
	        hideLabel: true,
	        hideTrigger:true,

	        listConfig: {
	            loadingText: 'Searching...',
	            emptyText: 'No matching posts found.',
	            
	            itemSelector: '.search-item',

	            // Custom rendering template for each item
	            itemTpl: [
	                '<div class="search-item">',
	                    '<h3><span>{[Ext.Date.format(values.pubDate, "M j, Y")]}<br />by {author}</span>{title}</h3>',
	                    '{excerpt}',
	                '</div>'
	            ]
	        }, 
	        listeners: {
	        	select: function(cb, records) {
	        		cb.up('panel').down('uxiframe').load(records[0].data.link);
	        	}
	        }
	    }, {
	    	flex: 1,
	    	width: '100%',
	    	xtype: 'uxiframe'     
	    }]
	});
	

});
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
				} else {
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
		autoFilter: true,
		fields: ['actress'],
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
		width: 500,
		labelWidth: 130,
		store: actressStore,
		queryMode: 'remote'
	});

});
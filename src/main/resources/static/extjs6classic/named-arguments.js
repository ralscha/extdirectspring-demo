Ext.require([ 'Ext.direct.*', 'Ext.form.Panel', 'Ext.form.field.Text', 'Ext.form.field.Number' ]);

Ext.onReady(function() {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

	var form = Ext.create('Ext.form.Panel', {
		width: 360,
		renderTo: document.body,
		bodyPadding: 5,
		items: [ {
			xtype: 'textfield',
			fieldLabel: 'First Name',
			name: 'firstName',
			value: 'Evan',
			allowBlank: false,
			maxLength: 30,
			enforceMaxLength: true
		}, {
			xtype: 'textfield',
			fieldLabel: 'Last Name',
			name: 'lastName',
			value: 'Trimboli',
			allowBlank: false,
			maxLength: 30,
			enforceMaxLength: true
		}, {
			xtype: 'numberfield',
			fieldLabel: 'Age',
			name: 'age',
			value: 25,
			allowBlank: false
		} ],
		dockedItems: [ {
			dock: 'bottom',
			ui: 'footer',
			xtype: 'toolbar',
			items: [ '->', {
				formBind: true,
				text: 'Send',
				handler: function() {
					var values = form.getForm().getValues();
					values.bo = [{id:1,name:'test'},{id:2,name:'test2'}];
					namedService.showDetails(values, function(value) {
						 Ext.toast({
					            title: 'Server Response',
					            html: value,
					            align: 't',
					            bodyPadding: 10,
					            shadow: true
					        });

					});
					namedService.nonStrict({one:1,two:2,three:3,four:4,five:5,string:'aString'});
				}
			} ]
		} ]
	});
});

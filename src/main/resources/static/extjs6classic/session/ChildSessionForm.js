/**
 * This form is a popup window used by the ChildSession view. This view is added as a
 * contained window so we use the same ViewController instance.
 */
Ext.define('ChildSessionForm', {
	extend: 'Ext.window.Window',

	bind: {
		title: '{title}'
	},
	layout: 'fit',
	modal: true,
	width: 500,
	height: 430,
	closable: true,

	items: {
		xtype: 'form',
		reference: 'form',
		bodyPadding: 10,
		border: false,
		// use the Model's validations for displaying form errors
		modelValidation: true,
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [ {
			xtype: 'textfield',
			fieldLabel: 'Name',
			reference: 'name',
			msgTarget: 'side',
			bind: '{theCustomer.name}'
		}, {
			xtype: 'textfield',
			fieldLabel: 'Phone',
			reference: 'phone',
			msgTarget: 'side',
			bind: '{theCustomer.phone}'
		}, {
			xtype: 'grid',
			flex: 1,
			reference: 'orders',
			margin: '10 0 0 0',
			title: 'Orders',
			bind: '{theCustomer.orders}',
			tbar: [ {
				text: 'Add Order',
				handler: 'onAddOrderClick'
			} ],
			columns: [ {
				text: 'Id',
				dataIndex: 'id',
				width: 50
			}, {
				xtype: 'datecolumn',
				text: 'Date',
				dataIndex: 'date',
				format: 'Y-m-d',
				flex: 1
			}, {
				xtype: 'checkcolumn',
				text: 'Shipped',
				dataIndex: 'shipped'
			}, {
				xtype: 'widgetcolumn',
				width: 90,
				widget: {
					xtype: 'button',
					text: 'Remove',
					handler: 'onRemoveOrderClick'
				}
			} ]
		} ]
	},

	buttons: [ {
		text: 'Save',
		handler: 'onSaveClick'
	}, {
		text: 'Cancel',
		handler: 'onCancelClick'
	} ]
});

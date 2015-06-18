/**
 * This example demonstrates an isolated child session. When the dialog is created, a
 * child session is spawned from the parent. Any changes made to data in this session do
 * not affect the parent immediately. The changes are kept separate from the parent and
 * may then be saved to the parent or alternatively discarded to leave the parent in its
 * original state.
 */
Ext.define('ChildSession', {
	extend: 'Ext.grid.Panel',

	title: 'All Customers',

	width: 420,
	height: 320,

	viewModel: {
		xclass: 'ChildSessionModel'
	},

	controller: {
		xclass: 'ChildSessionController'
	},

	bind: {
        store: '{customers}',
        selection: '{selectedCustomer}'
    },
	
	// Create a session for this view
	session: true,

	columns: [ {
		dataIndex: 'name',
		flex: 1,
		text: 'Name'
	}, {
		dataIndex: 'phone',
		flex: 1,
		text: 'Phone'
	}, {
		xtype: 'widgetcolumn',
		width: 90,
		widget: {
			xtype: 'button',
			text: 'Edit',
			handler: 'onEditCustomerClick'
		}
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		docked: 'top',
		items: [ {
			text: 'Add Customer',
			handler: 'onAddCustomerClick'
		}, {
			text: 'Remove Customer',
			handler: 'onRemoveCustomerClick',
			bind: {
				disabled: '{!selectedCustomer}'
			}
		}, '-', {
			text: 'Show Changes',
			handler: 'onSessionChangeClick'
		}, '-', {
			text: 'Submit Changes To Server',
			handler: 'onSubmit'
		} ]
	} ]
});

Ext.define('Simple.view.user.Edit', {
	extend: 'Ext.window.Window',

	title: 'Edit User',
	layout: 'fit',
	autoShow: true,
	resizable: false,
	modal: true,
	modelValidation: true,

	items: [ {
		xtype: 'form',
		padding: '5 5 0 5',
		defaults: {
			width: 500,
			msgTarget: 'side',
			xtype: 'textfield'
		},
		items: [ {
			bind: '{editUser.firstName}',
			fieldLabel: 'First Name'
		}, {
			bind: '{editUser.lastName}',
			fieldLabel: 'Last Name'
		}, {
			bind: '{editUser.email}',
			fieldLabel: 'Email'
		}, {
			bind: '{editUser.city}',
			fieldLabel: 'City'
		} ]
	} ],

	buttons: [ {
		text: 'Save',
		handler: 'saveUser',
		bind: {
			disabled: '{!editUserValid}'
		}
	}, {
		text: 'Cancel',
		handler: 'closeWindow'
	} ]

});
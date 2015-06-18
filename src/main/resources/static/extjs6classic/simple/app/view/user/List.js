Ext.define('Simple.view.user.List', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Simple.view.user.UserController', 'Simple.view.user.UserModel' ],

	controller: {
		xclass: 'Simple.view.user.UserController'
	},
	viewModel: {
		xclass: 'Simple.view.user.UserModel'
	},

	bind: {
		store: '{userStore}'
	},

	listeners: {
		itemdblclick: 'editUser'
	},

	title: 'Usermanagement',
	reference: 'userGrid',

	columns: [ {
		header: 'ID',
		dataIndex: 'id',
		width: 50,
		sortable: false
	}, {
		header: 'First Name',
		dataIndex: 'firstName',
		flex: 1
	}, {
		header: 'Last Name',
		dataIndex: 'lastName',
		flex: 1
	}, {
		header: 'Email',
		dataIndex: 'email',
		flex: 1
	}, {
		header: 'City',
		dataIndex: 'city',
		flex: 1
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: 'New User',
			iconCls: 'icon-add',
			handler: 'createUser'
		}, {
			text: 'Delete User',
			handler: 'deleteUser',
			bind: {
				disabled: '{!userSelected}'
			},
			iconCls: 'icon-delete'
		}, '->', {
			fieldLabel: 'Filter',
			labelWidth: 40,
			xtype: 'textfield',
			listeners: {
				change: {
					fn: 'filterChange',
					buffer: 350
				}
			}
		} ]
	}, {
		xtype: 'pagingtoolbar',
		dock: 'bottom',
		reference: 'pagingtoolbar'
	} ]

});
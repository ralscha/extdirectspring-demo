Ext.define('Simple.view.user.UserController', {
	extend: 'Ext.app.ViewController',

	init: function(view) {
		var userStore = this.getStore('userStore');
		userStore.load();
		this.lookupReference('pagingtoolbar').setStore(userStore);
	},

	deleteUser: function() {
		Ext.Msg.confirm('Really delete?', 'Are you sure?', 'onDeleteUserConfirm', this);
	},

	onDeleteUserConfirm: function(choice) {
		if (choice === 'yes') {
			var selectedUser = this.getViewModel().get('selectedUser');
			selectedUser.erase({
				callback: function(e) {
					Ext.toast({
						html: 'User deleted',
						title: 'Info',
						width: 200,
						align: 't'
					});
					this.getStore('userStore').load();
				},
				scope: this
			});
		}
	},

	filterChange: function(field, newValue) {
		var usersStore = this.getStore('userStore');
		if (newValue) {
			usersStore.filter('filter', newValue);
		}
		else {
			usersStore.clearFilter();
		}
	},

	createUser: function() {
		this.getViewModel().set('editUser', Ext.create('Simple.model.User'));
		Ext.create('Simple.view.user.Edit');
	},

	editUser: function() {
		this.getViewModel().set('editUser', this.getViewModel().get('selectedUser'));
		Ext.create('Simple.view.user.Edit');
	},

	closeWindow: function(btn) {
		btn.up('window').close();
	},

	saveUser: function(btn) {
		var editUser = this.getViewModel().get('editUser');

		if (editUser.isValid()) {
			if (editUser.phantom) {
				editUser.save(function() {
					this.getStore('userStore').load();
				}, this);
			}
			else {
				this.getStore('userStore').sync();
			}
			btn.up('window').close();
		}
		
	}

});
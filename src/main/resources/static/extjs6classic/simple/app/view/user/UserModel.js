Ext.define('Simple.view.user.UserModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Simple.model.User' ],

	formulas: {
		selectedUser: function(get) {
			return get('userGrid.selection');
		},
		userSelected: function(get) {
			return !!get('selectedUser');
		},
		editUserValid: {
			bind: {
				bindTo: '{editUser}',
				deep: true
			},
			get: function(data) {
				return data.isValid();
			}
		}
	},

	stores: {
		userStore: {
			model: 'Simple.model.User',
			autoLoad: false,
			remoteSort: true,
			remoteFilter: true,
			pageSize: 30,
			autoSync: false,
			sorters: [ {
				property: 'lastName',
				direction: 'ASC'
			} ]
		}
	}

});
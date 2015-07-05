Ext.define('Simple.controller.Users', {
	extend : 'Ext.app.Controller',

	views : [ 'user.List', 'user.Edit' ],
	stores : [ 'Users' ],
	models : [ 'User' ],
	refs : [ {
		ref : 'userList',
		selector : 'userlist'
	}, {
		ref : 'userEditForm',
		selector : 'useredit form'
	}, {
		ref : 'userEditWindow',
		selector : 'useredit'
	}],

	init : function() {
		this.control({
			'userlist' : {
				itemdblclick : this.editUser,
				itemclick : this.enableDelete
			},
			'useredit button[action=save]' : {
				click : this.updateUser
			},
			'userlist button[action=add]': {
				click: this.createUser
			},			
			'userlist button[action=delete]' : {
				click : this.deleteUser
			},
			'userlist #filterField' : {
				change: {
					fn: this.onFilterFieldChange,
					buffer: 500
				}
			}
		});
		
	    this.getUsersStore().on({
	      	load: function(store) {
	            var currentPage = store.currentPage;
	            var data = store.data;

	            if (currentPage > 1 && data.length === 0) {
	                store.previousPage();
	            }
	        }
	    });	    		
	},

	onFilterFieldChange: function(cmp, newValue) {
		var myStore = this.getStore('Users');
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('filter', newValue);
		} else {
			myStore.clearFilter();
		}
	},
	
	editUser : function(grid, record) {
		var view = Ext.widget('useredit');
		view.down('form').loadRecord(record);
	},
	
	createUser : function() {
		Ext.widget('useredit');
	},
	
	deleteUser : function(button) {
		var record = this.getUserList().getSelectionModel().getSelection()[0];

		if (record) {
			this.getUsersStore().remove(record);
			this.doGridRefresh();
			this.toggleDeleteButton(false);
		}
	},

	enableDelete : function(button, record) {
		this.toggleDeleteButton(true);
	},

	toggleDeleteButton : function(enable) {
		var button = this.getUserList().down('button[action=delete]');
		if (enable) {
			button.enable();
		} else {
			button.disable();
		}
	},
	
	updateUser : function(button) {
		var form = this.getUserEditForm();
		var record = form.getRecord(); 
		var values = form.getValues();
		
		if (form.getForm().isValid()) {
			if (record) {
				record.set(values);	
				record.save();
			} else {
				var newUser = this.getUserModel().create(values);
				newUser.save();
				//this.getUsersStore().add(newUser);
				this.doGridRefresh();
			}
			this.getUserEditWindow().close();
		}				
	},
	
	doGridRefresh : function() {
		this.getUserList().down('pagingtoolbar').doRefresh();
	}
	
});
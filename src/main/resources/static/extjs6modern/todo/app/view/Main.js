Ext.define('Todo.view.Main', {
	extend: 'Ext.tab.Panel',
	xtype: 'main',
	requires: [ 'Ext.TitleBar', 'Ext.dataview.List', 'Ext.plugin.PullRefresh', 'Ext.form.Panel', 'Todo.store.Todo' ],

	tabBarPosition: 'bottom',

	items: [ {
		title: 'Todo List',
		iconCls: 'team',
		xtype: 'list',
		items: [ {
			xtype: 'titlebar',
			title: 'Todo List',
			docked: 'top',
			items: [ {
				text: 'Reload',
				handler: function() {
					this.up('list').getStore().load();
				}
			} ]
		} ],
//		plugins: [ {
//			xclass: 'Ext.plugin.PullRefresh',
//			pullText: 'Pull down to reload data'
//		} ],
		store: {
			xclass: 'Todo.store.Todo'
		},
		itemTpl: '{id} : {text}'
	}, {
		title: 'Form',
		xtype: 'formpanel',
		paramsAsHash: true,
		iconCls: 'bookmarks',
		api: {
			load: 'todoService.load',
			submit: 'todoService.submit'
		},

		scrollable: true,

		items: [ {
			docked: 'top',
			xtype: 'titlebar',
			title: 'User Form',
			items: [ {
				xtype: 'button',
				text: 'Load',
				align: 'left',
				handler: function(btn) {
					var form = btn.up('formpanel');

					form.load({
						params: {
							id: 1
						},
						success: function() {
							Ext.Msg.alert('Success', 'Form loaded successfully');
						},
						failure: function(form, result, response) {
							var errors = response.errors, ret = [], name, error;

							for (name in errors) {
								if (errors.hasOwnProperty(name)) {
									error = errors[name];

									ret.push(name + ': ' + error);
								}
							}

							Ext.Msg.alert('Failure', ret.join('<br />'));
						}
					});
				}
			}, {
				xtype: 'button',
				text: 'Submit',
				align: 'right',
				handler: function(btn) {
					var form = btn.up('formpanel');

					form.submit({
						success: function() {
							Ext.Msg.alert('Success', 'Form submit successful');
						},
						failure: function(form, result, response) {
							var errors = response.errors, ret = [], name, error;

							for (name in errors) {
								if (errors.hasOwnProperty(name)) {
									error = errors[name];

									ret.push(name + ': ' + error);
								}
							}

							Ext.Msg.alert('Failure', ret.join('<br />'));
						}
					});
				}
			} ]
		}, {
			xtype: 'textfield',
			label: 'First name',
			name: 'firstname'
		}, {
			xtype: 'textfield',
			label: 'Last nanme',
			name: 'lastname'
		}, {
			xtype: 'textfield',
			label: 'Email',
			name: 'email'
		} ]
	} ]

});
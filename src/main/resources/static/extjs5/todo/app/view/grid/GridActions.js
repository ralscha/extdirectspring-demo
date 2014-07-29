Ext.define('Todo.view.grid.GridActions', {
	extend: 'Ext.container.Container',

	requires: [ 'Todo.view.grid.GridController', 'Todo.view.grid.GridModel' ],

	controller: {
		xclass: 'Todo.view.grid.GridController'
	},

	viewModel: {
		xclass: 'Todo.view.grid.GridModel'
	},

	title: 'Grid CRUD operations',
	layout: 'border',
	style: 'padding:5px',
	items: [ {
		xtype: 'gridpanel',
		reference: 'todoGrid',
		region: 'center',
		bind: '{todos}',
		listeners: {
			itemclick: 'onTodoGridItemClick'
		},
		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				icon: 'resources/assets/plus-circle.png',
				text: 'Insert blank record',
				handler: 'insertTodo'
			}, {
				icon: 'resources/assets/minus-circle.png',
				text: 'Remove',
				handler: 'destroyTodo',
				bind: {
					disabled: '{!todoSelected}'
				}
			}, {
				text: 'Reload data',
				icon: 'resources/assets/arrow-circle-double-135.png',
				handler: 'reloadData'
			}, '->', {
				xtype: 'textfield',
				reference: 'filterField',
				triggers: {
					clear: {
						cls: 'x-form-clear-trigger',
						weight: 1,
						hideOnReadOnly: false,
						handler: 'onClearTrigger'
					}
				},
				listeners: {
					specialkey: 'onFilterFieldKey'
				},
				emptyText: 'Filter'
			}, {
				xtype: 'button',
				text: 'Filter',
				handler: 'doFilter'
			} ]
		} ],

		columns: [ {
			dataIndex: 'id',
			text: 'Id',
			width: 40
		}, {
			flex: 1,
			dataIndex: 'text',
			text: 'Text'
		}, {
			dataIndex: 'complete',
			renderer: function(value) {
				return value ? 'Done' : 'Not yet';
			},
			text: 'Complete'
		}, {
			xtype: 'actioncolumn',
			width: 40,
			items: [ {
				icon: 'resources/assets/information.png',
				tooltip: 'Click for more info',
				handler: function(grid, rowIndex, colIndex) {
					var rec = grid.getStore().getAt(rowIndex);
					Ext.Msg.alert('Item description', 'Task: ' + rec.get('text'));
				}
			} ]
		} ]
	}, {
		xtype: 'form',
		region: 'east',
		split: true,
		bind: {
			disabled: '{!editTodo}',
		},
		width: 350,
		bodyPadding: 10,
		title: 'Edit details',
		defaults: {
			xtype: 'textfield',
			anchor: '100%'
		},
		items: [ {
			disabled: true,
			fieldLabel: 'Id',
			bind: '{editTodo.id}'
		}, {
			allowBlank: false,
			msgTarget: 'side',
			fieldLabel: 'Text',
			bind: '{editTodo.text}'
		}, {
			xtype: 'checkboxfield',
			fieldLabel: 'Complete',
			bind: '{editTodo.complete}',
			boxLabel: 'Yes'
		} ],
		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				xtype: 'button',
				icon: 'resources/assets/pencil.png',
				text: 'Update',
				handler: 'updateTodo'
			} ]

		}

		]
	} ]
});
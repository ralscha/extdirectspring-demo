Ext.define('Task.view.Viewport', {
	extend: 'Ext.container.Viewport',

	requires: [ 'Task.view.TaskController', 'Task.view.TaskModel' ],

	controller: {
		xclass: 'Task.view.TaskController'
	},

	viewModel: {
		xclass: 'Task.view.TaskModel'
	},

	layout: 'fit',

	items: [ {
		xtype: 'panel',
		resizable: false,
		layout: 'border',
		collapsed: false,
		manageHeight: false,
		title: 'My Tasks',
		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				xtype: 'label'
			}, {
				xtype: 'button',
				icon: 'resources/images/add.png',
				text: 'Add Task',
				handler: 'addTask'
			} ]
		} ],
		items: [ {
			xtype: 'gridpanel',
			reference: 'taskGrid',
			flex: 1,
			region: 'west',
			split: true,
			border: '2 2 2 2',
			width: 150,
			resizable: false,
			bodyBorder: true,
			forceFit: true,

			listeners: {
				itemclick: 'onGridItemClick'
			},
			bind: {
				store: '{tasks}',
				selection: '{selectedTask}'
			},
			columns: [ {
				xtype: 'templatecolumn',
                tpl: [
                    '<span style="font-size: 1.1em; font-weight: bold">{title}</span>'
                ],
				text: 'Task',
				editor: {
					xtype: 'textfield',
					name: 'title'
				}
			}, {
				xtype: 'datecolumn',
				dataIndex: 'dueDate',
				text: 'Due Date',
				format: 'Y-m-d',
				editor: {
					xtype: 'datefield'
				}
			}, {
				xtype: 'templatecolumn',
                tpl: [
                    '<table>',
                    '    <tr>',
                    '        <td style="padding-right: 8px">',
                    '            <img src="resources/images/{priority}.png" title="{priority} Priority" />',
                    '        </td>',
                    '    	<td>',
                    '            {priority}',
                    '        </td>',
                    '    </tr>',
                    '</table>'
                ],
				text: 'Priority',
				editor: {
					xtype: 'combobox',
					name: 'priority',
					emptyText: 'Select Priority',
					editable: false,
					displayField: 'priority',
					bind: {
						store: '{priorities}'
					},
					valueField: 'priority'
				}
			} ]
		}, {
			xtype: 'panel',
			flex: 1,
			region: 'center',
			split: true,
			border: '2 2 2 2',
			bind: {
				data: {
					bindTo: '{detailTask}',
					deep: true
				}
			},
            tpl: [
                '<tpl if="values.id">',
                '    <h1><tpl if="priority"><img src="resources/images/{priority}.png" title="{priority} Priority" /></tpl>&nbsp;&nbsp;{title}</h1>',
                '    <p>',
                '        {description}',
                '    </p>',
                '    <p>',
                '        {dueDate:date("Y-m-d")}',
                '    </p>',                
                '</tpl>',
                '',
                '<tpl if="!values.id">',
                '    <h1>Click task to view</h1>',
                '</tpl>'
            ],
			resizable: false,
			layout: 'fit',
			bodyBorder: true,
			bodyPadding: '10 10 10 10',
			manageHeight: false,
			dockedItems: [ {
				xtype: 'toolbar',
				dock: 'top',
				bind: {
					hidden: '{!detailTask}'
				},
				items: [ {
					xtype: 'button',
					icon: 'resources/images/edit.png',
					text: 'Edit',
					handler: 'editTask'
				}, {
					xtype: 'tbseparator'
				}, {
					xtype: 'button',
					icon: 'resources/images/delete.png',
					text: 'Delete',
					handler: 'deleteTask'
				} ]
			} ]
		} ]
	} ]

});
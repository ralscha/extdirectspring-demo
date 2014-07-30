Ext.define('Task.view.TaskForm', {
	extend: 'Ext.window.Window',

	width: 475,
	bind: {
		title: 'Edit Task: {detailTask.title}',
	},
	modal: true,
	autoShow: true,

	items: [ {
		xtype: 'form',
		border: 0,
		bodyBorder: false,
		bodyPadding: 10,
		defaults: {
			anchor: '100%'
		},
		items: [ {
			xtype: 'textfield',
			margin: '0 0 10',
			fieldLabel: 'Title',
			labelAlign: 'right',
			bind: '{detailTask.title}',
			validateBlank: true
		}, {
			xtype: 'textareafield',
			margin: '0 0 10',
			fieldLabel: 'Description',
			labelAlign: 'right',
			bind: '{detailTask.description}',
			cols: 45,
			rows: 3
		}, {
			xtype: 'datefield',
			margin: '0 0 10',
			fieldLabel: 'Due Date',
			labelAlign: 'right',
			format: 'Y-m-d',
			bind: '{detailTask.dueDate}',
		}, {
			xtype: 'combobox',
			fieldLabel: 'Priority',
			labelAlign: 'right',
			allowBlank: false,
			allowOnlyWhitespace: false,
			emptyText: 'Select Priority',
			editable: false,
			displayField: 'priority',
			bind: {
				store: '{priorities}',
				value: '{detailTask.priority}'
			},
			valueField: 'priority'
		}, {
			xtype: 'container',
			padding: '10 10 10 10',
			layout: {
				type: 'hbox',
				align: 'middle',
				pack: 'center'
			},
			items: [ {
				xtype: 'button',
				margin: '5 5 5 5 ',
				text: 'Save',
				handler: 'saveTask'
			}, {
				xtype: 'button',
				margin: '5 5 5 5',
				text: 'Cancel',
				handler: 'cancelTask'
			} ]
		} ]
	} ]

});
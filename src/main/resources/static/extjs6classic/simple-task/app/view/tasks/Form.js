Ext.define('SimpleTasks.view.tasks.Form', {
	extend: 'Ext.form.Panel',
	xtype: 'taskForm',
	requires: [ 'Ext.ux.TreePicker' ],
	layout: 'hbox',
	cls: 'tasks-new-form',

	initComponent: function() {
		this.items = [ {
			xtype: 'component',
			cls: 'tasks-new',
			width: 24,
			height: 24
		}, {
			xtype: 'textfield',
			name: 'title',
			emptyText: 'Add a new task'
		}, {
			xtype: 'treepicker',
			name: 'list_id',
			displayField: 'name',
			store: Ext.data.StoreManager.lookup('Lists'),
			width: 195
		}, {
			xtype: 'datefield',
			name: 'due',
			value: new Date(),
			width: 95
		} ];

		this.callParent(arguments);
	}

});
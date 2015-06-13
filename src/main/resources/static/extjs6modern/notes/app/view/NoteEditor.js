Ext.define('Notes.view.NoteEditor', {
	extend: 'Ext.form.Panel',

	scrollable: 'vertical',
	reference: 'form',

	items: [ {
		xtype: "toolbar",
		docked: "top",
		title: "Edit Note",
		items: [ {
			xtype: "button",
			ui: "back",
			text: "Home",
			handler: 'onHome'
		}, {
			xtype: "spacer"
		}, {
			xtype: "button",
			ui: "action",
			text: "Save",
			handler: 'onSave'
		} ]
	}, {
		xtype: "toolbar",
		docked: "bottom",
		items: [ {
			xtype: "button",
			iconCls: "trash",
			iconMask: true,
			handler: 'onTrash'
		} ]
	}, {
		xtype: "fieldset",
		items: [ {
			xtype: 'datepickerfield',
			name: 'dateCreated',
			dateFormat: 'd.m.Y',
			label: 'Date',
			required: true
		}, {
			xtype: 'textfield',
			name: 'title',
			label: 'Title',
			required: true
		}, {
			xtype: 'textareafield',
			name: 'narrative',
			label: 'Narrative'
		} ]
	} ]

});
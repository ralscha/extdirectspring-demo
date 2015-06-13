Ext.define('Notes.view.NotesList', {
	extend: 'Ext.Container',

	layout: 'fit',

	items: [ {
		xtype: "toolbar",
		title: "My Notes",
		docked: "top",
		items: [ {
			xtype: 'spacer'
		}, {
			xtype: "button",
			text: 'New',
			ui: 'action',
			handler: 'onNew'
		} ]
	}, {
		xtype: "list",
		bind: {
			store: '{notes}'
		},
		loadingText: "Loading Notes...",
		emptyText: "<div class=\"notes-list-empty-text\">No notes found.</div>",
		onItemDisclosure: true,
		grouped: true,

		listeners: {
			disclose: 'onItemDisclose'
		},

		// plugins: [ {
		// xclass: 'Ext.plugin.PullRefresh'
		// } ],
		itemTpl: "<div class=\"list-item-title\">{id}: {title}</div><div class=\"list-item-narrative\">{narrative}</div>"
	} ]
});
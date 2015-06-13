Ext.define("Notes.model.Note", {
	extend: 'Ext.data.Model',

	identifier: 'negative',

	fields: [ {
		name: 'id',
		type: 'int'
	}, {
		name: 'dateCreated',
		type: 'date',
		dateFormat: 'd.m.Y'
	}, {
		name: 'title',
		type: 'string'
	}, {
		name: 'narrative',
		type: 'string'
	} ],

	validators: {
		dateCreated: 'presence',
		title: {
			type: 'presence',
			message: 'Please enter a title for this note.'
		}
	},
	proxy: {
		type: 'direct',
		api: {
			create: notesService.updateNotes,
			read: notesService.readNotes,
			update: notesService.updateNotes,
			destroy: notesService.destroyNotes
		},
		writer: {
			writeAllFields: true
		}
	}

});
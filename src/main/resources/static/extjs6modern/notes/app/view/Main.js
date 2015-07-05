Ext.define('Notes.view.Main', {
	extend: 'Ext.Container',
	requires: [ 'Notes.view.NotesList', 'Notes.view.NoteEditor', 'Notes.view.NotesController', 'Notes.view.NotesModel' ],

	layout: {
		type: 'card',
		animation: {
			duration: 300,
			easing: 'ease-out',
			type: 'slide',
			direction: 'left'
		}
	},

	controller: {
		xclass: 'Notes.view.NotesController'
	},

	viewModel: {
		xclass: 'Notes.view.NotesModel'
	},

	items: [ {
		xclass: 'Notes.view.NotesList'
	}, {
		xclass: 'Notes.view.NoteEditor'
	} ]

});
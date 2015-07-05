Ext.define('Notes.view.NotesController', {
	extend: 'Ext.app.ViewController',

	requires: [ 'Notes.view.NoteEditor' ],

	onItemDisclose: function(list, record) {
		this.showForm();

		this.lookupReference('form').setRecord(record);
	},

	onNew: function() {
		var newNote = Ext.create('Notes.model.Note', {
			dateCreated: new Date(),
			title: '',
			narrative: ''
		});
		this.lookupReference('form').setRecord(newNote);		
		this.showForm();
	},
	
	showForm: function() {
		this.getView().getLayout().getAnimation().setReverse(false);
		this.getView().setActiveItem(1);
	},

	onHome: function() {
		this.getView().getLayout().getAnimation().setReverse(true);
		this.getView().setActiveItem(0);
	},

	onSave: function() {
		var form = this.lookupReference('form');
		var currentNote = form.getRecord();
		form.updateRecord(currentNote);
		
		var errors = currentNote.validate();

		if (!errors.isValid()) {
			Ext.Msg.alert('Wait!', errors.getByField('title')[0].getMessage(), Ext.emptyFn);
			currentNote.reject();
			return;
		}
		
		var notesStore = this.getStore('notes');

		if (currentNote.getId() <= -1) {
			notesStore.add(currentNote);
		}
		notesStore.sync();
		
		this.onHome();
	},

	onTrash: function() {
		var form = this.lookupReference('form');
		var currentNote = form.getRecord();
		var notesStore = this.getStore('notes');
		notesStore.remove(currentNote);
		notesStore.sync();
		this.onHome();
	}

});
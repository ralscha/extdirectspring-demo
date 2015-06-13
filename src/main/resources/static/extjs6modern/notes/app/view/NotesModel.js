Ext.define('Notes.view.NotesModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Notes.model.Note' ],

	stores: {
		notes: {
			model: 'Notes.model.Note',
			autoLoad: true,
			sorters: [ {
				property: 'dateCreated',
				direction: 'DESC'
			} ],

			grouper: {
				sortProperty: "dateCreated",
				direction: "DESC",
				groupFn: function(record) {
					if (record && record.data.dateCreated) {
						return record.data.dateCreated.toDateString();
					}
					return '';
				}
			}
		}
	}

});
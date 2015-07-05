Ext.define('Sencha.view.MainModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Sencha.model.President' ],

	stores: {
		presidents: {
			model: 'Sencha.model.President',
			autoLoad: true,
			remoteFilter: false,
			remoteSort: false,
			sorters: [ {
				property: 'lastName',
				direction: 'ASC'
			} ],
			groupField: 'lastName'
		}
	}

});
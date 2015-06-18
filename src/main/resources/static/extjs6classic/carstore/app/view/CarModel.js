Ext.define('Carstore.view.CarModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Carstore.model.CarModel' ],

	stores: {
		carStore: {
			model: 'Carstore.model.CarModel',
			autoLoad: false,
			pageSize: 0
		},
		chartStore: {
			fields: [ 'name', 'rating' ]
		}
	}

});
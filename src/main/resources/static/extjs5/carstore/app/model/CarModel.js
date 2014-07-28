Ext.define('Carstore.model.CarModel', {
	extend: 'Ext.data.Model',

	fields: [ 'manufacturer', 'model', 'price', 'wiki', 'img', 'quality' ],

	proxy: {
		type: 'direct',
		directFn: 'carService.read'
	}
});
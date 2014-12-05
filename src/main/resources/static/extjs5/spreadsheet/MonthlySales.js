Ext.define('MonthlySales', {
	extend: 'Ext.data.Model',

	fields: [ 'id', {
		name: 'year',
		type: 'int'
	}, {
		name: 'jan',
		type: 'int',
		allowNull: true
	}, {
		name: 'feb',
		type: 'int',
		allowNull: true
	}, {
		name: 'mar',
		type: 'int',
		allowNull: true
	}, {
		name: 'apr',
		type: 'int',
		allowNull: true
	}, {
		name: 'may',
		type: 'int',
		allowNull: true
	}, {
		name: 'jun',
		type: 'int',
		allowNull: true
	}, {
		name: 'jul',
		type: 'int',
		allowNull: true
	}, {
		name: 'aug',
		type: 'int',
		allowNull: true
	}, {
		name: 'sep',
		type: 'int',
		allowNull: true
	}, {
		name: 'oct',
		type: 'int',
		allowNull: true
	}, {
		name: 'nov',
		type: 'int',
		allowNull: true
	}, {
		name: 'dec',
		type: 'int',
		allowNull: true
	} ],

	proxy: {
		type: 'direct',
		api: {
			//create: 'spreadsheetService.create',
			read: 'spreadsheetService.read',
			update: 'spreadsheetService.update',
			//destroy: 'spreadsheetService.destroy'
		},
		writer: {
			writeAllFields: true
		}
	}
});

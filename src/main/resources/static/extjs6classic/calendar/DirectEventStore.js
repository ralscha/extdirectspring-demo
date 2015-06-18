Ext.define('DirectEventStore', {
	extend: 'Ext.data.Store',
	requires: [ 'Ext.calendar.data.EventModel' ],
	model: 'Ext.calendar.data.EventModel',
	autoLoad: true,
	autoSync: true,
	proxy: {
		type: 'direct',
		api: {
			read: 'calendarService.read',
			create: 'calendarService.create',
			update: 'calendarService.update',
			destroy: 'calendarService.destroy'
		},
		writer: {
			writeAllFields: true
		}
	}

});
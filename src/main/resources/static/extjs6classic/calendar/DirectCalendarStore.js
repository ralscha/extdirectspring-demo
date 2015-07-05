Ext.define('DirectCalendarStore', {
	extend: 'Ext.data.Store',
	requires: [ 'Ext.calendar.data.CalendarModel' ],
	model: 'Ext.calendar.data.CalendarModel',
	autoLoad: true,
	autoSync: true,
	proxy: {
		type: 'direct',
		directFn: 'calendarService.readCalendars',
		writer: {
			writeAllFields: true
		}
	}

});
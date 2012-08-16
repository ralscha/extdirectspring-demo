Ext.define('Cal.DirectCalendarStore', {
    extend: 'Ext.data.Store',
    model: 'Extensible.calendar.data.CalendarModel',
    autoLoad: true,
    proxy: {
		type: 'direct',
		directFn: calendarService.readCalendars
	}
  
});
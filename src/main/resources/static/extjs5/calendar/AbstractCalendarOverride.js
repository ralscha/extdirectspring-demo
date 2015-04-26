Ext.define('AbstractCalendarOverride', {
	override: 'Ext.calendar.view.AbstractCalendar',

	setStartDate: function(start, refresh) {
		this.startDate = Ext.Date.clearTime(start);
		this.setViewBounds(start);
		this.store.load({
			params: {
				startDate: Ext.Date.format(this.viewStart, 'Y-m-d'),
				endDate: Ext.Date.format(this.viewEnd, 'Y-m-d')
			}
		});
		if (refresh === true) {
			this.refresh();
		}
		this.fireEvent('datechange', this, this.startDate, this.viewStart, this.viewEnd);
	}
});
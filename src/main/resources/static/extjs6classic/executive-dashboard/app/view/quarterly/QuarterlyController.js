Ext.define('ExecDashboard.view.quarterly.QuarterlyController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.quarterly',

	init: function(view) {
		view.updateActiveState = this.updateActiveState.bind(this);
	},

	menuItemClick: function(button, menuitem) {
		this.getView().setActiveState(menuitem.text);
	},

	updateActiveState: function(activeState) {

		var viewModel = this.getViewModel();
		viewModel.set('company', activeState);

		execDashboardService.readCompanyData(activeState, function(result) {
			viewModel.set('companyData', result);
		});

		this.fireEvent('changeroute', this, 'quarterly/' + activeState);
	}
});

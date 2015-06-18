Ext.define('ExecDashboard.Application', {
	extend: 'Ext.app.Application',
	name: 'ExecDashboard',
	requires: ['ExecDashboard.ux.plugin.RowExpander'],

	// The tab we want to activate if there is no "#tag" in the URL.
	defaultToken: '!kpi/clicks',

	views: [ 'ExecDashboard.view.main.Main' ],

	launch: function() {
		// Let's add a CSS class to body if flex box wrap is not implemented or broken
		// http://flexboxlayouts.com/flexboxlayout_tricks.html
		if (Ext.browser.is.Gecko && Ext.browser.version.major < 28) {
			Ext.getBody().addCls('x-flex-wrap-broken');
		}
	}
});

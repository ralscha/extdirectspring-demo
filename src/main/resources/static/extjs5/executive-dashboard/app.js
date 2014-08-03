Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
Ext.application({
	name: 'ExecDashboard',
	extend: 'ExecDashboard.Application',
	autoCreateViewport: 'ExecDashboard.view.main.Main'
});

Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.application({
	name: 'Simple',
	extend: 'Simple.Application',
	autoCreateViewport: 'Simple.view.user.List'
});

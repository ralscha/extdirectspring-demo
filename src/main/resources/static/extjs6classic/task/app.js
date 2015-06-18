Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.application({
	name: 'Task',
	extend: 'Task.Application',
	autoCreateViewport: true
});

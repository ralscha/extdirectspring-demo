Ext.require(['Ext.direct.Manager','Ext.direct.RemotingProvider', 'Ext.data.proxy.Direct'], function() {
  Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
});

Ext.application({
	name: 'Task',
	extend: 'Task.Application',
	autoCreateViewport: true
});

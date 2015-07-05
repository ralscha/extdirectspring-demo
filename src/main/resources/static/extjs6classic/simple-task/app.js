Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
Ext.application({
    name: 'SimpleTasks',
    extend: 'SimpleTasks.Application',
    autoCreateViewport: true
});

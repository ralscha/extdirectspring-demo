Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.application({
    name: 'Todo',
    extend: 'Todo.Application',
    autoCreateViewport: true
});

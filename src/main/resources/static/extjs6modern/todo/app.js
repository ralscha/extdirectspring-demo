Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.application({
	name: 'Todo',	
	requires: ['Todo.view.Main'],
	extend: 'Todo.Application',
	mainView: 'Todo.view.Main',
	lanuch: function() {
		Ext.fly('appLoadingIndicator').destroy();
	}
});

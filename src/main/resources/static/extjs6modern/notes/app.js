Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.application({
	name: 'Notes',	
	requires: ['Notes.view.Main'],
	extend: 'Notes.Application',
	mainView: 'Notes.view.Main',
	lanuch: function() {
		Ext.fly('appLoadingIndicator').destroy();
	}
});

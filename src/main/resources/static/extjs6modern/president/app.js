Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.application({
	name: 'Sencha',
	extend: 'Sencha.Application',
	requires: [ 'Sencha.view.Main' ],
	mainView: 'Sencha.view.Main'
});
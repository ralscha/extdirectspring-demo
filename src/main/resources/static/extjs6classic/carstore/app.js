Ext.application({
	name: 'Carstore',
	extend: 'Ext.app.Application',
	autoCreateViewport: 'Carstore.view.CarListings',
	
	constructor: function() {
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
		this.callParent(arguments);
	}
		
});

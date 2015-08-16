Ext.application({
	requires: ['Ext.direct.*'],
	name: 'Feed',
	extend: 'Ext.app.Application',
	autoCreateViewport: true,

	constructor: function() {
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
		this.callParent(arguments);
	}
		
});

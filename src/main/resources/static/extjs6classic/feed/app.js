Ext.application({
	requires: ['Ext.direct.*', 'Feed.store.Feeds'],
	name: 'Feed',
	extend: 'Ext.app.Application',
	autoCreateViewport: true,
	stores:['Feed.store.Feeds'],
	constructor: function() {
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
		this.callParent(arguments);
	}
		
});

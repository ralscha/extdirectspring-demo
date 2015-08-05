Ext.define('Feed.store.Feeds', {
	extend: 'Ext.data.Store',
	storeId: 'Feeds',
	requires: ['Feed.model.Feed'],
	model: 'Feed.model.Feed',
	autoLoad: true,
	autoSync: true,
	pageSize: 0
});
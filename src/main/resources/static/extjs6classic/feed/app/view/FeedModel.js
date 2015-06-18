Ext.define('Feed.view.FeedModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Feed.model.Feed', 'Feed.model.FeedItem' ],

	data: {
		selectedFeed: false,
		selectedFeedItem: false
	},

	stores: {
		feedStore: {
			model: 'Feed.model.Feed',
			autoLoad: true,
			autoSync: true,
			pageSize: 0
		},
		feedItemStore: {
			model: 'Feed.model.FeedItem',
			autoLoad: false,
			pageSize: 0
		}
	}

});
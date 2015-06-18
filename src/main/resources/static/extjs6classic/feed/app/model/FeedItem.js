Ext.define('Feed.model.FeedItem', {
	extend: 'Ext.data.Model',

	fields: [ 'id', 'title', 'author', 'link', {
		name: 'pubDate',
		type: 'date',
		dateFormat: 'c'
	}, 'description', 'content' ],

	proxy: {
		type: 'direct',
		api: {
			read: 'feedService.readFeedItem'
		}
	}
});
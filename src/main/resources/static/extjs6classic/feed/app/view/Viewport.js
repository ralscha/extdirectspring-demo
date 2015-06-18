Ext.define('Feed.view.Viewport', {
	extend: 'Ext.container.Viewport',
	requires: [ 'Feed.view.FeedModel', 'Feed.view.FeedController', 'Feed.view.FeedPanel',
			'Feed.view.FeedTabPanel' ],

	controller: {
		xclass: 'Feed.view.FeedController'
	},

	viewModel: {
		xclass: 'Feed.view.FeedModel'
	},

	layout: {
		type: 'border',
		padding: 5
	},

	items: [ {
		xclass: 'Feed.view.FeedPanel',
		region: 'west',
		collapsible: true,
		width: 225,
		split: true,
		minWidth: 175
	}, {
		xclass: 'Feed.view.FeedTabPanel',
		region: 'center',
		minWidth: 300
	} ]

});
Ext.define('Feed.view.FeedWindow', {
	extend: 'Ext.window.Window',
	plain: true,
	resizable: false,
	modal: true,
	autoShow: true,
	width: 500,
	defaultFocus: 'combobox',
	title: 'Add Feed',
	iconCls: 'feed',
	layout: 'fit',
	buttons: [ {
		xtype: 'button',
		text: 'Add Feed',
		handler: 'addFeed'
	}, {
		xtype: 'button',
		text: 'Cancel',
		handler: 'cancelFeedWindow'
	} ],

	items: [ {
		xype: 'form',
		reference: 'newFeedForm',
		bodyPadding: '12 10 10',
		border: false,
		items: [ {
			width: 450,
			fieldLabel: 'Enter the URL of the feed to add',
			labelAlign: 'top',
			msgTarget: 'under',
			xtype: 'combobox',
			bind: '{newFeedUrl}',
			store: [
					[ 'http://rss.cnn.com/rss/edition.rss', 'CNN Top Stories' ],
					[ 'http://sports.espn.go.com/espn/rss/news', 'ESPN Top Headlines' ],
					[ 'https://news.google.com/news/feeds?ned=us&topic=t&output=rss',
							'Sci/Tech - Google News' ],
					[ 'http://news.yahoo.com/rss/', 'Yahoo News' ] ]
		} ]
	} ]

});
Ext.define('Feed.view.FeedController', {
	extend: 'Ext.app.ViewController',
	requires: [ 'Feed.view.FeedWindow', 'Feed.view.FeedItems', 'Feed.view.FeedItem' ],
	
	feedSelected: function(view, record) {
		this.getViewModel().set('selectedFeed', record);
		this.getViewModel().set('selectedFeedItem', false);
		this.getStore('feedItemStore').load({
			params: {
				feedUrl: record.data.url
			}
		});

		var tabPanel = this.lookupReference('feedTabPanel');
		var active = tabPanel.items.first();
		if (!active) {
			active = tabPanel.add({
				xclass: 'Feed.view.FeedItems',
				closable: false,
				listeners: {
					openTab: 'openTab'
				}
			});
		}

		tabPanel.setActiveTab(active);
	},

	openFeedWindow: function() {
		new Feed.view.FeedWindow();
	},

	addFeed: function(btn) {
		var newFeedUrl = this.getViewModel().get('newFeedUrl');
		var feedForm = this.lookupReference('newFeedForm');

		feedForm.setLoading({
			msg: 'Validating feed...'
		});

		feedService.verifyUrl(newFeedUrl, function(valid) {
			feedForm.setLoading(false);

			if (valid) {
				this.getStore('feedStore').reload();
				btn.up('window').close();
				this.getViewModel().set('newFeedUrl', null);
			}
			else {
				feedForm.down('combobox').markInvalid(
						'The URL specified is not a valid RSS/ATOM feed.');
			}
		}, this);
	},

	removeFeed: function() {
		this.getStore('feedStore').remove(this.getViewModel().get('selectedFeed'));
		this.getViewModel().set('selectedFeed', false);
		this.getViewModel().set('selectedFeedItem', false);
	},

	cancelFeedWindow: function(btn) {
		btn.up('window').close();
	},

	openTab: function(items) {
		var tabs = [];
		var tabPanel = this.lookupReference('feedTabPanel');

		if (items.length === 1) {
			var index = this.getTabByTitle(tabPanel, items[0].data.title.trunc(30));
			if (index) {
				tabPanel.setActiveTab(index);
			}
		}

		Ext.each(items, function(item) {
			var shortTitle = item.data.title.trunc(30);
			if (!this.getTabByTitle(tabPanel, shortTitle)) {
				tabs.push({
					xclass: 'Feed.view.FeedItem',
					closable: true,
					viewModel: {
						data: {
							selectedFeedItem: item,
							title: item.data.title,
							abbreviatedTitle: shortTitle
						}
					},
					tabConfig: {
						tooltip: item.data.title
					}
				});
			}
		}, this);

		if (tabs.length > 0) {
			tabPanel.add(tabs);
		}

	},

	getTabByTitle: function(tabPanel, title) {
		var index = tabPanel.items.findIndex('title', title);
		return (index < 0) ? null : tabPanel.items.getAt(index);
	}

});
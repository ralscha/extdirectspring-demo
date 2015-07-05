Ext.define('Feed.view.FeedItemsController', {
	extend: 'Ext.app.ViewController',
	requires: [ 'Feed.view.FeedItem' ],

	feedItemSelected: function(view, record) {
		this.getViewModel().set('selectedFeedItem', record);
	},

	feedItemDblClick: function(view, record) {
		this.fireViewEvent('openTab', [ record ]);
	},

	onOpenAll: function() {
		this.fireViewEvent('openTab', this.getStore('feedItemStore').getRange(0, 9));
	},

	viewInNewTab: function() {
		this.fireViewEvent('openTab', [ this.getViewModel().get('selectedFeedItem') ]);
	},

	onSummaryToggle: function(btn, flag) {
		this.getView().down('grid').getView().getPlugin('preview').toggleExpanded(flag);
	},

	titleRenderer: function(value, p, record) {
		return Ext.String.format(
				'<div class="topic"><b>{0}</b><span class="author">{1}</span></div>',
				value, record.get('author') || "Unknown");
	},

	readingPaneChange: function(cycle, item) {
		var feedItemPanel = this.lookupReference('feedItemPanel');
		switch (item.text) {
		case 'Bottom':
			feedItemPanel.show();
			feedItemPanel.setRegion('south');
			break;
		case 'Right':
			feedItemPanel.show();
			feedItemPanel.setRegion('east');
			break;
		default:
			feedItemPanel.hide();
			break;
		}
	},

	dateRenderer: function(date) {
		if (!date) {
			return '';
		}

		var now = new Date(), d = Ext.Date.clearTime(now, true), notime = Ext.Date
				.clearTime(date, true).getTime();

		if (notime === d.getTime()) {
			return 'Today ' + Ext.Date.format(date, 'g:i a');
		}

		d = Ext.Date.add(d, 'd', -6);
		if (d.getTime() <= notime) {
			return Ext.Date.format(date, 'D g:i a');
		}
		return Ext.Date.format(date, 'Y/m/d g:i a');
	}

});
Ext.define('Feed.view.FeedPanel', {
	extend: 'Ext.panel.Panel',

	animCollapse: true,
	layout: 'fit',
	title: 'Feeds',

	items: [ {
		xtype: 'dataview',
		autoScroll: true,
		bind: {
			store: '{feedStore}'
		},
		trackOver: true,
		cls: 'feed-list',
		itemSelector: '.feed-list-item',
		overItemCls: 'feed-list-item-hover',
		tpl: '<tpl for="."><div class="feed-list-item">{title}</div></tpl>',
		listeners: {
			select: 'feedSelected'
		}
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: 'Add',
			iconCls: 'feed-add',
			handler: 'openFeedWindow'
		}, {
			text: 'Remove',
			iconCls: 'feed-remove',
			handler: 'removeFeed',
			bind: {
				disabled: '{!selectedFeed}'
			}
		} ]
	} ]

});

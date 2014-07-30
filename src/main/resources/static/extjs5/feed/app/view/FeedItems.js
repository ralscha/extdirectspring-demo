Ext.define('Feed.view.FeedItems', {
	extend: 'Ext.panel.Panel',
	requires: [ 'Feed.view.FeedItem', 'Feed.view.FeedItemsController' ],

	controller: {
		xclass: 'Feed.view.FeedItemsController'
	},

	border: false,

	layout: 'border',
	bind: {
		title: '{selectedFeed.title}'
	},
	items: [ {
		xtype: 'grid',
		cls: 'feed-grid',
		region: 'center',
		flex: 2,
		minHeight: 200,
		minWidth: 150,
		bind: '{feedItemStore}',
		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				iconCls: 'open-all',
				text: 'Open First 10',
				handler: 'onOpenAll'
			}, '-', {
				xtype: 'cycle',
				text: 'Reading Pane',
				prependText: 'Preview: ',
				showText: true,
				listeners: {
					change: 'readingPaneChange'
				},
				// changeHandler: 'readingPaneChange',
				menu: {
					id: 'reading-menu',
					items: [ {
						text: 'Bottom',
						checked: true,
						iconCls: 'preview-bottom'
					}, {
						text: 'Right',
						iconCls: 'preview-right'
					}, {
						text: 'Hide',
						iconCls: 'preview-hide'
					} ]
				}
			}, {
				iconCls: 'summary',
				text: 'Summary',
				enableToggle: true,
				pressed: true,
				toggleHandler: 'onSummaryToggle'
			} ]
		} ],
		columns: [ {
			text: 'Title',
			dataIndex: 'title',
			flex: 1,
			renderer: 'titleRenderer'
		}, {
			text: 'Author',
			dataIndex: 'author',
			hidden: true,
			width: 200

		}, {
			text: 'Date',
			dataIndex: 'pubDate',
			renderer: 'dateRenderer',
			width: 200
		} ],

		viewConfig: {
			plugins: [ {
				ptype: 'preview',
				bodyField: 'description',
				expanded: true,
				pluginId: 'preview',
			} ]
		},

		listeners: {
			select: 'feedItemSelected',
			itemdblclick: 'feedItemDblClick'
		}
	}, {
		xtype: 'panel',
		reference: 'feedItemPanel',
		layout: 'fit',
		region: 'south',
		border: false,
		split: true,
		flex: 2,
		minHeight: 150,
		items: [ {
			xclass: 'Feed.view.FeedItem'
		} ]
	} ]

});
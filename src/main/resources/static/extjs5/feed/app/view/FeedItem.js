Ext.define('Feed.view.FeedItem', {
	extend: 'Ext.panel.Panel',
	cls: 'preview',
	autoScroll: true,
	border: true,

	bind: {
		data: '{selectedFeedItem}',
		hidden: '{!selectedFeedItem}',
		title: '{abbreviatedTitle}'
	},

	tpl: [ '<div class="post-data">',
			'<span class="post-date">{pubDate:this.formatDate}</span>',
			'<h3 class="post-title">{title}</h3>',
			'<h4 class="post-author">by {author:this.defaultValue}</h4>', '</div>',
			'<div class="post-body">{content:this.getBody}</div>', {
				getBody: function(value, all) {
					return Ext.util.Format.stripScripts(value);
				},

				defaultValue: function(v) {
					return v ? v : 'Unknown';
				},

				formatDate: function(value) {
					if (!value) {
						return '';
					}
					return Ext.Date.format(value, 'M j, Y, g:i a');
				}
			} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: 'View in new tab',
			iconCls: 'tab-new',
			handler: 'viewInNewTab'
		}, {
			bind: {
				href: '{selectedFeedItem.link}'
			},
			hrefTarget: '_blank',
			text: 'Go to post',
			iconCls: 'post-go'
		} ]
	} ]

});
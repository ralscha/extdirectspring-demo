Ext.define('Feed.view.FeedTabPanel', {
	extend: 'Ext.tab.Panel',

	reference: 'feedTabPanel',
	bind: {
		hidden: '{!selectedFeed}'
	},

	maxTabWidth: 230,
	border: false,

	plugins: [ {
		ptype: 'tabclosemenu'
	} ]

});
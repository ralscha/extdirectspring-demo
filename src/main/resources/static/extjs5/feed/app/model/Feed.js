Ext.define('Feed.model.Feed', {
	extend: 'Ext.data.Model',

	fields: [ 'id', 'title', 'url' ],

	proxy: {
		type: 'direct',
		api: {
			create: 'feedService.create',
			read: 'feedService.read',
			destroy: 'feedService.destroy'
		}
	}
});
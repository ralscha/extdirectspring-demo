Ext.define('ExecDashboard.view.companynews.NewsModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.news',

	requires: [ 'ExecDashboard.model.News' ],

	formulas: {
		typeFilter: function(get) {
			var category = get('category');
			return this.filters[category];
		}
	},

	filters: {
		all: [ 'news', 'forum' ],
		news: [ 'news' ],
		forum: [ 'forum' ]
	},

	stores: {
		news: {
			model: 'ExecDashboard.model.News',
			autoLoad: true,
			remoteFilter: true,
			pageSize: 0,
			sorters: [ {
				property: 'date',
				direction: 'DESC'
			} ],
			filters: {
				property: 'type',
				value: '{typeFilter}'
			},
			proxy: {
				type: 'direct',
				directFn: execDashboardService.readNews
			}
		}
	}
});

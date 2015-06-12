Ext.define('Sencha.view.Main', {
	extend: 'Ext.navigation.View',
	requires: [ 'Sencha.view.MainController', 'Sencha.view.MainModel' ],

	controller: {
		xclass: 'Sencha.view.MainController'
	},

	viewModel: {
		xclass: 'Sencha.view.MainModel'
	},

	items: [ {
		xtype: 'list',
		title: 'American Presidents',
		grouped: true,
		itemTpl: '{firstName} <tpl if="middleInitial">{middleInitial}. </tpl>{lastName}',
		bind: {
			store: '{presidents}',
		},
		onItemDisclosure: true,
		listeners: {
			disclose: 'onItemDisclose'
		}
	} ]

});
Ext.define('Sencha.view.MainController', {
	extend: 'Ext.app.ViewController',

	onItemDisclose: function(list, record) {

		this.getView().push({
			xtype: 'panel',
			title: record.fullName(),
			styleHtmlContent: true,
			scrollable: 'vertical',
			tpl: [ '<p>Hello {firstName}!</p>', '<img src="http://www.whitehouse.gov/sites/default/files/first-family/masthead_image/{imageUrl}" />' ],
			data: record.getData()
		});

	}

});
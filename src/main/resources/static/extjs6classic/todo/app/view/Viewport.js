Ext.define('Todo.view.Viewport', {
	extend: 'Ext.container.Viewport',
	requires: [ 'Ext.tab.Panel', 'Ext.layout.container.Border' ],

	layout: {
		type: 'border'
	},

	items: [ {
		region: 'center',
		xtype: 'tabpanel',
		items: [ {
			xclass: 'Todo.view.grid.GridActions'
		}, {
			xclass: 'Todo.view.MethodCall'
		}, {
			xclass: 'Todo.view.FormActions'
		}, {
			xclass: 'Todo.view.FormUpload'
		}, {
			xclass: 'Todo.view.TreeActions'
		}, {
			xclass: 'Todo.view.Cookies'
		} ]
	} ]
});
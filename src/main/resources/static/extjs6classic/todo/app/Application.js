Ext.define('Todo.Application', {
	name: 'Todo',
	extend: 'Ext.app.Application',

	views: [ 'MethodCall', 'FormActions', 'grid.GridActions', 'FormUpload', 'TreeActions',
			'Cookies' ],
	models: [ 'TodoItem' ],
	stores: [ 'Tree' ]
});
Ext.define('Task.Application', {
	name: 'Task',
	extend: 'Ext.app.Application',

	views: [ 'TaskForm', ],
	models: [ 'Task' ]
});

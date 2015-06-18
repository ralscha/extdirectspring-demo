Ext.define('Todo.view.MethodCall', {
	extend: 'Ext.form.Panel',

	title: 'Direct method calls, exceptions',
	tplWriteMode: 'append',
	tpl: '{data}<br>',
	autoScroll: true,
	bodyPadding: 5,

	dockedItems: [ {
		xtype: 'toolbar',
		docked: 'top',
		items: [ {
			xtype: 'button',
			text: 'Test call, one parameter',
			handler: function(bt) {
				var me = bt.up('panel');
				todoServiceExt.testMe({
					test: true
				}, function(result, event) {
					// you can grab useful info from event
					var transaction = event.getTransaction(), status = event.status;
					console.log(transaction);
					me.updateContent('Status     : ' + status);
					me.updateContent('Result     : ' + result);
					me.updateContent('<hr>');
				});
			}
		}, {
			xtype: 'button',
			text: 'Test call, empty parameter object',
			handler: function(bt) {
				var me = bt.up('panel');
				todoServiceExt.testMe({}, function(result, event) {
					console.log(event.getTransaction());
					me.updateContent('Status     : ' + event.status);
					me.updateContent('Result     : ' + result);
					me.updateContent('<hr>');
				});
			}
		}, {
			xtype: 'button',
			text: 'Read table page',
			handler: function(bt) {
				var me = bt.up('panel');
				todoService.read({
					page: 1,
					start: 0,
					limit: 10
				}, function(result, event) {
					me.updateContent(Ext.encode(result));
					me.updateContent('<hr>');
				});
			}
		}, {
			xtype: 'button',
			text: 'Exception',
			handler: function(bt) {
				var me = bt.up('panel');
				todoServiceExt.testException(function(result, event) {
					me.updateContent('Status   : ' + event.status);
					me.updateContent('Event    : ' + Ext.encode(event));
					me.updateContent('<hr>');
				});
			}
		} ]
	} ],

	updateContent: function(content) {
		var me = this;
		me.update({
			data: content
		});
		me.body.scroll('b', 100000, true);
	}

});
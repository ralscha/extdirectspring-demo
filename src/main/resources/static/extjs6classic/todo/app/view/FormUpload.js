Ext.define('Todo.view.FormUpload', {
	extend: 'Ext.form.Panel',

	title: 'Form File Upload',

	bodyPadding: 5,

	api: {
		submit: 'todoServiceExt.upload'
	},

	paramsAsHash: true,

	items: [ {
		xtype: 'textfield',
		fieldLabel: 'Description',
		name: 'description'
	}, {
		xtype: 'filefield',
		name: 'afile',
		fieldLabel: 'File',
		labelWidth: 50,
		msgTarget: 'side',
		allowBlank: false,
		anchor: '40%',
		buttonText: 'Select File...'
	} ],

	tbar: [ {
		text: 'Upload..',
		handler: function(btn) {
			btn.up('form').getForm().submit({
				waitMsg: 'Uploading your file...',
	
				success: function(fp, o) {
					Ext.Msg.alert('Success', 'Your file "' + o.result.filename
							+ '" has been uploaded.<br>File size:'
							+ o.result.size + ' bytes.'
							+ '<br>Description:' + o.result.description);
				},
	
				failure: function(form, action) {
					console.log(arguments);
					Ext.MessageBox.show({
						title: 'EXCEPTION',
						msg: 'Error uploading file',
						icon: Ext.MessageBox.ERROR,
						buttons: Ext.Msg.OK
					});
				}
			});
		}
	} ]
});

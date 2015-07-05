Ext.define('Todo.view.Cookies', {
    extend: 'Ext.form.Panel',
    title: 'Cookies',

    border: false,
    bodyPadding: 5,
    
    dockedItems: [{
        xtype: 'toolbar',
        docked: 'top',
        items: [{
            xtype: 'button',
            formBind: true,
            text: 'Authentificate using credentials below',
            handler: function(bt) {
                todoServiceExt.login(bt.up('form').getValues(),
                    function(result, event) {
                        console.log(event);
                        console.log(this);
                        Ext.Msg.alert('Response', Ext.encode(result) + '<br>' + 'Cookie \'login\'=' + readCookie('login'));
                    }
                );
            },
            scope: this
        }]
    }],
    
    defaults: {
        xtype: 'textfield',
        msgTarget: 'side',
        allowBlank: false
    },
    items: [{
        name: 'username',
        fieldLabel: 'Username'
    }, {
        name: 'password',
        inputType: 'password',
        fieldLabel: 'Password'
    }, {
        xtype: 'component',
        margin: '20 0 0 0',
        html: 'Note: Please enter username and password before submitting the form<br>Successful login with username <b>admin</b> and password <b>admin</b>'
    }]
});
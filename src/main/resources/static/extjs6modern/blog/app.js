Ext.require(['Ext.direct.Manager','Ext.direct.RemotingProvider', 'Ext.data.proxy.Direct'], function() {
	Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
});

Ext.application({
    name: 'Sencha',
    requires: ['Ext.tab.Panel','Ext.dataview.NestedList','Ext.TitleBar',
               'Ext.data.proxy.JsonP','Ext.form.Panel','Ext.form.FieldSet', 
               'Ext.field.Email'],
    
    launch: function() {
        //The whole app UI lives in this tab panel
        Ext.Viewport.add({
            xtype: 'tabpanel',
            fullscreen: true,
            tabBarPosition: 'bottom',

            items: [
                // This is the home page, just some simple html
                {
                    title: 'Home',
                    iconCls: 'x-fa fa-home',
                    cls: 'home',
                    scrollable: true,
                    html: [
                        '<img height="291" src="https://demo.rasc.ch/resources/ext-6.0.1.250/examples/kitchensink/modern/resources/images/sencha.png" />',
                        '<h1>Welcome to Sencha Touch</h1>',
                        "<p>Building the Getting Started app</p>",
                        '<h2>Ext JS 6 Modern</h2>'
                    ].join("")
                },

                // This is the recent blogs page. It uses a tree store to load its data from blog.json
                {
                    xtype: 'nestedlist',
                    title: 'Blog',
                    iconCls: 'x-fa fa-star',
                    cls: 'blog',
                    displayField: 'title',

                    store: {
                        type: 'tree',

                        fields: ['title', 'link', 'author', 'contentSnippet', 'content', {
                            name: 'leaf',
                            defaultValue: true
                        }],

                        root: {
                            leaf: false
                        },

                        proxy: {
                            type: 'direct',
                            directFn: blogService.getBlogPosts                            
                        }
                    },

                    detailCard: {
                        xtype: 'panel',
                        scrollable: true,
                        styleHtmlContent: true
                    },

                    listeners: {
                        itemtap: function(nestedList, list, index, element, post) {
                            this.getDetailCard().setHtml(post.get('content'));
                        }
                    }
                },

                // This is the contact page, which features a form and a button. The button submits the form
                {
                    xtype: 'formpanel',
                    title: 'Contact Us',
                    iconCls: 'x-fa fa-user',
                    url: '../../postContact',
                    layout: 'vbox',

                    items: [
                        {
                            xtype: 'fieldset',
                            title: 'Contact Us',
                            instructions: 'Email address is optional',

                            items: [
                                {
                                    xtype: 'textfield',
                                    label: 'Name',
                                    name: 'name'
                                },
                                {
                                    xtype: 'emailfield',
                                    label: 'Email',
                                    name: 'email'
                                },
                                {
                                    xtype: 'textareafield',
                                    label: 'Message',
                                    name: 'message',
                                    height: 90
                                }
                            ]
                        },
                        {
                            xtype: 'button',
                            text: 'Send',
                            ui: 'confirm',

                            // The handler is called when the button is tapped
                            handler: function() {

                                // This looks up the items stack above, getting a reference to the first form it see
                                var form = this.up('formpanel');

                                // Sends an AJAX request with the form data to the url specified above (contact.php).
                                // The success callback is called if we get a non-error response from the server
                                form.submit({
                                    success: function() {
                                        // The callback function is run when the user taps the 'ok' button
                                        Ext.Msg.alert('Thank You', 'Your message has been received', function() {
                                            form.reset();
                                        });
                                    }
                                });
                            }
                        }
                    ]
                }
            ]
        });
    }
});

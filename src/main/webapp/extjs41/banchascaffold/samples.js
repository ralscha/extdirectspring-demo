/*!
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2012 Roland Schuetz
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold.samples
 * @copyright     Copyright 2011-2012 Roland Schuetz
 * @link          http://scaffold.banchaproject.org
 * @since         Bancha.scaffold 0.5.1
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha v PRECOMPILER_ADD_RELEASE_VERSION
 *
 * For more information go to http://scaffold.banchaproject.org
 */
/*jslint browser: true, vars: true, undef: true, nomen: true, eqeq: false, plusplus: true, bitwise: true, regexp: true, newcap: true, sloppy: true, white: true */
/*jshint bitwise:true, curly:true, eqeqeq:true, forin:true, immed:true, latedef:true, newcap:true, noarg:true, noempty:true, regexp:true, undef:true, trailing:false */
/*global Ext, Bancha */


// wait for DOM to be ready
Ext.onReady(function() {

    // ... create a full featured users grid
    Ext.create('Ext.grid.Panel', {
        title: 'Easily scaffold full CRUD support, based on the models proxy',

        // define the model
        scaffold: 'Bancha.model.User',
        
        // some additional styles
        height: 350,
        width: 650,
        frame: true,
        renderTo: 'gridpanel'
    });

    // ... a simple example how you can extend it with regular code
    Ext.create('Ext.grid.Panel', {
        title: 'Starting from the scaffolded code you can easily add your own code',
        
        // use scaffolding
        scaffold: {

            // define the model
            target: 'Bancha.model.Article',

            // we don't need an editable grid for this example
            editable: false,
            deletable: false, // records won't be deletable
            buttons: false, // there will be toolbar

            // use a different store in every example
            oneStorePerModel: false,

            // configure paging
            storeDefaults: {
                autoLoad: true,
                pageSize: 10,
                remoteSort: true
            },

            // add a paging bar
            afterBuild: function(config) {
                // paging bar on the bottom
                config.bbar = Ext.create('Ext.PagingToolbar', {
                    store: config.store,
                    displayInfo: true,
                    displayMsg: 'Displaying entry {0} - {1} of {2}',
                    emptyMsg: 'No entires to display'
                });
                return config;
            } //eo afterBuild
        },
        
        // add some styling
        height: 300,
        width: 650,
        frame: true,
        renderTo: 'paginated-gridpanel'
    });



    // and a form panel
    Ext.create('Ext.form.Panel', {
        title: 'Demonstration of a scaffolded Form',
        
        // use scaffolding
        scaffold: {

            // define the model
            target: 'Bancha.model.User',
            
            // simply add you own buttons
            buttons: ['->',{
                    text: 'Load Sample Record',
                    iconCls: 'icon-edit-user',
                    handler: function() {
                        var panel = this.getPanel(), // scopeButtonHandler enables this
                            form = this.getForm(); // scopeButtonHandler enables this
                        
                        // load the form
                        panel.load({
                            params: {
                                data: { data: { id:1 } }
                            }
                        });
                        
                        // change the header title
                        panel.setTitle('Demonstration of a scaffolded Form - Change Record 1');
                    },
                    scope: 'scaffold-scope-me'
                },'reset','save']
        }, // eo scaffoldConfig
        
        api: {
            load    : bancha.remoteStubs.banchaUserService.read,
            submit  : bancha.remoteStubs.banchaUserService.submit
        },
        
        // some additional styles
        width: 650,
        frame:true,
        renderTo: 'formpanel',
        id: 'form',
        bodyStyle:'padding:5px 5px 0',
        fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 75
        },
        defaultType: 'textfield',
        defaults: {
            anchor: '100%'
        }
    }); // eo form create


    // add a management panel
    Ext.create('Bancha.grid.ManagementPanel', {
        title: 'Bancha.grid.ManagementPanel autodetects all possible model capabilities',

        // define the models to scaffold
        models: [
            'Bancha.model.User',
            'Bancha.model.Book',
            'Bancha.model.Article'
            ],
        
        // some additional styles
        frame: true,
        bodyBorder: true,
        height: 350,
        width: 650,
        renderTo: 'managementpanel'
    });

});

// eof

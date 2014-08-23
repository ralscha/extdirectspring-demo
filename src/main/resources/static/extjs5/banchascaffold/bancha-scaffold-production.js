/*!
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 0.3.0
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

// This code below is a copy from the Bancha package!

// Fake missing classes for production
if(Ext.versions.extjs.major === 5) {
    Ext.define('Ext.data.validations', {});
}

/**
 * @private
 * @class Bancha.data.override.Validations
 *
 * Ext JS 4 and Sencha Touch range and file 
 * validation rules.
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.data.override.Validations', {
    requires: ['Ext.data.validations'],
    alternateClassName: [
        'Bancha.data.override.Validations' // Bancha.Scaffold uses the same class
    ]
}, function() {

    // helper function for Bancha.data.override.validations
    var filenameHasExtension = function(filename,validExtensions) {
        if(!filename) {
            return true; // no file defined (emtpy string or undefined)
        }
        if(!Ext.isDefined(validExtensions)) {
            return true;
        }
        var ext = filename.split('.').pop();
        return Ext.Array.contains(validExtensions,ext);
    };

    // Ext.Logger might not be available
    var markDeprecated = function(msg) {
        if(Ext.Logger && Ext.Logger.deprecate !== Ext.emptyFn) {
            Ext.Logger.deprecate(msg);
        } else if(Bancha.Logger) {
            Bancha.Logger.warn('[DEPRECATE]'+msg);
        } else if(Ext.global.console && Ext.global.console.warn) {
            Ext.global.console.warn('[DEPRECATE]'+msg);
        }
    };

    /**
     * @class Ext.data.validations
     *
     * For Sencha Touch and Ext JS 4:
     * Bancha extends Ext.data.validations with two new validation rules:
     * *range* and *file*.
     *
     * For Ext JS 5 see {@class Bancha.data.validator.File}
     * 
     * @author Roland Schuetz <mail@rolandschuetz.at>
     * @docauthor Roland Schuetz <mail@rolandschuetz.at>
     */
    Ext.apply(Ext.data.validations, { // this is differently called in Ext JS 4 and Sencha Touch, but work by alias just fine

        /**
         * @property
         * @deprecated In favor of the validation rule range.
         * 
         * The default error message used when a range validation fails.
         */
        numberformatMessage: 'is not a number or not in the allowed range',

        /**
         * @property
         * The default error message used when a range validation fails.
         */
        rangeMessage: 'is not a number or not in the allowed range',

        /**
         * @property
         * The default error message used when a file validation fails.
         */
        fileMessage: 'is not a valid file',

        /**
         * @method
         * @deprecated In favor of the validation rule range.
         * 
         * Validates that the number is in the range of min and max.
         * Precision is not validated, but it is used for differenting int from float,
         * also it's metadata for scaffolding.
         *
         * For example:
         *     {type: 'numberformat', field: 'euro', precision:2, min:0, max: 1000}
         */
        numberformat: function(config, value) {
            markDeprecated('Bancha: Validation rules "numberformat" is deprecated in favor of "range" for Ext JS 5 compatibility.');
            if(typeof value !== 'number') {
                value = (config.precision===0) ? parseInt(value, 10) : parseFloat(value);
                if(typeof value !== 'number') {
                    return false; // could not be converted to a number
                }
            }
            if((Ext.isDefined(config.min) && config.min > value) || (Ext.isDefined(config.max) && value > config.max)) {
                return false; // not in the range
            }
            return true;
        },

        /**
         * @method
         * Validates that the number is in the range of min and max.
         * Precision is not validated, but it is used for differenting int from float,
         * also it's metadata for scaffolding.
         *
         * For example:
         *     {type: 'range', field: 'euro', precision:2, min:0, max: 1000}
         */
        range: function(config, value) {
            if(typeof value !== 'number') {
                value = (config.precision===0) ? parseInt(value, 10) : parseFloat(value);
                if(typeof value !== 'number') {
                    return false; // could not be converted to a number
                }
            }
            if((Ext.isDefined(config.min) && config.min > value) || (Ext.isDefined(config.max) && value > config.max)) {
                return false; // not in the range
            }
            return true;
        },

        /**
         * @method
         * Validates that the given filename is of the configured extension. Also validates
         * if no extension are defined and empty values.
         *
         * For example:
         *     {type: 'file', field: 'avatar', extension:['jpg','jpeg','gif','png']}
         */
        file: function(config, value) {
            return filenameHasExtension(value,config.extension);
        }
    }); //eo apply
});
/*!
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 0.3.0
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

// This code below is a copy from the Bancha package!

// Fake missing classes for production
if(Ext.versions.extjs.major === 4) {
    Ext.define('Ext.data.validator.Validator', {});
}

/**
 * @class Bancha.data.validator.File
 * @extends Ext.data.validator.Validator
 *
 * For Ext JS 5:
 * Validates that the filename is one of given {@link #extension}.
 *
 * For Ext JS 4 see {@class Ext.data.validations}
 */
Ext.define('Bancha.scaffold.data.validator.File', {
    extend: 'Ext.data.validator.Validator',
    alias: 'data.validator.file',
    alternateClassName: [
        'Bancha.data.validator.File' // Bancha.Scaffold uses the same class
    ],
    
    type: 'file',
    
    config: {
        /**
         * @cfg {String} message
         * The error message to return when the value is not a valid email
         */
        message: 'is not a valid file',
        /**
         * @cfg {Array} extensions
         * The allowed filename extensions.
         */
        extensions: [],
        /**
         * @cfg {Array} extension
         * @deprecated Please use #extensions instead
         * Backwards compatibility with Bancha before 2.3
         */
        extension: false
    },

    /**
     * Validates that the given filename is of the configured extension. Also validates
     * if no extension are defined and empty values.
     * 
     * @param {Object} value The value
     * @param {Ext.data.Model} record The record
     * @return {Boolean/String} `true` if the value is valid. A string may be returned if the value 
     * is not valid, to indicate an error message. Any other non `true` value indicates the value
     * is not valid.
     */
    validate: function(filename, record) {
        var validExtensions = this.getExtension() || this.getExtensions();
        if(!filename) {
            return true; // no file defined (emtpy string or undefined)
        }
        if(!Ext.isDefined(validExtensions)) {
            return true;
        }
        var ext = filename.split('.').pop();
        return Ext.Array.contains(validExtensions,ext) || this.getMessage();
    }
});
/*!
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 0.3.0
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

// This code below is a copy from the Bancha package!

/**
 * @private
 * @class Bancha.scaffold.data.Validators
 *
 * This class adds additional validation rules used by Bancha.
 * 
 * For Ext JS 5 it adds a File validation class,
 * for Ext JS 4 and Sencha Touch it adds a range and 
 * file validation rule to Ext.data.validations.
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.data.Validators', {
    alternateClassName: [
        'Bancha.data.Validators' // Bancha.Scaffold uses the same class
    ]
}, function() {

    //<if ext>
    if(Ext.versions.extjs && Ext.versions.extjs.major === 5) {
        // Ext JS 5 doesn't have a validations class anymore, 
        // so use the range validator and add a file validator
        Ext.syncRequire('Bancha.scaffold.data.validator.File');

        /**
         * @private
         * @class Bancha.data.validator.override.Bound
         * 
         * Fixes issues with the current Range validator
         *
         * See http://www.sencha.com/forum/showthread.php?288168
         * 
         * @author Roland Schuetz <mail@rolandschuetz.at>
         * @docauthor Roland Schuetz <mail@rolandschuetz.at>
         */
        Ext.define('Bancha.data.validator.override.Bound', {
            override: 'Ext.data.validator.Bound',
            /**
             * @class Ext.data.validator.Bound
             *
             * To normalize the CakePHP, Ext JS 4 and Ext JS 5 validation
             * handling Bancha adds an additional check to the Ext JS 5
             * Bound validation rules.
             *
             * The effect is that non-numeric values are invalid using
             * the Range validation rule. For the error message an 
             * additional config is introduced.
             */
            config: {
                /**
                 * @cfg {String} nanMessage
                 * The error message to return when the value is not a number.
                 */
                nanMessage: 'Must be a number'
            },
            validate: function(value) {
                if(isNaN(this.getValue(value))) {
                    return this._nanMessage;
                }
                return this.callParent(arguments);
            },
            getValue: function(value) {
                return parseFloat(value);
            }
        });
        Ext.define('Ext.data.validator.override.Range', {
            override: 'Ext.data.validator.Range'
        }, function() {
            // for some reason setting via config doesn't work
            this.prototype.setNanMessage('Must be a number');
        });

    } else {
    //</if>
        Ext.syncRequire('Bancha.scaffold.data.override.Validations');
    //<if ext>
    }
    //</if>
});
/*
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 0.2.5
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

/**
 * @private
 * @class Bancha.scaffold.form.field.override.VTypes
 *
 * Add custom VTypes for scaffolding support.
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.form.field.override.VTypes', {
    requires: ['Ext.form.field.VTypes']
}, function() {

    // helper function from Bancha.data.override.validations
    var filenameHasExtension = function (filename, validExtensions) {
        if (!filename) {
            return true; // no file defined
        }
        if (!Ext.isDefined(validExtensions)) {
            return true;
        }
        var ext = filename.split('.').pop();
        return Ext.Array.contains(validExtensions, ext);
    };

    /**
     * @class Ext.form.field.VTypes
     *
     * Custom VTypes for file exensions.
     *
     * @author Roland Schuetz <mail@rolandschuetz.at>
     * @docauthor Roland Schuetz <mail@rolandschuetz.at>
     */
    Ext.apply(Ext.form.field.VTypes, {
        /**
         * @method
         * Validates that the file extension is of one of *field.validExtensions*.
         *
         * This also returns true if *field.validExtensions* is undefined or
         * if *val* is an empty string.
         *
         * @param {String} val The file name including file extension to validate
         * @param {Object} field The field to validate
         * @returns {Boolean} True if it matches the above described.
         */
        fileExtension: function (val, field) {
            return filenameHasExtension(val, field.validExtensions);
        },
        /**
         * @property
         * The error text to display when the file extension validation function returns false.
         *
         * Defaults: *'This file type is not allowed.'*
         */
        fileExtensionText: 'This file type is not allowed.',
        /**
         * @property
         * The keystroke filter mask to be applied on alpha input.
         *
         * Defaults: /[\^\r\n]/
         */
        fileExtensionMask: /[\^\r\n]/ // alow everything except new lines
    });
});
/*
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 1.0.0
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

/**
 * @singleton
 * @class Bancha.scaffold.form.Config
 * **This is only available inside Ext JS.**
 *
 * This class is a config class for creating scaffoled {@link Ext.form.Panel}s.
 *
 * For more information see {@link Ext.form.Panel}.
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.form.Config', {
    uses: [
        'Ext.window.MessageBox'
    ],

    /**
     * Create a new config instance
     */
    constructor: function(config) {
        config = config || {};
        var triggeredFrom = config.triggeredFrom || 'Unknown Origin',
            me = this,
            modelName;

        // if the config is just a model name or model, transform to a config object
        if (Ext.isString(config) || Bancha.scaffold.Util.isModel(config)) {
            config = {
                target: config // use the model as target
            };
        }

        

        // check that the configured model is valid
        modelName = config.target;
        modelName = Ext.isString(modelName) ? modelName : Ext.ClassManager.getName(modelName);

        // make sure it's loaded
        Ext.syncRequire(modelName);

        

        // make sure that the model property is always a model class
        config.target = Bancha.scaffold.Util.getModel(modelName);

        if(Ext.isDefined(config.exclude) && !Ext.isArray(config.exclude)) {
            
            config.exclude = [];
        }

        // apply to the object
        Ext.apply(me, config);
    },

    statics: {
        /**
         * Set a new default for scaffolding forms
         */
        setDefault: function(configName, config) {
            // apply to the class prototype
            this.prototype[configName] = config;
        },
        /**
         * Set some defaults for scaffolding forms
         */
        setDefaults: function(config) {
            // apply to the class prototype
            Ext.apply(this.prototype, config);
        }
    },

    /**
     * @private
     * @cfg {String} triggeredFrom
     * (optional) Used for debugging messages as target
     */
    /**
     * @cfg {Ext.data.Model|String} target
     * The model to use
     */
    /**
     * @private
     * @property {Ext.data.Model} target
     * The model to use
     */
    /**
     * @cfg {String[]}
     * Exclude some model fields from scaffolding
     */
    exclude: [],
    /**
     * @cfg {String[]|false}
     * If this is set to an array, only those fields are displayed.
     * 
     * The given order of fields is also applied, so to reorder your fields 
     * simply defined something like _fields: ['field2','field1']_
     * 
     * Note that the exclude setting are still applied.
     */
    fields: false,
    /**
     * @cfg
     * This config is applied to each scaffolded form field
     */
    fieldDefaults: {},
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.form.field.Date
     */
    datefieldDefaults: {},
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.form.field.File
     */
    fileuploadfieldDefaults: {
        emptyText: 'Select an file'
    },
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.form.field.Text
     */
    textfieldDefaults: {},
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.form.field.Number
     */
    numberfieldDefaults: {},
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.form.field.Checkbox
     */
    checkboxfieldDefaults: {
        uncheckedValue: false
    },
    /**
     * @cfg
     * The defaults class to create a stores for associated model selects for
     * form scaffolding.
     *
     * See also {@link Bancha.scaffold.Util#getStore}.
     *
     * Default: *"Ext.data.Store"*
     */
    storeDefaultClass: 'Ext.data.Store',
    /**
     * @cfg
     * Defaults for all stores for associated model selects created with
     * this scaffolding.
     *
     * See also {@link Bancha.scaffold.Util#getStore}.
     *
     * Default:
     *
     *     {
     *       autoLoad: true
     *     }
     */
    storeDefaults: {
        autoLoad: true
    },
    /**
     * @cfg
     * True to use only one store per model (singleton),
     * false to create a new store each time a associated
     * model is found and a selects field with a store is
     * build.
     *
     * See also {@link Bancha.scaffold.Util#getStore}.
     *
     * Default: *true*
     */
    oneStorePerModel: true,
    /**
     * @cfg {Function} transformFieldConfig
     * Used to add some custom behaviour.
     *
     * This function can be overwritten by any custom function.
     * @param {Object} fieldConfig the field config to transform
     * @param {String} modelType A standard model field type like 'string'
     * (also supports 'file' for compability with http://bancha.io)
     * @return {Object} Returns an Ext.form.field.* configuration object
     */
    transformFieldConfig: function (fieldConfig, modelType) {
        return fieldConfig;
    },
    /**
     * @cfg
     * Editable function to be called when the save button is pressed.
     * To change the default scaffolding behaviour just replace this function.
     *
     * The default scope provides two functions:
     *
     *  - this.getPanel() to get the form panel
     *  - this.getForm() to get the basic form
     */
    onSave: function () {
        var me = this,
            form = me.getForm(),
            config = me.getPanel().scaffold;

        form.submit({
            waitMsg: form.hasUpload() ? 'Uploading files...' : 'Saving data..',
            success: function (form, action) {
                Ext.MessageBox.alert('Success', action.result.msg || 'Successfully saved data.');

                // load result as record into form
                var rec = Ext.create(config.target.getName(), action.result.data);
                form.loadRecord(rec);

                if(config.onSaved) {
                    config.onSaved.call(me, me, true, action);
                }
            },
            failure: function (form, action) {
                if(action.result.msg || !action.result.errors) {
                    // if there is a server-side message or no server-side validation errors are send,
                    // show a warning alert. Otherwise the errors are rendered in the form and no alert
                    // is necessary.
                    Ext.MessageBox.alert('Failed', action.result.msg || 'Could not save data, unknown error.');
                }

                if(config.onSaved) {
                    config.onSaved.call(me, me, false, action);
                }
            }
        });
    },
    /**
     * @cfg
     * Editable function to be called when the record was saved to the server.
     * To change the default scaffolding behaviour just replace this function.
     *
     * The arguments are:
     * 
     *  - this: The scope described below
     *  - success: True if successfully saved
     *  - action: The request action
     *  
     * The default scope provides two functions:
     *
     *  - this.getPanel() to get the form panel
     *  - this.getForm() to get the basic form
     *
     * Note: 
     * This function is called by onSave, so if you override onSave this function
     * might note be called anymore.
     */
    onSaved: Ext.emptyFn,
    /**
     * @cfg
     * Editable function to be called when the reset button is pressed.
     * To change the default scaffolding behaviour just replace this function.
     *
     * The default scope provides two functions:
     *
     *  - this.getPanel() to get the form panel
     *  - this.getForm() to get the basic form
     */
    onReset: function () {
        this.getForm().reset();
    },
    /**
     * @cfg {String[]|Ext.button.Button[]}
     * If an array of elements, a footer toolbar is rendered.
     *
     * 'reset' and 'save' will be replaced by scaffolded
     * buttons, other elements are treated like default Ext JS items.
     *
     * Inside your own buttons you can set the scope property to
     * 'scaffold-scope-me', this scope provides two functions:
     *  - this.getPanel() to get the form panel
     *  - this.getForm() to get the basic form
     *
     * Default: ['reset','save']
     */
    buttons: ['reset','save'],
    /**
     * @cfg
     * Default save button config, used in buttons.
     * If not defined scope and handler properties will be set by
     * the build function.
     */
    saveButtonConfig: {
        iconCls: 'icon-save',
        text: 'Save',
        formBind: true
    },
    /**
     * @cfg
     * Default reset button config, used in buttons config.
     * If not defined scope and handler properties will be set by
     * the build function.
     */
    resetButtonConfig: {
        iconCls: 'icon-reset',
        text: 'Reset'
    },
    /**
     * @cfg {Function}
     * Build the form api config, used only by buildConfig()
     * just for separation of concern, since this is the only
     * part which deals with proxies
     *
     * For Bancha projects with an CakePHP backend this function will
     * scaffold everything
     *
     * Otherwise it will use the initial config, if provided. If not,
     * it will use the model proxies load method. There is no generic
     * way to guess the submit method, please either modify this function
     * or provide a propery form api config.
     *
     * @param {Ext.data.Model} model the model used for scaffolding
     * @param {Object} initialApi the initial Ext.form.Panel api config
     * @return the final Ext.form.Panel api config
     */
    buildApiConfig: function (model, initialApi) {

        if(Bancha.getModel) {
            // the user is using the full Bancha stack
            // everything can be automatically done
            return Ext.form.Panel.buildBanchaApiConfig(model,initialApi);
        } else{

            if(initialApi) {
                // just use the configured api
                return initialApi;
            }

            
        }

        // try to find the proxy configuration for load
        var proxy = model.getProxy(),
            load = proxy && proxy.api && proxy.api.read ? proxy.api.read :
                    (proxy && proxy.directFn ? proxy.directFn : undefined);

        return load ? {load: load} : undefined;
    },
    /**
     * @cfg {String|Number|False}
     * Define a record id here to autolaod this record for editing in this form, or choose
     * false to create a new record onSave. You can also overwrite onSave to define your
     * own behavior
     * (Default: false)
     */
    loadRecord: false,
    /**
     * @cfg {Function}
     * The function will be executed before scaffolding as interceptor.
     * @param {Ext.data.Model} model the model used for scaffolding
     * @param {Bancha.scaffold.form.Config} config the scaffold full config for this call
     * @param {Object} initialPanelConfig please ignore, this is a legacy argument, may having some additional Ext.form.Panel configs
     * @return {Object|undefined} object with initial Ext.form.Panel configs
     */
    beforeBuild: function (model, config, initialPanelConfig) {},
    /**
     * @cfg {Function}
     * This function will be executed after scaffolding as interceptor.
     * @param {Object} formConfig the just build form panel config
     * @param {Ext.data.Model} model the model used for scaffolding
     * @param {Bancha.scaffold.form.Config} config the scaffold full config for this call
     * @param {Object} initialPanelConfig please ignore, this is a legacy argument, may having some additional Ext.form.Panel configs
     * @return {Object|undefined} object with final Ext.form.Panel configs or undefined to use the passed config
     */
    afterBuild: function (formConfig, model, config, initialPanelConfig) {}
});
/*
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 1.0.0
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

/**
 * @singleton
 * @class Bancha.scaffold.grid.Config
 * **This is only available inside Ext JS.**
 *
 * This class is a config class for creating scaffoled {@link Ext.grid.Panel}s.
 *
 * For more information see {@link Ext.grid.Panel}.
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.grid.Config', {
    uses: [
        'Ext.window.MessageBox'
    ],

    /**
     * Create a new config instance
     */
    constructor: function(config) {
        config = config || {};
        var triggeredFrom = config.triggeredFrom || 'Unknown Origin',
            modelName;

        // if the config is just a model or model name, transform to a config object
        if (Ext.isString(config) || Bancha.scaffold.Util.isModel(config)) {
            config = {
                target: config // this is a valid model
            };
        }

        

        // check that the configured model is valid
        modelName = config.target;
        modelName = Ext.isString(modelName) ? modelName : Ext.ClassManager.getName(modelName);

        // make sure it's loaded
        Ext.syncRequire(modelName);

        

        // make sure that the model property is always a model class
        config.target = Bancha.scaffold.Util.getModel(modelName);

        // now build the form config

        // normally we would use config.isInstance here, but that was introduced in Ext JS 4.1
        if (!Ext.isObject(config.formConfig) || !config.formConfig.$className) {
            config.formConfig = config.formConfig || this.formConfig || {}; // instance config, or default, or {}
            config.formConfig.triggeredFrom = config.triggeredFrom;
            // take the model the grid config
            config.formConfig = Ext.apply(config.formConfig, {
                target: config.target
            });
            // build the instance
            config.formConfig = Ext.create('Bancha.scaffold.form.Config', config.formConfig);
        }

        // apply to the object
        Ext.apply(this, config);
    },

    statics: {
        /**
         * Set a new default for scaffolding forms
         */
        setDefault: function(configName, config) {
            // apply to the class prototype
            this.prototype[configName] = config;
        },
        /**
         * Set some defaults for scaffolding forms
         */
        setDefaults: function(config) {
            // apply to the class prototype
            Ext.apply(this.prototype, config);
        }
    },

    /**
     * @private
     * @cfg {String} triggeredFrom
     * (optional) Used for debugging messages as target
     */
    /**
     * @cfg {Ext.data.Model|String} target
     * The model to use
     */
    /**
     * @private
     * @property {Ext.data.Model} target
     * The model to use
     */
    /**
     * @cfg {String[]}
     * Exclude some model fields from scaffolding
     */
    exclude: [],
    /**
     * @cfg {String[]|false}
     * If this is set to an array, only those fields are displayed.
     *
     * The given order of fields is also applied, so to reorder your fields 
     * simply defined something like _fields: ['field2','field1']_
     *
     * Note that the exclude setting are still applied.
     */
    fields: false,
    /**
     * @cfg
     * This config is applied to each scaffolded column config
     */
    columnDefaults: {
        flex: 1 // foreFit the columns to take the whole available space
    },
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.grid.column.Grid
     */
    gridcolumnDefaults: {},
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.grid.column.Number
     */
    numbercolumnDefaults: {},
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.grid.column.Boolean
     */
    booleancolumnDefaults: {},
    /**
     * @cfg
     * This config is applied to each scaffolded Ext.grid.column.Column
     */
    datecolumnDefaults: {},
    /**
     * @private
     * @property {Bancha.scaffold.form.Config} formConfig
     * If the editable property was true while building, these configurations
     * were applied for building the editor fields.
     */
    /**
     * @cfg {Bancha.scaffold.form.Config|Object}
     * If the editable property is true, these configurations will be applied
     * for building the editor fields.
     *
     * See {@link Bancha.scaffold.form.Config}
     * for all configuration options
     */
    formConfig: {},
    /**
     * @cfg
     * The defaults class to create an store for grid scaffolding.
     *
     * See also {@link Bancha.scaffold.Util#getStore}.
     *
     * Default: *"Ext.data.Store"*
     */
    storeDefaultClass: 'Ext.data.Store',
    /**
     * @cfg
     * Defaults for all grid stores created with this scaffolding.
     *
     * See also {@link Bancha.scaffold.Util#getStore}.
     *
     * Default:
     *
     *     {
     *       autoLoad: true
     *     }
     */
    storeDefaults: {
        autoLoad: true
    },
    /**
     * @cfg
     * True to use only one store per model (singleton),
     * false to create a new store each time.
     *
     * See also {@link Bancha.scaffold.Util#getStore}.
     *
     * Default: *true*
     */
    oneStorePerModel: true,
    /**
     * @cfg {Function} transformColumnConfig
     * Used to add some custom behaviour.
     *
     * This function can be overwritten by any custom function.
     * @param {Object} columnConfig the column config to transform
     * @param {String} modelType A standard model field type like 'string'
     * (also supports 'file' for compability with http://bancha.io)
     * @return {Object} Returns an Ext.grid.column.* configuration object
     */
    transformColumnConfig: function (columnConfig, modelType) {
        return columnConfig;
    },
    /**
     * @cfg
     * Editable function to be called when the create button is pressed.
     * To change the default scaffolding behaviour just replace this function.
     *
     * The scope provides two functions:
     *
     *  - this.getStore() to get the grids store
     *  - this.getCellEditing() to get the grids cell editing plugin
     */
    onCreate: function () {
        var edit = this.getCellEditing(),
            grid = edit.grid,
            store = this.getStore(),
            model = store.getProxy().getModel(),
            rec, visibleColumn = false;

        // Cancel any active editing.
        edit.cancelEdit();

        // create new entry
        rec = Ext.create(Ext.ClassManager.getName(model), {});

        // add entry
        store.insert(0, rec);

        // find first visible column
        Ext.each(grid.columns, function (el, i) {
            if (el.hidden !== true) {
                visibleColumn = i;
                return false;
            }
        });

        // start editing
        if (visibleColumn) {
            edit.startEditByPosition({
                row: 0,
                column: visibleColumn
            });
        }
    },
    /**
     * @cfg
     * Editable function to be called when the save button is pressed.
     * To change the default scaffolding behaviour just replace this function.
     *
     * The scope provides two functions:
     *
     *  - this.getStore() to get the grids store
     *  - this.getCellEditing() to get the grids cell editing plugin
     */
    onSave: function () {
        var valid = true,
            msg = '',
            name,
            store = this.getStore();

        // check if all changes are valid
        store.each(function (el) {
            if (!el.isValid()) {
                valid = false;
                name = el.get('name') || el.get('title') || (el.phantom ? 'New entry' : el.getId());
                msg += '<br><br><b>' + name + ':</b>';
                el.validate().each(function (error) {
                    msg += '<br>&nbsp;&nbsp;&nbsp;' + error.field + ' ' + error.message;
                });
            }
        });

        if (!valid) {
            Ext.MessageBox.show({
                title: 'Invalid Data',
                msg: '<div style="text-align:left; padding-left:50px;">There are errors in your data:' + msg + '</div>',
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        } else {
            // commit create and update
            store.sync();
        }
    },
    /**
     * @cfg
     * Editable function to be called when the reset button is pressed.
     * To change the default scaffolding behaviour just replace this function.
     *
     * The scope provides two functions:
     *
     *  - this.getStore() to get the grids store
     *  - this.getCellEditing() to get the grids cell editing plugin
     */
    onReset: function () {
        // reject all changes
        var store = this.getStore();
        store.each(function (rec) {
            if (rec && rec.modified) {
                rec.reject();
            }
            if (rec && rec.phantom) {
                store.remove(rec);
            }
        });

        // TODO fix this really ugly thing
        // there is a strange bug going on, when you change all records,
        // and then create a new one and then hit reset, the original first
        // record becomes undefined and is not reset
        // That's why we iterate here two times and check for rec beeing truthy
        store.each(function (rec) {
            if (rec && rec.modified) {
                rec.reject();
            }
            if (rec && rec.phantom) {
                store.remove(rec);
            }
        });
    },
    /**
     * @cfg
     * Editable function to be called when the delete button is pressed.
     * To change the default scaffolding behaviour just replace this function.
     *
     * Scope can be defined in *destroyButtonConfig.items[0].scope*, but normally
     * you don't need a scope here, since the arguments already provide everything.
     */
    onDelete: function (grid, rowIndex, colIndex) {
        var store = grid.getStore(),
            rec = store.getAt(rowIndex),
            name = Ext.getClassName(rec),
            displayName = Bancha.scaffold.Util.humanizeClassName(name);

        // instantly remove vom ui
        store.remove(rec);

        // sync to server
        // for before-Ext JS 4.1 the callbacks will be ignored,
        // since they were added in 4.1
        store.sync({
            success: function (record, operation) {
                Ext.MessageBox.show({
                    title: displayName + ' record deleted',
                    msg: displayName + ' record was successfully deleted.',
                    icon: Ext.MessageBox.INFO,
                    buttons: Ext.Msg.OK
                });
            },
            failure: function (record, operation) {

                // since it couldn't be deleted, add again
                store.add(rec);

                // inform user
                Ext.MessageBox.show({
                    title: displayName + ' record could not be deleted',
                    msg: operation.getError() || (displayName + ' record could not be deleted.'),
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        });

    },
    /**
     * @cfg
     * If true all cells become editable by double-click. If false
     * Bancha doesn't create editor properties for columns.
     *
     * The buttons 'create' and 'save' expect this to be true.
     */
    editable: true,
    /**
     * @cfg
     * If true a destroy button is rendered for each record.
     * See also {@link #destroyButtonConfig}
     */
    deletable: true,
    /**
     * @cfg {String[]|Ext.button.Button[]}
     * If an array of elements, a footer toolbar is rendered.
     *
     * 'create','reset' and 'save' will be replaced by scaffolded
     * buttons, other elements are treated like default Ext JS items.
     *
     * Inside your own buttons you can set the scope property to
     * 'scaffold-scope-me', this scope provides two functions:
     *  - this.getStore() to get the grids store
     *  - this.getCellEditing() to get the grids cell editing plugin
     *
     * Default: ['->','create','reset','save']
     */
    buttons: ['->','create','reset','save'],
    /**
     * @cfg
     * Default create button config, used in buttons config.
     * If not defined scope and handler properties will be set by
     * the build function.
     */
    createButtonConfig: {
        iconCls: 'icon-add',
        text: 'Create'
    },
    /**
     * @cfg
     * Default save button config, used in buttons config
     * If not defined scope and handler properties will be set by
     * the build function.
     */
    saveButtonConfig: {
        iconCls: 'icon-save',
        text: 'Save'
    },
    /**
     * @cfg
     * Default reset button config, used in buttons config
     * If not defined scope and handler properties will be set by
     * the build function.
     */
    resetButtonConfig: {
        iconCls: 'icon-reset',
        text: 'Reset'
    },
    /**
     * @cfg
     * Default last column config, used if deletable is true to render a destroy
     * button at the end of the line.
     * The button handler is expected at destroyButtonConfig.items[0].handler, if it is
     * equal Ext.emptyFn it will be replace, otherwise the custom config is used.
     */
    destroyButtonConfig: {
        xtype: 'actioncolumn',
        width: 30,
        items: [{
            iconCls: 'icon-destroy',
            tooltip: 'Delete',
            handler: Ext.emptyFn // will be replaced by button handler
        }]
    },
    /**
     * @cfg {Function}
     * The function will be executed before scaffolding as interceptor.
     * @param {Ext.data.Model} model the model used for scaffolding
     * @param {Object} config the scaffold full config for this call
     * @param {Object} initialPanelConfig please ignore, this is a legacy argument, may having some additional Ext.grid.Panel configs
     * @return {Object|undefined} object with initial Ext.form.Panel configs
     */
    beforeBuild: function (model, config, initialPanelConfig) {},
    /**
     * @cfg {Function}
     * This function will be executed after scaffolding as interceptor.
     * @param {Object} gridConfig the just build grid panel config
     * @param {Ext.data.Model} model the model used for scaffolding
     * @param {Object} config the scaffold full config for this call
     * @param {Object} initialPanelConfig please ignore, this is a legacy argument, may having some additional Ext.grid.Panel configs
     * @return {Object|undefined} object with final Ext.grid.Panel configs or undefined to use the passed config
     */
    afterBuild: function (gridConfig, model, config, initialPanelConfig) {}
});
/*
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 0.0.1
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

/**
 * @singleton
 * @class Bancha.scaffold.Util
 * **This is only available inside Ext JS.**
 *
 * Some scaffolding util functions for {@link Ext.grid.Panel} and {@link Ext.form.Panel}.
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.Util', {
    singleton: true,

    /**
     * Makes the first letter of an string upper case.
     * @param {String} str The string to transform
     * @return {String} The transformed string with a first letter upper case
     */
    toFirstUpper: function (str) {
        if (typeof str !== 'string') {
            return str;
        }
        if (str.length === 1) {
            return str.toUpperCase();
        }
        // note: IE6 ad 7 doesn't allow array syntax for getting the first letter
        return str.substr(0,1).toUpperCase() + str.substr(1);
    },

    /**
     * Capitalizes the first word, turns underscores into spaces and strips
     * trailing *'_id'*.
     *
     * Also it converts camel case by finding upper case letters right after
     * lower case and replaceing the upper case with an space and lower case.
     *
     * examples:
     *     "user_name"  -> "User name"
     *     "userName"   -> "User name"
     *     "John Smith" -> "John Smith"
     *
     * @param {String} str
     * @return {String} The transformed string
     */
    humanize: function (str) {
        str = str.replace(/_id/g, ''); // delete _id from the string
        str = str.replace(/_/g, ' '); // _ to spaces
        str = str.replace(/([a-z])([A-Z])/g, function (all, first, second) {
            return first + ' ' + second.toLowerCase();
        }); // convert camel case (only)
        return this.toFirstUpper(str);
    },

    /**
     * Transforms a namespacd class name like *'Bancha.model.AwesomeArticle'*
     * and transforms it into a name you can show to users, in this case
     * *'Awesome article'*.
     *
     * For details on how the transformation, see also {@link #humanize}
     *
     * @param {String} classname A full classname to humanize
     * @return {String} The transformed string
     */
    humanizeClassName: function(classname) {
        // get the class name without any namespacing
        if(classname.indexOf('.')) {
            classname = classname.substr(classname.lastIndexOf('.')+1);
        }
        return this.humanize(classname);
    },

    /**
     * Makes every words first letter upper case.
     *
     * @param {String} str String of words, separated by space
     * @return {String} The transformed string
     */
    toTitle: function(str) {
        return str.replace(/ ([a-z])/g, function (all, letter) {
            return ' ' + letter.toUpperCase();
        });
    },

    /**
     * @private
     * This function will search for 'create', 'reset' and 'save' and will
     * properly replace them with the values from the config object
     *
     * It will also inject the scope into all elements where the scope
     * equals 'scaffold-scope-me'
     *
     * @param {String[]|Ext.button.Button[]} buttons the  button config, e.g. ['->','create','reset','save']
     * @param {Bancha.scaffold.form.Config|Bancha.scaffold.grid.Config} config The config with all necessary
     * replacements (onCreate, createButtonConfig, onReset, ...)
     * @param buttonScope the scope, that's applied to all replaced buttons
     * @return {String[]|Ext.button.Button[]} The build buttons array
     */
    replaceButtonPlaceHolders: function(buttons, config, buttonScope) {
        if(typeof buttons === 'undefined' || buttons.length===0) {
            return buttons;
        }

        for(var i=0, len=buttons.length; i<len; i++) {
            switch(buttons[i]) {
            case 'create':
                buttons[i] = Ext.apply(config.createButtonConfig, {
                    scope: buttonScope,
                    handler: config.onCreate
                });
                break;
            case 'reset':
                buttons[i] = Ext.apply(config.resetButtonConfig, {
                    scope: buttonScope,
                    handler: config.onReset
                });
                break;
            case 'save':
                buttons[i] = Ext.apply(config.saveButtonConfig, {
                    scope: buttonScope,
                    handler: config.onSave
                });
                break;
            default:
                // check if we should inject a scope
                if(buttons[i].scope === 'scaffold-scope-me') {
                    buttons[i].scope = buttonScope;
                }
            }
        }

        return buttons;
    },

    /**
     * Returns true if a model class name (or class) is given.
     * 
     * Normalized Ext JS 4 and 5.
     * 
     * @param {string} classOrClassName The class name or class itself.
     * @returns {boolen}
     */
    isModel: function(classOrClassName) {
        var className = Ext.isString(classOrClassName) ? classOrClassName : Ext.ClassManager.getName(classOrClassName),
            model = false;
        if(!className) {
            return false;
        }
        if(Ext.versions.extjs.major === 4) {
            // Ext JS 4
            return !!Ext.ModelManager.getModel(className);
        }
        // Ext JS 5
        var handle = Ext.Error.handle;
        Ext.Error.handle = Ext.emptyFn;
        try {
            model = Ext.data.schema.Schema.lookupEntity(className);
        } catch(e) {}
        Ext.Error.handle = handle;
        return model;
    },

    /**
     * Returns the model for a given class name (or class)
     * 
     * Normalized Ext JS 4 and 5.
     * 
     * @param {string} classOrClassName The class name or class itself.
     * @returns {Ext.Class|null} The corresponsing class, or null
     */
    getModel: function(classOrClassName) {
        var className = Ext.isString(classOrClassName) ? classOrClassName : Ext.ClassManager.getName(classOrClassName),
            model;
        if(!className) {
            Ext.Error.raise('No model given to Bancha.scaffold.Util.getModel("' + className + '").');
        }
        if(Ext.versions.extjs.major === 4) {
            // Ext JS 4
            model = Ext.ModelManager.getModel(className);
            if(!model) {
                Ext.Error.raise('No such model "' + className + '" found.');
            }
            return model;
        }
        // Ext JS 5
        return Ext.data.schema.Schema.lookupEntity(className);
    },

    /**
     * @method
     * For separation of concerns, gets/creates a store.
     *
     * Used to build the grid store and to build associated stores.
     *
     * @param {Ext.data.Model} model A model
     * @param {Bancha.scaffold.form.Config|Bancha.scaffold.grid.Config} config (optional)
     * A config object with the properties oneStorePerModel, storeDefaults and storeDefaultClass
     * @return {Ext.data.Store} The store
     */
    getStore: (function (model, config) {
        var stores = {};

        return function (model, config) {
            var modelName = Ext.ClassManager.getName(model),
                store;
            config = config || {};
            if (config.oneStorePerModel && stores[modelName]) {
                return stores[modelName];
            }

            store = Ext.create(config.storeDefaultClass || 'Ext.data.Store', Ext.apply({
                model: modelName
            }, Ext.clone(config.storeDefaults || {})));

            if (config.oneStorePerModel) {
                stores[modelName] = store;
            }

            return store;
        };
    }()),

    /**
     * This method can be overriden by your own needs.
     *
     * Tries to find the most usefull model field for dispaying. This is
     * used in the Grid scaffolding renderer for associations and can be
     * overwritten at any time.
     *
     * By default it used the models "displayField" config, which just exists
     * in Bancha Scaffold and soon should be implemented in Bancha for CakePHP.
     *
     * @param {Ext.data.Model} model The model to look through
     * @return {String} The most accurate field name
     */
    getDisplayFieldName: function(model) {
        // if defined use the display field config (when using CakePHP)
        if(model.getDisplayField && model.getDisplayField()) {
            return model.getDisplayField();
        }

        // get the field names
        var fieldNames;
        if(Ext.versions.extjs.shortVersion < 410) {
            // legacy support
            fieldNames = Ext.Array.pluck(model.prototype.fields.items, 'name');
        } else {
            fieldNames = Ext.Array.pluck(model.getFields(), 'name');
        }

        // try to find name, title or code
        if(Ext.Array.indexOf(fieldNames, 'name') !== -1) {
            return 'name';
        }
        if(Ext.Array.indexOf(fieldNames, 'title') !== -1) {
            return 'title';
        }
        if(Ext.Array.indexOf(fieldNames, 'code') !== -1) {
            return 'code';
        }

        // nothing usefull found
        return model.idProperty || fieldNames[0];
    },

    /**
     * This function may need to be customized, it generates the expected model associations "name" value,
     * to check if this field has a association (this is used for replacing id values with actual data).
     *
     * The default uses CakePHP naming conventions, e.g.
     * fieldname *'book_author_id'* expects association name *'bookAuthors'*
     *
     * @param {String} modelFieldName The fields name of an model, e.g. 'title'
     * @param {String} associationType The association type, e.g. belongsTo
     * @return {String} The guessed association name
     */
    fieldNameToModelAssociationName: function(modelFieldName, associationType) {
        if(!Ext.isString(modelFieldName)) {
            return;
        }

        var parts = modelFieldName.split('_');
        if(parts.length<2 || parts[parts.length-1] !== 'id') {
            return;
        }
        parts.pop(); // remove 'id'

        var name = parts.shift(); // the first stays lower case
        Ext.each(parts, function(part) {
            name += part.substr(0,1).toUpperCase() + part.substr(1);
        });

        return associationType==='belongsTo' ? name : name+'s';
    },

    /**
     * Returns the corresponding association for a given field, or false.
     *
     * This will not return a reference, if defined on the field. See getBelongsToModel.
     *
     * @param {Ext.data.Field} field The model field to look for an association (belongsTo)
     * @param {Ext.data.Model} model The fields model
     * @return {Ext.data.association.belongsTo|Ext.data.schema.ManyToOne|False} The found association or false
     */
    getBelongsToAssociation: function(field, model) {
        var associationName,
            associations,
            key;

        // Ext JS 5 works differently then older versions
        if(Ext.versions.extjs && Ext.versions.extjs.major === 5) {
            associations = model.associations;
            // this will detect Bancha associations
            for(key in associations) {
                if(associations.hasOwnProperty(key) && associations[key].foreignKey === field.name) {
                    return associations[key]; // match
                }
            }
            return false; // no match
        }

        // Ext JS 4 and Sencha Touch
        associationName = this.fieldNameToModelAssociationName(field.name, 'belongsTo');
        associations = Ext.isFunction(model.getAssociations) ? model.getAssociations():
                            (model.prototype ? model.prototype.associations : false);
        if(!associationName || !associations) {
            return false;
        }
        return associations.get(associationName);
    },
    /**
     * Returns the associated model for a given field. 
     *
     * Normalized the function across Ext JS 4 and 5.
     * 
     * @param {Ext.data.Field} field The model field to look for an association (belongsTo)
     * @param {Ext.data.Model} model The fields model
     * @return {Ext.data.Model|null} The associated model or null
     */
    getBelongsToModel: function(field, model) {
        // this will detect new Ext JS 5 reference associations
        if(field.reference) {
            return field.reference.cls;
        }

        // below will detect associations array configurations
        var association = this.getBelongsToAssociation(field, model);

        // if no association found
        if(!association) {
            return association;
        }

        // Ext JS 5 works differently then older versions
        if(Ext.versions.extjs && Ext.versions.extjs.major === 5) {
            return Ext.ClassManager.get(association.model);
        }

        // Ext JS 4
        return association.associatedModel;
    }
});
/*
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 0.3.0
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

/**
 * @private
 * @class Bancha.scaffold.form.override.Panel
 *
 * This override adds support for defining a scaffold config in a formpanel
 * configuration.
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.form.override.Panel', {
    requires: [
        'Ext.form.Panel',
        'Bancha.scaffold.form.Config',
        'Bancha.scaffold.data.Validators',
        'Bancha.scaffold.form.field.override.VTypes',
        'Bancha.scaffold.Util'
    ]
}, function() {

    /**
     * @class Ext.form.Panel
     * **This is only available inside Ext JS.**
     *
     * After requiring *'Bancha.scaffold.form.override.Panel'*, form panels have
     * an additional scaffold configuration.
     *
     * The simplest usage is:
     *
     *     Ext.create("Ext.form.Panel", {
     *         scaffold: 'MyApp.model.User', // the model name
     *     });
     *
     * A more complex usage example:
     *
     *     Ext.create("Ext.form.Panel", {
     *
     *         scaffold: {
     *             // define the model name here
     *             target: 'MyApp.model.User',
     *
     *             // you can tell the form to automatically load a record for edting, by id
     *             loadRecord: 3,
     *
     *             // define which buttons should be displayed
     *             buttons: ['reset','save'],
     *
     *             // advanced configs can be set here:
     *             textfieldDefaults: {
     *                 emptyText: 'Please fill this out'
     *             },
     *             datefieldDefaults: {
     *                 format: 'm/d/Y'
     *             },
     *             onSave: function() {
     *                 Ext.MessageBox.alert("Tada","You've pressed the form save button");
     *             }
     *         },
     *
     *         // and add some styling
     *         height: 350,
     *         width: 650,
     *         frame:true,
     *         title: 'Form Panel',
     *         renderTo: 'formpanel',
     *         bodyStyle:'padding:5px 5px 0',
     *         fieldDefaults: {
     *             msgTarget: 'side',
     *             labelWidth: 75
     *         },
     *         defaults: {
     *             anchor: '100%'
     *         }
     *     });
     *
     * It currently creates fields for:
     *
     *  - string
     *  - integer
     *  - float (precision is read from metadata)
     *  - boolean (checkboxes)
     *  - date
     *
     * It's recognizing following validation rules on the model to add validations
     * to the form fields:
     *
     *  - format
     *  - file
     *  - length
     *  - range
     *  - presence
     *
     * You have three possible interceptors:
     *
     *  - {@link Bancha.scaffold.form.Config#cfg-beforeBuild}         : executed before #buildConfig
     *  - {@link Bancha.scaffold.form.Config#cfg-transformFieldConfig}: executed after a field config is created
     *  - {@link Bancha.scaffold.form.Config#cfg-afterBuild}          : executed after #buildConfig created the config
     *
     *  If you use the scaffold config and don't define an id,
     * it will be created and can not be changed anymore afterwards.
     *
     * @author Roland Schuetz <mail@rolandschuetz.at>
     * @docauthor Roland Schuetz <mail@rolandschuetz.at>
     */
    Ext.apply(Ext.form.Panel, {
        /**
         * @cfg {Bancha.scaffold.form.Config|Ext.data.Model|Object|String|false} scaffold
         *
         * The simplest configuration is to set a model class or model name as scaffold
         * config. All fields, validation rules and other configurations are taken from
         * this model.
         *
         * For more complex configurations you can set a {@link Bancha.scaffold.form.Config}
         * config or class. This config must have the model name defined as target
         * config. Any property from {@link Bancha.scaffold.form.Config} can be defined here.
         */
        /**
         * @property {Bancha.scaffold.form.Config|False} scaffold
         * If this panel was scaffolded, all initial configs are stored here, otherwise false.
         */
        scaffold: false
    });

    // add scaffolding support
    Ext.override(Ext.form.Panel, {
        /**
         * @private
         */
        initComponent: function () {
            var isModel, config;

            if(this.scaffold) {
                // check if the scaffold config is a model class or string
                isModel = Ext.isString(this.scaffold) || Bancha.scaffold.Util.isModel(this.scaffold);

                // if there's a model or config object, transform to config class
                // normally we would use this.scaffold.isInstance instead of $className, but that was introduced in Ext JS 4.1
                if (isModel || (Ext.isObject(this.scaffold) && !this.scaffold.$className)) {
                    this.scaffold.triggeredFrom = 'Ext.form.Panel';
                    this.scaffold = Ext.create('Bancha.scaffold.form.Config', this.scaffold);
                }

                // apply scaffolding
                config = Ext.form.Panel.buildConfig(this.scaffold, this.initialConfig);
                Ext.apply(this, config);
                Ext.apply(this.initialConfig, config);
            }

            // continue with standard behaviour
            this.callOverridden();
        },
        statics: {

            /**
             * @private
             * @property
             * Maps model field configs with field types and additional configs
             */
            fieldToFieldConfigs: {
                'auto': {
                    xtype: 'textfield'
                },
                'string': {
                    xtype: 'textfield'
                },
                'int': {
                    xtype: 'numberfield',
                    allowDecimals: false
                },
                'float': {
                    xtype: 'numberfield'
                },
                'boolean': {
                    xtype: 'checkboxfield'
                },
                'bool': {
                    xtype: 'checkboxfield'
                },
                // a synonym
                'date': {
                    xtype: 'datefield'
                }
                // TODO OPTIMIZE Add combobox support
            },

            /**
             * @private
             * @property {Function} internalTransformFieldConfig
             * This function just hides id columns and makes it uneditable.
             * @param {Object} fieldConfig the field config to transform
             * @param {String} modelType A standard model field type like 'string'
             * (also supports 'file' for compability with http://bancha.io)
             * @return {Object} Returns an Ext.form.field.* configuration object
             */
            internalTransformFieldConfig: function (fieldConfig, modelType) {
                if (fieldConfig.name === 'id') {
                    fieldConfig.xtype = 'hiddenfield';
                }

                return fieldConfig;
            },

            /**
             * @private
             *
             * Parses ExtJS 4 validation rules.
             * 
             * Analysis the validation rules for a field and adds validation rules to the field config.
             * For what is supported see {@link Bancha.scaffold.form.Config}
             * 
             * @param {Object} field A Ext.form.field.* config
             * @param {Array} validations An array of Ext.data.validations of the model
             * @param {Bancha.scaffold.form.Config} config A Bancha.scaffold.form.Config config
             * @return {Object} Returns a Ext.form.field.* config
             */
            addValidationRuleConfigs: (function () {
                /*
                 * closure these in so they are only created once.
                 * we first create the regex and then get the string of them to not have to delete the backslashes
                 * have a bit cleaner code. It doesn't matter for performance cause it's done only once
                 */
                var alpha = /^[a-zA-Z_]+$/.toString(),
                    alphanum = /^[a-zA-Z0-9_]+$/.toString(),
                    email = /^(\w+)([\-+.][\w]+)*@(\w[\-\w]*\.){1,5}([A-Za-z]){2,6}$/.toString(),
                    url = /(((^https?)|(^ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i.toString();

                return function (field, validations, config) {
                    var name = field.name, // it's used so often, make a shortcut
                        msgAddition;

                    Ext.Array.forEach(validations, function (rule) {
                        if (rule.field && rule.field !== name) {
                            // If there's a name property it means that's validation syntax, 
                            // otherwise already filtered validators.
                            // For validation rules these are all fields, not just the mathing ones, so filter
                            return;
                        }

                        switch (rule.type) {
                        case 'presence':
                            field.allowBlank = false;
                            break;
                        case 'length':
                            
                            if (field.xtype === 'textfield') {
                                // Ext JS 4
                                if (Ext.isDefined(rule.min)) {
                                    field.minLength = rule.min;
                                }
                                if (Ext.isDefined(rule.max)) {
                                    field.maxLength = rule.max;
                                }
                                // Ext JS 5
                                if (rule.getMin && Ext.isDefined(rule.getMin())) {
                                    field.minLength = rule.getMin();
                                }
                                if (rule.getMax && Ext.isDefined(rule.getMax())) {
                                    field.maxLength = rule.getMax();
                                }
                            }
                            break;
                        case 'format':
                            
                            switch ((rule.matcher || rule.getMatcher()).toString()) {
                            case alpha:
                                field.vtype = 'alpha';
                                break;
                            case alphanum:
                                field.vtype = 'alphanum';
                                break;
                            case email:
                                field.vtype = 'email';
                                break;
                            case url:
                                field.vtype = 'url';
                                break;
                            default:
                                
                                break;
                            }
                            break;
                        case 'range':
                            // range validation works only only on numberfields
                            
                            if (field.xtype === 'numberfield') {
                                // Ext JS 4
                                if (Ext.isDefined(rule.min)) {
                                    field.minValue = rule.min;
                                }
                                if (Ext.isDefined(rule.max)) {
                                    field.maxValue = rule.max;
                                }
                                if (Ext.isDefined(rule.precision)) {
                                    field.decimalPrecision = rule.precision;
                                }
                                // Ext JS 5
                                if (rule.getMin && Ext.isDefined(rule.getMin())) {
                                    field.minValue = rule.getMin();
                                }
                                if (rule.getMax && Ext.isDefined(rule.getMax())) {
                                    field.maxValue = rule.getMax();
                                }
                                if (Ext.isDefined(rule._precision) || Ext.isDefined((rule.config || {}).precision)) {
                                    field.decimalPrecision = rule._precision || (rule.config || {}).precision;
                                }
                            }
                            break;
                        case 'file':
                            // make the field a fileuploadfield
                            field.xtype = 'fileuploadfield';
                            Ext.apply(field, config.fileuploadfieldDefaults);

                            // add validation rules
                            var extension = rule.getExtension ? rule.getExtension() : rule.extension;
                            if (Ext.isString(extension)) {
                                extension = [extension];
                            }
                            if (Ext.isArray(extension)) {
                                field.vtype = 'fileExtension';
                                field.validExtensions = extension;
                            }
                            break;
                        default:
                            
                            break;
                        }
                        // TODO OPTIMIZE Also include inclusion and exclusion
                    }); //eo forEach
                    return field;
                }; //eo return fn
            }()),

            /**
             * @private
             *
             * Parses ExtJS 5 validator rules.
             * 
             * Analysis the validator rules for a field and adds validation rules to the field config.
             * For what is supported see {@link Bancha.scaffold.form.Config}
             * 
             * @param {Object} field A Ext.form.field.* config
             * @param {Array} validators An array of Ext.data.validators.*'s of the model
             * @param {Bancha.scaffold.form.Config} config A Bancha.scaffold.form.Config config
             * @return {Object} Returns a Ext.form.field.* config
             */
            addValidatorRuleConfigs: function (field, validators, config) {
                var fieldValidators = validators[field.name];
                if(!fieldValidators) {
                    return field; // no validator rules found
                }
                if(!Ext.isArray(fieldValidators)) {
                    fieldValidators = [fieldValidators]; // always have an array
                }

                return this.addValidationRuleConfigs(field, fieldValidators, config);
            },

            /**
             * @private
             * Builds a field with all defaults defined here
             * @param {String} type The model field type
             * @param {Object} defaults (optional) Defaults like textfieldDefaults as property of this config.
             * See {@link #buildConfig}'s config property
             * @return {Object} Returns a Ext.form.field.* config
             */
            buildDefaultFieldFromModelType: function (type, defaults) {
                defaults = defaults || {};
                var field = Ext.clone(this.fieldToFieldConfigs[type]),
                    fieldDefaults = Ext.clone(defaults.fieldDefaults || this.fieldDefaults),
                    // make a new object of defaults
                    fieldTypeDefaults = Ext.clone(defaults[field.xtype + 'Defaults'] || this[field.xtype + 'Defaults']);
                return Ext.apply(fieldDefaults, field, fieldTypeDefaults);
            },

            /**
             * @private
             * Creates a Ext.form.Field config from an model field type
             * @param {String} type The model's current field
             * @param {Bancha.scaffold.form.Config} config A config object
             * @param {Array} validations (optional) An array of Ext.data.validations of the model
             * @param {Object} isEditorfield (optional) True to don't add field label (usefull e.g. in an editor grid)
             * @param {Object} nonEditorFieldModelField (optional) Dirty hack to set the fieldname on
             * form panel creations, should be refactored!
             * @return {Object} Returns a field config
             */
            buildFieldConfig: function (modelField, config, validations, isEditorfield) {
                var type = Ext.versions.extjs.major === 5 ? modelField.type : modelField.type.type,
                    model = config.target,
                    field = this.buildDefaultFieldFromModelType(type, config),
                    Util = Bancha.scaffold.Util,
                    defaultAltFormats,
                    associatedModel;

                // infer name
                field.name = modelField.name;
                if (!isEditorfield) {
                    field.fieldLabel = Util.humanize(modelField.name);
                }

                // infer date format into editor (not needed for editor fields)
                if(type==='date' && !isEditorfield && modelField.dateFormat) {
                    if(!field.format) {
                        // keep it simple and use the model date format as display format
                        field.format = modelField.dateFormat;
                    } else {
                        // allow the developer to override the date format

                        // to make sure reading and writing still works as expected
                        // add the model date format to altFormats for reading
                        defaultAltFormats = (((Ext.form.field || {}).Date || {}).prototype || {}).altFormats ||
                                    'm/d/Y|n/j/Y|n/j/y|m/j/y|n/d/y|m/j/Y|n/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|n-j|n/j';
                        field.altFormats = (field.altFormats || defaultAltFormats)+'|'+modelField.dateFormat;

                        // and enforce the submit value to match the model date format
                        field.submitFormat = modelField.dateFormat;
                    }
                }

                // add some additional validation rules from model validation rules
                if (Ext.isDefined(validations) && validations.length) {
                    field = this.addValidationRuleConfigs(field, validations, config);
                }
                if (Ext.isDefined(validations) && Ext.Object.getSize(validations)>0) {
                    field = this.addValidatorRuleConfigs(field, validations, config);
                }
                if(!field) {
                    return;
                }

                // check for associations
                associatedModel = Util.getBelongsToModel(modelField, model);
                if(associatedModel) {
                    Ext.apply(field, {
                        xtype: 'combobox',
                        store: Util.getStore(associatedModel, config),
                        displayField: Util.getDisplayFieldName(associatedModel),
                        valueField: associatedModel.prototype.idProperty || 'id',
                        queryMode: 'local'
                    });
                }

                // now make custom transforms
                field = this.internalTransformFieldConfig(field,type);
                if (typeof config.transformFieldConfig === 'function') {
                    field = config.transformFieldConfig(field, type);
                }

                // fileuploads are currently not supported in editor fields (ext doesn't render them usable)
                if (isEditorfield && field.xtype === 'fileuploadfield') {
                    field = undefined; // TODO Maybe on double click open a modal window for file uploads
                }

                return field;
            },

            /**
             * @private
             * This function is used if you are using Bancha.scaffold.form.Config with
             * the full Bancha library. It will automatically find all
             * api configurations
             */
            buildBanchaApiConfig: function(model, initialApi) {
                initialApi = initialApi || {};

                

                var modelName = Ext.ClassManager.getName(model),
                    stubName = modelName.substr(Bancha.modelNamespace.length + 1),
                    stub = Bancha.getStub(stubName);

                

                return {
                    // The server-side method to call for load() requests
                    load: initialApi.read || stub.read,
                    // as first and only param you must add data: {id: id} when loading
                    // The server-side must mark the submit handler as a 'formHandler'
                    submit: initialApi.submit || stub.submit
                };
            },

            /**
             * @private
             * You only need this is you're adding additional buttoms to the form inside the
             * afterBuild function.
             * Since the form panel doesn't give us an useful scope to get the form panel,
             * this function will create an proper scope. The scope provides two functions:
             *  - this.getPanel() to get the form panel
             *  - this.getForm() to get the basic form
             *
             * @param {Function} handler A button handler function to apply the scope to
             * @param {Number|String} id The form panel id
             */
            buildButtonScope: (function () {
                var scopePrototype = {
                    getPanel: function () {
                        return this.panel || Ext.ComponentManager.get(this.id);
                    },
                    getForm: function () {
                        return this.form || this.getPanel().getForm();
                    }
                };

                return function (id) {
                    return Ext.apply({
                        id: id
                    }, scopePrototype);
                };
            }()),

            /**
             * @private
             * Builds form configs from the metadata, for scaffolding purposes.
             * By default data is loaded from the server if an id is supplied and
             * onSave it pushed the data to the server.
             *
             * Guesses are made by model field configs and validation rules.
             * @param {Bancha.scaffold.form.Config} config The config object to apply
             * @param {Object} initialPanelConfig   (optional) Some additional Ext.form.Panel
             *                                      configs which are applied to the config
             * @return {Object}                     object with Ext.form.Panel configs
             */
            buildConfig: function (config, initialPanelConfig) {
                initialPanelConfig = initialPanelConfig || {};
                var fields = [],
                    model = Bancha.scaffold.Util.getModel(config.target),
                    me = this,
                    formConfig,
                    prototypeFieldKeys,
                    fieldNames,
                    validations,
                    isExtJS5,
                    id,
                    loadFn;

                

                // build initial config
                formConfig = config.beforeBuild(model, config, initialPanelConfig) || {};

                // in Firefox fields.keys is a native function with Ext JS 4
                prototypeFieldKeys = typeof model.prototype.fields.keys === 'function' ? false : model.prototype.fields.keys;

                // if there is a fields config, use this for ordering
                // Otherwise use fields.keys for Sencha Touch and Ext JS 4
                fieldNames = config.fields || prototypeFieldKeys || Ext.Object.getKeys(model.fieldsMap);

                // build all columns
                validations = model.prototype.validations || model.validators; // ST & Ext JS 4 || Ext JS 5
                isExtJS5 = Ext.versions.extjs.major === 5;
                Ext.each(fieldNames, function(fieldName) {
                    if(Ext.Array.indexOf(config.exclude, fieldName) === -1) { // if not excluded
                        var field = isExtJS5 ? model.getField(fieldName) : model.prototype.fields.getByKey(fieldName);
                        fields.push(
                            me.buildFieldConfig(field, config, validations)
                        );
                    }
                });

                // probably not neccessary in Ext JS 4 and 5!
                // if one of the fields is a fileupload, mark the form
                Ext.each(fields, function (field) {
                    if (field.xtype === 'fileuploadfield') {
                        formConfig.isUpload = true;
                        formConfig.fileUpload = true;
                        return false;
                    }
                    return true;
                });

                // for scoping reason we have to force an id here
                id = initialPanelConfig.id || Ext.id(null, 'formpanel-');
                formConfig.id = id;

                // build buttons
                if(!config.hasOwnProperty('buttons')) {
                    // this a default, so clone before instanciating
                    // See also test case "should clone all configs, so that you
                    // can create multiple forms from the same defaults"
                    config.buttons = Ext.clone(config.buttons || []);
                }
                config.buttons = Bancha.scaffold.Util.replaceButtonPlaceHolders(config.buttons,
                                                            config, this.buildButtonScope(id));

                // extend formConfig
                Ext.apply(formConfig, initialPanelConfig, {
                    id: id,
                    api: config.buildApiConfig(model, initialPanelConfig.api),
                    paramOrder: ['data'],
                    items: fields,
                    buttons: config.buttons
                });

                // the scaffold config of the grid is saved as well
                formConfig.scaffold = config;

                // autoload the record
                if (Ext.isDefined(config.loadRecord) && config.loadRecord !== false) {
                    // load the record on component load
                    loadFn = function (component, options) {
                        component.load({
                            params: {
                                data: {
                                    data: {
                                        id: config.loadRecord
                                    }
                                } // bancha expects it this way
                            }
                        });
                    };
                    // if there's already a function, batch them
                    formConfig.listeners = formConfig.listeners || {};
                    if (formConfig.listeners.afterrender) {
                        formConfig.listeners.afterrender = Ext.Function.createSequence(formConfig.listeners.afterrender, loadFn);
                    } else {
                        formConfig.listeners.afterrender = loadFn;
                    }
                }

                // return after interceptor
                return config.afterBuild(formConfig, model, config, initialPanelConfig) || formConfig;
            }
        } // eo statics
    }); //eo override

    // Ext JS prior to 4.1 does not yet support statics, manually add them
    if(parseInt(Ext.versions.extjs.shortVersion,10) < 410) {
        Ext.apply(Ext.form.Panel, Ext.form.Panel.prototype.statics);
    }
});
/*
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 0.3.0
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

/**
 * @private
 * @class Bancha.scaffold.grid.override.Panel
 *
 * This override adds support for defining a scaffold config in a gridpanel
 * configuration.
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.grid.override.Panel', {
    requires: [
        'Ext.grid.Panel',
        'Bancha.scaffold.form.Config',
        'Bancha.scaffold.grid.Config',
        'Bancha.scaffold.data.Validators',
        'Bancha.scaffold.form.field.override.VTypes',
        'Bancha.scaffold.Util'
    ]
}, function() {

    /**
     * @class Ext.grid.Panel
     * **This is only available inside Ext JS.**
     *
     * After requiring *'Bancha.scaffold.grid.override.Panel'*, grid panels have
     * an additional scaffold configuration.
     *
     * The simplest usage is:
     *
     *     Ext.create("Ext.grid.Panel", {
     *         scaffold: 'MyApp.model.User', // the model name
     *     });
     *
     * A more complex usage example is:
     *
     *     Ext.create("Ext.grid.Panel", {
     *
     *         // for more configurations
     *         scaffold: {
     *
     *             // define the model name here
     *             target: 'MyApp.model.User',
     *
     *             // enable full CRUD on the grid (default)
     *             deletable: true,
     *             buttons: ['->','create','reset','save'],
     *
     *
     *             // and some more advanced configs
     *             columnDefaults: {
     *                 width: 200
     *             },
     *             datecolumnDefaults: {
     *                 format: 'm/d/Y'
     *             },
     *             // use the same store for all grids
     *             oneStorePerModel: true,
     *             // custom onSave function
     *             onSave: function() {
     *                 Ext.MessageBox.alert("Tada","You've pressed the save button");
     *             },
     *             formConfig: {
     *                 textfieldDefaults: {
     *                     minLength: 3
     *                 }
     *             }
     *         },
     *
     *         // and add some styling
     *         height   : 350,
     *         width    : 650,
     *         frame    : true,
     *         title    : 'User Grid',
     *         renderTo : 'gridpanel'
     *     });
     *
     * If the editable property is true, a {@link Bancha.scaffold.form.Config}
     * config is used to create the editor fields.
     *
     * You have three possible interceptors:
     *
     *  - {@link Bancha.scaffold.form.Config#cfg-beforeBuild}         : executed before the config is build
     *  - {@link Bancha.scaffold.form.Config#cfg-transformFieldConfig}: executed after a field config is created
     *  - {@link Bancha.scaffold.form.Config#cfg-afterBuild}          : executed after the config is created
     *
     * @author Roland Schuetz <mail@rolandschuetz.at>
     * @docauthor Roland Schuetz <mail@rolandschuetz.at>
     */
    Ext.apply(Ext.grid.Panel, {
        /**
         * @cfg {Bancha.scaffold.grid.Config|Ext.data.Model|Object|String|False} scaffold
         *
         * The simplest configuration is to set a model class or model name as scaffold
         * config. All fields, validation rules and other configurations are taken from
         * this model.
         *
         * For more complex configurations you can set a {@link Bancha.scaffold.grid.Config}
         * config or instance. This config must have the model name defined as *target*
         * config. Any property from {@link Bancha.scaffold.grid.Config} can be defined here.
         */
        /**
         * @property {Bancha.scaffold.grid.Config|False} scaffold
         * If this panel was scaffolded, all initial configs are stored here, otherwise false.
         */
        scaffold: false
    });

    // add scaffolding support
    Ext.override(Ext.grid.Panel, {
        initComponent: function () {
            var isModel, config;

            if(this.scaffold) {
                // check is the scaffold config is a model class or string
                isModel = Ext.isString(this.scaffold) || Bancha.scaffold.Util.isModel(this.scaffold);

                // if there's a model or config object, transform to config class
                // normally we would use this.scaffold.isInstance instead of $className, but that was introduced in Ext JS 4.1
                if (isModel || (Ext.isObject(this.scaffold) && !this.scaffold.$className)) {
                    this.scaffold.triggeredFrom = 'Ext.grid.Panel';
                    this.scaffold = Ext.create('Bancha.scaffold.grid.Config', this.scaffold);
                }

                // apply scaffolding
                config = Ext.grid.Panel.buildConfig(this.scaffold, this.initialConfig);
                Ext.apply(this, config);
                Ext.apply(this.initialConfig, config);
            }

            // continue with standard behaviour
            this.callOverridden();
        },
        statics: {
            /**
             * @private
             * @property
             * Maps model types with column types and additional configs for prototyping
             */
            fieldToColumnConfigs: {
                'auto': {
                    xtype: 'gridcolumn'
                },
                'string': {
                    xtype: 'gridcolumn'
                },
                'int': {
                    xtype: 'numbercolumn',
                    format: '0'
                },
                'float': {
                    xtype: 'numbercolumn'
                },
                'boolean': {
                    xtype: 'booleancolumn'
                },
                'bool': {
                    xtype: 'booleancolumn'
                },
                // a synonym
                'date': {
                    xtype: 'datecolumn'
                }
            },
            /**
             * @private
             * @property {Function} internalTransformColumnConfig
             * This function just hides id columns and makes it uneditable.
             * @param {Object} columnConfig the column config to transform
             * @param {String} modelType A standard model field type like 'string'
             * (also supports 'file' for compability with http://bancha.io)
             * @return {Object} Returns an Ext.grid.column.* configuration object
             */
            internalTransformColumnConfig: function (columnConfig, modelType) {
                if (columnConfig.dataIndex === 'id') {
                    columnConfig.hidden = true;
                    columnConfig.editor = undefined;
                }

                return columnConfig;
            },
            /**
             * @private
             * Builds a column with all defaults defined here
             * @param {String} type The model field type
             * @param {Object} defaults (optional) Defaults like numbercolumnDefaults as property of this config.
             * See {@link #buildConfig}'s config property
             * @return {Object} Returns an Ext.grid.column.* configuration object
             */
            buildDefaultColumnFromModelType: function (type, defaults) {
                defaults = defaults || {};
                var column = this.fieldToColumnConfigs[type],
                    columnDefaults = Ext.clone(defaults.columnDefaults || this.columnDefaults),
                    // make a new object of defaults
                    columnTypeDefaults = defaults[column.xtype + 'Defaults'] || this[column.xtype + 'Defaults'];
                return Ext.apply(columnDefaults, column, columnTypeDefaults);
            },
            /**
             * @private
             * Creates a Ext.grid.Column config from an model field type
             * @param {String}                       field         The model field
             * @param {Bancha.scaffold.grid.Config} config        The grid config object
             * @param {Object}                      gridListeners The grid listeners array, can be augmented by this function
             * @param {Array}                       validations   (optional) An array of Ext.data.validations of the model
             * @return {Object}                                   Returns an Ext.grid.column.* configuration object
             */
            buildColumnConfig: function (field, config, validations, gridListeners) {
                var fieldType = Ext.versions.extjs.major === 5 ? field.type : field.type.type,
                    column = this.buildDefaultColumnFromModelType(fieldType, config),
                    model = config.target,
                    associatedModel,
                    store,
                    fieldName;

                // infer name
                if (field.name) {
                    column.text = Bancha.scaffold.Util.humanize(field.name);
                    column.dataIndex = field.name;
                }

                // check for associations
                associatedModel = Bancha.scaffold.Util.getBelongsToModel(field, model);
                if(associatedModel) {
                    // load the store
                    store = Bancha.scaffold.Util.getStore(associatedModel, config);
                    // calculate the field name only once per column
                    fieldName = Bancha.scaffold.Util.getDisplayFieldName(associatedModel);

                    // build a renderer
                    column.renderer = function(id) {
                        var rec = store.getById(id);

                        // display either the found record name or Unknown
                        return rec ? rec.get(fieldName) : (Bancha.t ? Bancha.t('Unknown') : 'Unknown');
                    };

                    // For Ext JS 5, update after the id was changed
                    column.updater = function(cell, value, record, view) {
                        var name = column.renderer(value);
                        Ext.fly(cell).child('div').setHtml(name);
                    };

                    // if necessary re-render when the data is available
                    gridListeners.render = Ext.Function.createSequence(gridListeners.render || Ext.emptyFn, function(gridpanel) {
                        if(store.getCount() === 0) {
                            store.on('load', function(store, records, successful, eOpts) {
                                if(successful) {
                                    // re-render
                                    gridpanel.getView().refresh();
                                }
                            });
                        }
                    });
                } //eo if association

                // add an editor
                if(config.editable) {
                    // build the editor field
                    Ext.syncRequire([
                        'Ext.form.Panel',
                        'Bancha.scaffold.form.override.Panel'
                    ]);
                    column.editor = Ext.form.Panel.buildFieldConfig(field, config.formConfig, validations, true);

                    if(column.editor) {
                        // if this should have an editor field
                        // now make custom field transforms
                        column.editor = Ext.form.Panel.internalTransformFieldConfig(column.editor, fieldType);
                        if (typeof config.formConfig.transformFieldConfig === 'function') {
                            column.editor = config.formConfig.transformFieldConfig(column.editor, fieldType);
                        }
                    }
                }

                // now make custom transforms
                column = Ext.grid.Panel.internalTransformColumnConfig(column, fieldType);
                if (typeof config.transformColumnConfig === 'function') {
                    column = config.transformColumnConfig(column, fieldType);
                }

                return column;
            },
            /**
             * @private
             * Builds grid columns from the model definition, for scaffolding purposes.
             * This does not unclude the support for create, update and/or destroy!
             *
             * @param {Bancha.scaffold.grid.Config} config        The grid config
             * @param {Object}                      gridListeners The grid listeners array, can be augmented by this function
             * @return {Array}                                    Returns an array of Ext.grid.column.* configs
             */
            buildColumns: function (config, gridListeners) {
                var columns = [],
                    model = config.target,
                    me = this,
                    prototypeFieldKeys,
                    fieldNames,
                    validations,
                    isExtJS5,
                    button;

                if(!Ext.isArray(config.exclude)) {
                    
                    config.exclude = [];
                }

                // in Firefox fields.keys is a native function with Ext JS 4
                prototypeFieldKeys = typeof model.prototype.fields.keys === 'function' ? false : model.prototype.fields.keys;

                // if there is a fields config, use this for ordering
                // Otherwise use fields.keys for Sencha Touch and Ext JS 4
                fieldNames = config.fields || prototypeFieldKeys || Ext.Object.getKeys(model.fieldsMap);

                // build all columns
                validations = model.prototype.validations || model.validators; // ST & Ext JS 4 || Ext JS 5
                isExtJS5 = Ext.versions.extjs.major === 5;
                Ext.each(fieldNames, function(fieldName) {
                    if(Ext.Array.indexOf(config.exclude, fieldName) === -1) { // if not excluded
                        var field = isExtJS5 ? model.getField(fieldName) : model.prototype.fields.getByKey(fieldName);
                        columns.push(
                            me.buildColumnConfig(field, config, validations, gridListeners)
                        );
                    }
                });

                // add a destroy button
                if (config.deletable) {
                    button = Ext.clone(config.destroyButtonConfig);
                    if (button.items[0].handler === Ext.emptyFn) {
                        button.items[0].handler = config.onDelete;
                    }
                    columns.push(button);
                }

                return columns;
            },
            /**
             * @private
             * Builds a grid config from a model definition, for scaffolding purposes.
             * Guesses are made by model field configs and validation rules.
             *
             * @param {Ext.data.Model|String}       model              The model class or model name
             * @param {Bancha.scaffold.grid.Config} config             The grid config
             * @param {Object}                      initialPanelConfig (optional) Some additional grid panel configs
             * @return {Object}                                        Returns an Ext.grid.Panel configuration object
             */
            buildConfig: function (config, initialPanelConfig) {
                initialPanelConfig = initialPanelConfig || {};
                var model = Bancha.scaffold.Util.getModel(config.target),
                    gridConfig, cellEditing, store, scope, listeners;

                

                // call beforeBuild callback
                gridConfig = config.beforeBuild(model, config, initialPanelConfig) || {};

                // basic config
                store = Bancha.scaffold.Util.getStore(model, config);
                listeners = {};
                Ext.apply(gridConfig, {
                    store: store,
                    columns: this.buildColumns(config, listeners),
                    listeners: listeners // this is necessary for refreshing after associated stores are loaded
                });

                // add config for editable fields
                if (config.editable) {
                    cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
                        clicksToEdit: 2
                    });
                    Ext.apply(gridConfig, {
                        selType: 'cellmodel',
                        plugins: [cellEditing]
                    });
                }

                // replace button place holders and build toolbar
                if(config.buttons && config.buttons.length) {

                    scope = {
                        getCellEditing: function() {
                            return cellEditing;
                        },
                        getStore: function() {
                            return store;
                        }
                    };

                    // build buttons
                    if(!config.hasOwnProperty('buttons')) {
                        // this a default, so clone before instanciating
                        // See also test case "should clone all configs, so that you
                        // can create multiple forms from the same defaults"
                        config.buttons = Ext.clone(config.buttons || []);
                    }
                    config.buttons = Bancha.scaffold.Util.replaceButtonPlaceHolders(config.buttons, config, scope);

                    gridConfig.dockedItems = [{
                        xtype: 'toolbar',
                        dock: 'bottom',
                        ui: 'footer',
                        items: config.buttons
                    }];
                }

                // apply user configs
                if (Ext.isObject(initialPanelConfig)) {
                    gridConfig = Ext.apply(gridConfig, initialPanelConfig);
                }

                // the scaffold config of the grid is saved as well
                gridConfig.scaffold = config;

                // return after interceptor
                return config.afterBuild(gridConfig, model, config, initialPanelConfig) || gridConfig;
            }
        } // eo statics
    }); //eo override

    // Ext JS prior to 4.1 does not yet support statics, manually add them
    if(parseInt(Ext.versions.extjs.shortVersion,10) < 410) {
        Ext.apply(Ext.grid.Panel, Ext.grid.Panel.prototype.statics);
    }
});
/*
 *
 * Bancha Scaffolding Library
 * Copyright 2011-2014 codeQ e.U.
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @package       Bancha.scaffold
 * @copyright     Copyright 2011-2014 codeQ e.U.
 * @link          http://scaffold.bancha.io
 * @since         Bancha Scaffold v 0.5.3
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 * @author        Roland Schuetz <mail@rolandschuetz.at>
 * @version       Bancha Scaffold v 2.0.2
 *
 * For more information go to http://scaffold.bancha.io
 */

/**
 * @class Bancha.scaffold.grid.ManagementPanel
 * @extends Ext.tab.Panel
 * 
 * **This is only available inside Ext JS.**
 *
 * This will create a TabPanel with one tab per model.
 * It will autodetect the models capabilities from the proxy.
 *
 * Example:
 *
 *     Ext.create('Bancha.scaffold.grid.ManagementPanel', {
 *         models: [
 *             'MyApp.model.User',
 *             'MyApp.model.Post'
 *         ]
 *     });
 *
 * @author Roland Schuetz <mail@rolandschuetz.at>
 * @docauthor Roland Schuetz <mail@rolandschuetz.at>
 */
Ext.define('Bancha.scaffold.grid.ManagementPanel', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.managementpanel',

    /**
     * @cfg {String[]|Ext.data.Model[]} models
     * Define the models which should be added to the panel.
     */
    models: [],
    /**
     * @cfg {Object} panelDefaults
     * This config will be applied to each model grid and will overwrite
     * scaffolded code
     */
    panelDefaults: {},
    /**
     * @cfg {Object} scaffoldDefaults
     * This config will be applied to each model grid's scaffold property
     * and will overwrite scaffolded code
     */
    scaffoldDefaults: {},
    initComponent: function () {
        
        this.models = this.models || [];
        this.items = this.items || [];

        // build up all screens
        var items = this.items,
            me = this;
        Ext.each(this.models, function(model) {
            var modelName = Ext.isString(model) ? model : model.getName();
            model = Bancha.scaffold.Util.getModel(modelName);

            // probably some newbies confuse the namespaced and unnamespaced model names
            // so for Bancha users also support not namespaced version
            if(!model && Bancha.modelNamespace) {
                model = Bancha.scaffold.Util.getModel(Bancha.modelNamespace + '.' + modelName);
            }

            

            var tabitem = {
                xtype: 'gridpanel',
                title: Bancha.scaffold.Util.toTitle(
                            Bancha.scaffold.Util.humanizeClassName(modelName)),
                scaffold: {
                    target: modelName,
                    buttons: false,
                    deletable: true
                }
            };

            // create a new object
            var proxy = model.getProxy();
            if(proxy.api) {
                // it's an ext direct proxy
                var buttons = ['->'];
                if(proxy.api.create) {
                    buttons.push('create');
                }
                if(proxy.api.create || proxy.api.update) {
                    buttons.push('reset');
                    buttons.push('save');
                }
                if(buttons.length > 1) {
                    // the model supports create and/or save
                    tabitem.scaffold.buttons = buttons;
                } else {
                    tabitem.scaffold.editable = false;
                }
                if(!proxy.api.destroy) {
                    tabitem.scaffold.deletable = false;
                }
            } else if(proxy.writer) {
                // we can see that there is a writer, so provide all buttons
                delete tabitem.scaffold.buttons;
            }

            // add panel configs and possibly overwrite scaffolded code
            tabitem = Ext.apply(tabitem, me.panelDefaults);
            tabitem.scaffold = Ext.apply(tabitem.scaffold, me.scaffoldDefaults);

            // add tab to items
            items.push(tabitem);
        }); //eo each

        this.callParent();
    }
});

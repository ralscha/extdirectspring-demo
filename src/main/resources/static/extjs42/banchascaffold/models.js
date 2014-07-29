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
/*jslint browser: true, vars: true, nomen: true, eqeq: false, plusplus: true, bitwise: true, regexp: true, newcap: true, sloppy: true, white: true */
/*jshint bitwise:true, curly:true, eqeqeq:true, forin:true, immed:true, latedef:true, newcap:true, noarg:true, noempty:true, regexp:true, undef:true, trailing:false */
/*global Ext, Bancha */

// init Ext.Direct Provider
Ext.direct.Manager.addProvider(Bancha.REMOTE_API);

// setup the path to the delete image in local environments
Bancha.scaffold.Grid.destroyButtonConfig.items[0].icon = 'img/icons/delete.png';

// define the article model
Ext.define('Bancha.model.Article', {
	extend: 'Ext.data.Model',
	proxy: {
		type: 'direct',
		batchActions: false,
		api: {
			read: bancha.remoteStubs.articleService.read,
			create: bancha.remoteStubs.articleService.create,
			update: bancha.remoteStubs.articleService.update,
			destroy: bancha.remoteStubs.articleService.destroy
		},
		reader: {
			root: 'records'
		}
	},
	idProperty: 'id',
	fields: [ {
		name: 'id',
		type: 'int'
	}, {
		name: 'title',
		type: 'string'
	}, {
		name: 'date',
		type: 'date',
		dateFormat: 'Y-m-d\\TH:i:s'
	}, {
		name: 'body',
		type: 'string'
	}, {
		name: 'published',
		type: 'boolean'
	}, {
		name: 'user_id',
		type: 'int'
	} ],
	validations: [ {
		type: 'presence',
		field: 'title'
	}, {
		type: 'numberformat',
		field: 'userId'
	} ],
    associations:[
        {
          type:'belongsTo',
           model:'Bancha.model.User',
           name:'users'
        }
    ]
});

// define the user model
Ext.define('Bancha.model.User', {
	extend: 'Ext.data.Model',
	proxy: {
		type: 'direct',
		api: {
			read: bancha.remoteStubs.banchaUserService.read,
			create: bancha.remoteStubs.banchaUserService.create,
			update: bancha.remoteStubs.banchaUserService.update,
			destroy: bancha.remoteStubs.banchaUserService.destroy
		},
		reader: {
			root: 'records'
		}
	},
	idProperty: 'id',
	fields: [ {
		name: 'id',
		type: 'int'
	}, {
		name: 'name',
		type: 'string'
	}, {
		name: 'login',
		type: 'string'
	}, {
		name: 'created',
		type: 'date',
		dateFormat: 'Y-m-d\\TH:i:s'
	}, {
		name: 'email',
		type: 'string'
	}, {
		name: 'avatar',
		type: 'string'
	}, {
		name: 'weight',
		type: 'float'
	}, {
		name: 'height',
		type: 'int'
	} ],
	validations: [ {
		type: 'numberformat',
		field: 'id',
		'precision': 0
	}, {
		type: 'presence',
		field: 'name'
	}, {
		type: 'length',
		field: 'name',
		min: 3,
		max: 64
	}, {
		type: 'presence',
		field: 'login'
	}, {
		type: 'length',
		field: 'login',
		min: 3,
		max: 64
	}, {
		type: 'format',
		field: 'login',
		matcher: /^[a-zA-Z0-9_]+$/
	// alphanum regex
	}, {
		type: 'presence',
		field: 'email'
	}, {
		type: 'format',
		field: 'email',
		matcher: /^(\w+)([\-+.][\w]+)*@(\w[\-\w]*\.){1,5}([A-Za-z]){2,6}$/
	// email regex
	}, {
		type: 'numberformat',
		field: 'weight',
		precision: 2
	}, {
		type: 'numberformat',
		field: 'height',
		precision: 0
	}, {
		type: 'numberformat',
		field: 'height',
		min: 50,
		max: 300
	}, {
		type: 'file', // todo should create a file upload field
		field: 'avatar',
		extension: [ 'gif', 'jpeg', 'png', 'jpg' ]
	} ],
    associations: [
        {
            type: 'hasMany',
            model: 'Bancha.model.Article',
            name: 'articles'
        }
    ]
});

// define book model (for management panel)
Ext.define('Bancha.model.Book', {
	extend: 'Ext.data.Model',
	proxy: {
		type: 'direct',
		api: {
			read: bancha.remoteStubs.bookService.read
		},
		reader: {
			root: 'records'
		}
	},
	idProperty: 'id',
	fields: [ {
		name: 'id',
		type: 'int'
	}, {
		name: 'title',
		type: 'string'
	}, {
		name: 'published',
		type: 'boolean'
	}, {
		name: 'user_id',
		type: 'int'
	} ],
	validations: [ {
		type: 'presence',
		field: 'title'
	}, {
		type: 'numberformat',
		field: 'userId'
	} ],
    associations:[
        {
          type:'belongsTo',
           model:'Bancha.model.User',
           name:'users'
        }
    ]
});

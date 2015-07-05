Ext.define('Todo.store.Tree', {
    extend: 'Ext.data.TreeStore',

    root: {
        expanded: true
    },

    proxy: {
        type: 'direct',
        directFn: 'todoServiceExt.getTree'
    }
});
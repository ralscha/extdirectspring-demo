Ext.define('SimpleTasks.view.lists.Tree', {
    extend: 'Ext.tree.Panel',
    xtype: 'listTree',

    title: 'Lists',
    store: 'Lists',
    hideHeaders: true,

    dockedItems: [
        {
            xtype: 'toolbar',
            dock: 'bottom',
            items: [
                {
                    iconCls: 'tasks-new-list',
                    tooltip: 'New List'
                },
                {
                    iconCls: 'tasks-delete-list',
                    id: 'delete-list-btn',
                    tooltip: 'Delete List'
                },
                {
                    iconCls: 'tasks-new-folder',
                    tooltip: 'New Folder'
                },
                {
                    iconCls: 'tasks-delete-folder',
                    id: 'delete-folder-btn',
                    tooltip: 'Delete Folder'
                }
            ]
        }
    ],

    viewConfig: {
        plugins: {
            ptype: 'tasksdragdrop',
            dragText: 'Drag to reorder',
            ddGroup: 'task'
        }
    },

    initComponent: function() {
        var me = this;            
        me.plugins = [me.cellEditingPlugin = Ext.create('Ext.grid.plugin.CellEditing')];

        me.columns = [
            {
                xtype: 'treecolumn',
                dataIndex: 'name',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    selectOnFocus: true,
                    allowOnlyWhitespace: false
                },
                renderer: Ext.bind(me.renderName, me)
            },
            {
                xtype: 'actioncolumn',
                width: 24,
                icon: '//demo.rasc.ch/resources/ext-5.0.1/examples/simple-tasks/resources/images/delete.png',
                iconCls: 'x-hidden',
                tooltip: 'Delete',
                handler: Ext.bind(me.handleDeleteClick, me)
            }
        ];
        
        me.callParent(arguments);

        me.on('beforeedit', me.handleBeforeEdit, me);
				        me.relayEvents(me.getView(), ['taskdrop', 'listdrop']);

    },


    handleDeleteClick: function(gridView, rowIndex, colIndex, column, e) {
        // Fire a "deleteclick" event with all the same args as this handler
        this.fireEvent('deleteclick', gridView, rowIndex, colIndex, column, e);
    },


    handleBeforeEdit: function(editingPlugin, e) {
        return e.record.get('id') !== 0;
    },


    renderName: function(value, metaData, list, rowIndex, colIndex, store, view) {
    	
        var tasksStore = Ext.StoreMgr.lookup('Tasks'),
            count = 0;

        (function countTasks(list) {
            count += tasksStore.queryBy(function(task, id) {
                // only show count for tasks that are not done
                return task.get('list_id') === list.get('id') && task.get('done') === false;
            }).getCount();

            list.eachChild(function(child) {
                countTasks(child);
            });
        }(list));

        return list.data.id + ":" + value + ' (' + count + ')';
    },

    refreshView: function() {
        this.getView().refresh();
    }

});

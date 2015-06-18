Ext.define('SimpleTasks.controller.Lists', {
    extend: 'Ext.app.Controller',

    models: ['List'],
    stores: ['Lists', 'Tasks'],

    views: [
        'lists.Tree',
        'lists.ContextMenu',
        'Toolbar'
    ],

    refs: [
        {
            ref: 'listTree',
            selector: 'listTree'
        },
        {
            ref: 'taskGrid',
            selector: 'taskGrid'
        },
        {
            ref: 'taskForm',
            selector: 'taskForm'
        },
        {
            ref: 'contextMenu',
            selector: 'listsContextMenu',
            xtype: 'listsContextMenu',
            autoCreate: true
        }
    ],

    init: function() {
        var me = this,
            listsStore = me.getListsStore();

        me.control({
            '[iconCls=tasks-new-list]': {
                click: me.handleNewListClick
            },
            '[iconCls=tasks-new-folder]': {
                click: me.handleNewFolderClick
            },
            '[iconCls=tasks-delete-list]': {
                click: me.handleDeleteClick
            },
            '[iconCls=tasks-delete-folder]': {
                click: me.handleDeleteClick
            },
            'listTree': {
                afterrender: me.handleAfterListTreeRender,
                edit: me.updateList,
                completeedit: me.handleCompleteEdit,
                canceledit: me.handleCancelEdit,
                deleteclick: me.handleDeleteIconClick,
                selectionchange: me.filterTaskGrid,
                taskdrop: me.updateTaskList,
                listdrop: me.reorderList,
                itemmouseenter: me.showActions,
                itemmouseleave: me.hideActions,
                itemcontextmenu: me.showContextMenu
            }
        });

        if(listsStore.isLoading()) {
            listsStore.on('load', me.handleListsLoad, me);
        } else {
            me.handleListsLoad(listsStore);
        }
    },

    /**
     * Handles a click on the "New List" button or context menu item.
     * @param {Ext.Component} component
     * @param {Ext.EventObject} e
     */
    handleNewListClick: function(component, e) {
        this.addList(true);
    },

    /**
     * Handles a click on the "New Folder" button or context menu item.
     * @param {Ext.Component} component
     * @param {Ext.EventObject} e
     */
    handleNewFolderClick: function(component, e) {
        this.addList();
    },

    /**
     * Adds an empty list to the lists store and starts editing the new list
     * @param {Boolean} leaf    True if the new node should be a leaf node.
     */
    addList: function(leaf) {
        var me = this,
            listTree = me.getListTree(),
            cellEditingPlugin = listTree.cellEditingPlugin,
            selectionModel = listTree.getSelectionModel(),
            selectedList = selectionModel.getSelection()[0],
            parentList = selectedList.isLeaf() ? selectedList.parentNode : selectedList,
            newList = Ext.create('SimpleTasks.model.List', {
                name: 'New ' + (leaf ? 'List' : 'Folder'),
                leaf: leaf,
                loaded: true // set loaded to true, so the tree won't try to dynamically load children for this node when expanded
            }),
            expandAndEdit = function() {
                if(parentList.isExpanded()) {
                    selectionModel.select(newList);
                    me.addedNode = newList;
                    cellEditingPlugin.startEdit(newList, 0);
                } else {
                    listTree.on('afteritemexpand', function startEdit(list) {
                        if(list === parentList) {
                            selectionModel.select(newList);
                            me.addedNode = newList;
                            cellEditingPlugin.startEdit(newList, 0);
                            // remove the afterexpand event listener
                            listTree.un('afteritemexpand', startEdit);
                        }
                    });
                    parentList.expand();
                }
            };
            
        parentList.appendChild(newList);
        listTree.getStore().sync();
        if(listTree.getView().isVisible(true)) {
            expandAndEdit();
        } else {
            listTree.on('expand', function onExpand() {
                expandAndEdit();
                listTree.un('expand', onExpand);
            });
            listTree.expand();
        }
    },


    updateList: function(editor, e) {
        var me = this,
            list = e.record;

        list.save({
            success: function(list, operation) {
                // filter the task list by the currently selected list.  This is necessary for newly added lists
                // since this is the first point at which we have a primary key "id" from the server.
                // If we don't filter here then any new tasks that are added will not appear until the filter is triggered by a selection change.
                me.filterTaskGrid(me.getListTree().getSelectionModel(), [list]);
            },
            failure: function(list, operation) {
                var error = operation.getError(),
                    msg = Ext.isObject(error) ? error.status + ' ' + error.statusText : error;

                Ext.MessageBox.show({
                    title: 'Update List Failed',
                    msg: msg,
                    icon: Ext.Msg.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        });
    },
    

    handleCompleteEdit: function(editor, e){
        delete this.addedNode;
    },


    handleCancelEdit: function(editor, e) {
        var list = e.record,
            parent = list.parentNode,
            added = this.addedNode;

        delete this.addedNode;
        if (added === list) {
            // Only remove it if it's been newly added
            parent.removeChild(list);
            this.getListTree().getStore().sync();
            this.getListTree().getSelectionModel().select([parent]);
        }
    },


    handleDeleteIconClick: function(view, rowIndex, colIndex, column, e) {
        this.deleteList(view.getRecord(view.findTargetByEvent(e)));
    },


    handleDeleteClick: function(component, e) {
        this.deleteList(this.getListTree().getSelectionModel().getSelection()[0]);
    },


    deleteList: function(list) {
        var me = this,
            listTree = me.getListTree(),
            listName = list.get('name'),
            selModel = listTree.getSelectionModel(),
            tasksStore = me.getTasksStore(),
            listsStore = me.getListsStore(),
            tasks;

        Ext.Msg.show({
            title: 'Delete List?',
            msg: 'Are you sure you want to permanently delete the "' + listName + '" list and all its tasks?',
            buttons: Ext.Msg.YESNO,
            fn: function(response) {
                if(response === 'yes') {
                    // recursively remove any tasks from the store that are associated with the list being deleted or any of its children.
                    (function deleteTasks(list) {
                        tasks = tasksStore.queryBy(function(task, id) {
                            return task.get('list_id') === list.get('id');
                        });
                        tasksStore.remove(tasks.getRange(0, tasks.getCount()));

                        list.eachChild(function(child) {
                            deleteTasks(child);
                        });
                    })(list);

                    // destroy the tree node on the server
                    list.parentNode.removeChild(list);
                    listsStore.sync({
                        failure: function(batch, options) {
                            var error = batch.exceptions[0].getError(),
                                msg = Ext.isObject(error) ? error.status + ' ' + error.statusText : error;

                            Ext.MessageBox.show({
                                title: 'Delete List Failed',
                                msg: msg,
                                icon: Ext.Msg.ERROR,
                                buttons: Ext.Msg.OK
                            });
                        }
                    });

                    // If there is no selection, or the selection no longer exists in the store (it was part of the deleted node(s))
                    // then select the "All Lists" root
                    if (!selModel.hasSelection() || !listsStore.getNodeById(selModel.getSelection()[0].getId())) {
                        selModel.select(0);
                    }
                    
                    // refresh the list view so the task counts will be accurate
                    listTree.refreshView();
                }
            }
        });

    },


    filterTaskGrid: function(selModel, lists) {
        if (lists.length === 0) {
            return;
        }
        
        var list = lists[0],
            tasksStore = this.getTasksStore(),
            listIds = [],
            deleteListBtn = Ext.getCmp('delete-list-btn'),
            deleteFolderBtn = Ext.getCmp('delete-folder-btn'),
            i = 0;

        // build an array of all the list_id's in the hierarchy of the selected list
        list.cascadeBy(function(list) {
            listIds.push(list.get('id'));
        });

        tasksStore.addFilter({
            property: "list_id",
            value: new RegExp('^' + listIds.join('$|^') + '$')
        });

        // set the center panel's title to the name of the currently selected list
        this.getTaskGrid().setTitle(list.get('name'));

        // enable or disable the "delete list" and "delete folder" buttons depending on what type of node is selected
        if(list.get('id') === 0) {
            deleteListBtn.disable();
            deleteFolderBtn.disable();
        } else if(list.isLeaf()) {
            deleteListBtn.enable();
            deleteFolderBtn.disable();
        } else {
            deleteListBtn.disable();
            deleteFolderBtn.enable();
        }

        // make the currently selected list the default value for the list field on the new task form
        this.getTaskForm().query('[name=list_id]')[0].setValue(list.get('id'));
    },


    updateTaskList: function(task, list) {
        var me = this,
            listId = list.get('id');

        // set the tasks list_id field to the id of the list it was dropped on
        task.set('list_id', listId);
        // save the task to the server
        task.save({
            success: function(task, operation) {
                // refresh the lists view so the task counts will be updated.
                me.getListTree().refreshView();
            },
            failure: function(task, operation) {
                var error = operation.getError(),
                    msg = Ext.isObject(error) ? error.status + ' ' + error.statusText : error;

                Ext.MessageBox.show({
                    title: 'Move Task Failed',
                    msg: msg,
                    icon: Ext.Msg.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        });
    },


    reorderList: function(list, overList, position) {
        var listsStore = this.getListsStore();
        listsStore.sync();
        // refresh the lists view so the task counts will be updated.
        this.getListTree().refreshView();
    },


    handleTasksLoad: function(tasksStore, tasks, success, operation) {
        var me = this,
            listTree = me.getListTree(),
            selectionModel = listTree.getSelectionModel();

        // refresh the lists view so the task counts will be updated.
        listTree.refreshView();
        // filter the task grid by the selected list
        me.filterTaskGrid(selectionModel, selectionModel.getSelection());
        // remove the event listener after the first run
        tasksStore.un('load', this.handleTasksLoad, this);
    },


    handleListsLoad: function(listsStore, lists, success, operation) {
        var me = this,
            listTree = me.getListTree(),
            tasksStore = me.getTasksStore();
        
        if(listTree) {
            // if the list tree exists when the lists store is first loaded, select the root node.
            // when using a server proxy, the list tree will always exist at this point since asyncronous loading of data allows time for the list tree to be created and rendered.
            // when using a local storage proxy, the list tree will not yet exist at this point, so we'll have to select the root node on render instead (see handleAfterListTreeRender)
            listTree.getSelectionModel().select(0);
        }
        // wait until lists are done loading to load tasks since the task grid's "list" column renderer depends on lists store being loaded
        me.getTasksStore().load();
        // if the tasks store is asynchronous (server proxy) attach load handler for refreshing the list counts after loading is complete
        // if local storage is being used, isLoading will be false here since load() will run syncronously, so there is no need
        // to refresh the lists view because load will have happened before the list tree is even rendered
        if(tasksStore.isLoading()) {
            tasksStore.on('load', me.handleTasksLoad, me);
        }
        // remove the event listener after the first run
        listsStore.un('load', me.handleListsLoad, me);
    },


    handleAfterListTreeRender: function(listTree) {
        listTree.getSelectionModel().select(0);
    },

    showActions: function(view, list, node, rowIndex, e) {
        var icons = Ext.fly(node).query('.x-action-col-icon');
        if(view.getRecord(node).get('id') > 0) {
            Ext.each(icons, function(icon){
                Ext.get(icon).removeCls('x-hidden');
            });
        }
    },


    hideActions: function(view, list, node, rowIndex, e) {
        var icons = Ext.fly(node).query('.x-action-col-icon');
        Ext.each(icons, function(icon){
            Ext.get(icon).addCls('x-hidden');
        });
    },


    showContextMenu: function(view, list, node, rowIndex, e) {
        var contextMenu = this.getContextMenu(),
            newListItem = Ext.getCmp('new-list-item'),
            newFolderItem = Ext.getCmp('new-folder-item'),
            deleteFolderItem = Ext.getCmp('delete-folder-item'),
            deleteListItem = Ext.getCmp('delete-list-item');

        if(list.isLeaf()) {
            newListItem.hide();
            newFolderItem.hide();
            deleteFolderItem.hide();
            deleteListItem.show();
        } else {
            newListItem.show();
            newFolderItem.show();
            if(list.isRoot()) {
                deleteFolderItem.hide();
            } else {
                deleteFolderItem.show();
            }
            deleteListItem.hide();
        }
        contextMenu.setList(list);
        contextMenu.showAt(e.getX(), e.getY());
        e.preventDefault();
    }

});

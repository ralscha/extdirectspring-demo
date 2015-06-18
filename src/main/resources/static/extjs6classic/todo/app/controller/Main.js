Ext.define('Todo.controller.Main', {
    extend: 'Ext.app.Controller',

    refs: [
        {
            ref: 'editor',
            selector: 'grid-actions #todoForm'
        },
        {
            ref:'todoGrid',
            selector:'grid-actions #todoGrid'
        }
    ],

    init: function(application) {
        this.control({
            "grid-actions #todoGrid": {
                itemclick: this.onTodoGridItemClick
            },

            'grid-actions button': { 
                click: this.buttonActions
            },

            'grid-actions #todoGrid toolbar #filter': {
                reset: function(){ 
                    Ext.getStore('Todo').clearFilter();
                }
            }
        });
    },

    buttonActions: function(button, e, eOpts){
        switch(button.action){
            case 'insertRecord': this.onInsertBtnClick(); break;
            case 'updateRecord': this.onUpdateBtnClick(); break;
            case 'removeRecord': this.onRemoveBtnClick(); break;
            case 'loadStore': this.loadStore(); break;
            case 'filterStore': this.filterStore(); break;
            default: break;
        }
    },

    loadStore:function(){
        this.getTodoGrid().getStore().reload();
    },

    filterStore: function(){
        var field = this.getTodoGrid().down('toolbar #filter'),
            value = field.getValue(),
            store = Ext.getStore('Todo');

        if(value){
            store.filter('text', value);  
        }
    },

    onTodoGridItemClick: function(dataview, record, item, index, e, eOpts) {
        var form = this.getEditor();
        form.getForm().loadRecord(record);
        form.enable();
    },

    onInsertBtnClick: function() {
        var store = Ext.getStore('Todo');
        var record = Ext.create('Todo.model.TodoItem', {text:'New todo action ' + (store.getCount() +1), complete:0});
        record.save({
            callback:function(records, operation, success){
                if(success){
                    Ext.getStore('Todo').add(records);
                }else{
                    console.log('Failure to add record: ', arguments);
                }
            }
        });
    },



    onUpdateBtnClick: function() {
        if(this.missingSelection()){
            return false;
        }

        var form = this.getEditor().getForm();

        if (form.isValid()) {
            var record = form.getRecord();
            form.updateRecord(record);

            record.save({
                success: function(record, operation) {
                    record.commit();
                    console.log('success', record, operation);
                    form.loadRecord(record);
                },
                failure: function(record, operation) {
                    var exception = operation.getError();
                    if (exception && exception.errors) {
                    	form.markInvalid(exception.errors);
                    }
                    console.log('failure', record, operation, exception);
                },
                scope: this
            });
        }
    },

    missingSelection: function(){
        return this.getTodoGrid().getSelectionModel().getSelection().length === 0;
    }
});

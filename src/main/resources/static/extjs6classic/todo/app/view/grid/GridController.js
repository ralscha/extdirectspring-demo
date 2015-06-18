Ext.define('Todo.view.grid.GridController', {
	extend: 'Ext.app.ViewController',

	reloadData: function() {
		this.getStore('todos').reload();
	},

	onTodoGridItemClick: function(dataview, record, item, index, e, eOpts) {
		this.getViewModel().set('editTodo', record);
	},

	onFilterFieldKey: function(f, e) {
		if (e.getKey() === e.ENTER) {
			this.doFilter();
		}
	},

	doFilter: function() {
		var value = this.lookupReference('filterField').getValue();
		var store = this.getStore('todos');
		if (value) {
			store.filter('filter', value);
			this.lookupReference('todoGrid').setSelection(null);
			this.getViewModel().set('editTodo', false);
		}
		else {
			store.clearFilter();
		}
	},

	onClearTrigger: function(trigger) {
		trigger.reset();
		this.getStore('todos').clearFilter();
	},

	insertTodo: function() {
		var store = this.getStore('todos');
		var record = Ext.create('Todo.model.TodoItem', {
			text: 'New todo action ' + (store.getCount() + 1),
			complete: false
		});
		this.getViewModel().set('editTodo', record);
	},

	updateTodo: function() {
		var editTodo = this.getViewModel().get('editTodo');
		if (editTodo.phantom) {
			editTodo.save({
				callback: function() {
					this.getStore('todos').insert(0, editTodo);
				},
				scope: this
			});
		}
		else {
			editTodo.save();
		}
	},

	destroyTodo: function() {

		var selectedTodo = this.getViewModel().get('selectedTodo');
		var myStore = this.getStore('todos');
		myStore.remove(selectedTodo);
		this.getViewModel().set('editTodo', false);

		selectedTodo.erase({
			callback: function(records, operation) {
				var success = operation.wasSuccessful();
				if (success) {
					console.log('Sucessfully removed record: ', arguments);
				}
				else {
					myStore.insert(selectedTodo.index, selectedTodo);
					console.log('Failure to remove record: ', arguments);
					Ext.Msg.alert('Server side Error', 'Unable to remove the record');
				}
			}
		});
	}

});
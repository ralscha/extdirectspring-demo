Ext.define('Task.view.TaskController', {
	extend: 'Ext.app.ViewController',

	onGridItemClick: function(grid, record) {
		taskService.readOne(record.id, function(allDetailRecord) {
			this.getViewModel().set('detailTask', new Task.model.Task(allDetailRecord));
		}, this);
	},

	addTask: function() {
		this.getViewModel().set('detailTask', new Task.model.Task({
			dueDate: new Date()
		}));
		var taskForm = new Task.view.TaskForm();
		this.getView().add(taskForm);
		taskForm.show();
	},

	editTask: function() {
		var taskForm = new Task.view.TaskForm();
		this.getView().add(taskForm);
		taskForm.show();
	},

	saveTask: function(btn) {
		var detailTask = this.getViewModel().get('detailTask');
		var tasks = this.getStore('tasks');

		if (detailTask.phantom) {
			detailTask.save({
				callback: function() {
					tasks.insert(0, detailTask);
				}
			});
		}
		else {
			detailTask.save({
				callback: function() {
					var storeRecord = tasks.getById(detailTask.getId());
					storeRecord.set(detailTask.data);
					storeRecord.commit();
				}
			});
		}

		btn.up('window').destroy();
	},

	cancelTask: function(btn) {
		btn.up('window').destroy();
	},

	deleteTask: function() {
		Ext.Msg.confirm('Confirm', 'Are you sure you want to delete this task?', this.deleteTaskConfirm, this);
	},

	deleteTaskConfirm: function(btn) {
		var me = this;
		if (btn === 'yes') {
			var store = me.getStore('tasks');
			var record = me.getViewModel().get('selectedTask');
			var index = store.indexOf(record);

			record.erase({
				callback: function(records, operation) {
					var success = operation.wasSuccessful();
					me.getViewModel().set('detailTask', false);
					if (success) {
						console.log('Sucessfully removed record: ', arguments);
						Ext.Msg.alert('Success', 'Record removed sucessfully');
					}
					else {
						store.insert(index, record);
						console.log('Failure to remove record: ', arguments);
						Ext.Msg.alert('Server side Error', 'Unable to remove the record');
					}
				}
			});
		}
	}

});

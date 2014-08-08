/**
 * Copyright 2010-2014 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.extdirectspring.demo.simpletask;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class SimpleTaskService {

	private final Map<Integer, Task> DB = new ConcurrentHashMap<>();
	private final  AtomicInteger MAX_ID = new AtomicInteger(1);

	private String defaultReminderTime = "8:00 AM";
	
	@ExtDirectMethod(group = "simple-task")
	public void saveDefaultReminderTime(String value) {
		defaultReminderTime = value;
	}
	
	@ExtDirectMethod(group = "simple-task")
	public String readDefaultReminderTime() {
		return defaultReminderTime;
	}	
	
	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "simple-task")
	public Collection<Task> read() {
		return DB.values();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simple-task")
	public List<Task> create(List<Task> newTasks) {
		newTasks.stream().forEach(newTask -> {
			newTask.setId(MAX_ID.getAndIncrement());
			DB.put(newTask.getId(), newTask);
		});
		return newTasks;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simple-task")
	public List<Task> update(List<Task> updatedTasks) {
		updatedTasks.stream().forEach(updatedTask -> {
			Task li = DB.get(updatedTask.getId());
			if (updatedTask.getTitle() != null) {
				li.setTitle(updatedTask.getTitle());
			}
			if (updatedTask.getNote() != null) {
				li.setNote(updatedTask.getNote());
			}
			if (updatedTask.getDone() != null) {
				li.setDone(updatedTask.getDone());
			}
			if (updatedTask.getDue() != null) {
				li.setDue(updatedTask.getDue());
			}
			if (updatedTask.getReminder() != null) {
				li.setReminder(updatedTask.getReminder());
			}
			if (updatedTask.getList_id() != null) {
				li.setList_id(updatedTask.getList_id());
			}
		});
		return updatedTasks;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simple-task")
	public void destroy(List<Task> destroyedTasks) {
		destroyedTasks.stream().map(Task::getId).forEach(DB::remove);
	}

}

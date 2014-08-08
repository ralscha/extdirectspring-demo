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
package ch.rasc.extdirectspring.demo.task;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;

@Service
public class TaskService {

	private final static LongAdder maxId = new LongAdder();

	private final static Map<Long, Task> db = new ConcurrentHashMap<>();
	static {
		db.put(1L,
				new Task(
						1,
						"Task 1",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
						LocalDate.parse("2013-12-12"), Priority.HIGH));
		db.put(2L,
				new Task(
						2,
						"Task 2",
						"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
						LocalDate.parse("2013-12-13"), Priority.HIGH));
		db.put(3L,
				new Task(
						3,
						"Task 3",
						"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.",
						LocalDate.parse("2013-12-14"), Priority.HIGH));
		db.put(4L,
				new Task(
						4,
						"Task 4",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
						LocalDate.parse("2013-12-11"), Priority.NORMAL));
		db.put(5L,
				new Task(
						5,
						"Task 5",
						"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
						LocalDate.parse("2013-12-10"), Priority.NORMAL));
		db.put(6L,
				new Task(
						6,
						"Task 6",
						"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.",
						LocalDate.parse("2013-12-23"), Priority.NORMAL));
		db.put(7L,
				new Task(
						7,
						"Task 7",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
						LocalDate.parse("2013-12-01"), Priority.NORMAL));
		db.put(8L,
				new Task(
						8,
						"Task 8",
						"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
						LocalDate.parse("2013-12-27"), Priority.LOW));
		db.put(9L,
				new Task(
						9,
						"Task 9",
						"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.",
						LocalDate.parse("2013-12-08"), Priority.LOW));
		db.put(10L,
				new Task(
						10,
						"Task 10",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
						LocalDate.parse("2013-12-25"), Priority.LOW));
		maxId.add(10);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "task",
			jsonView = JsonViews.NO_DETAIL.class)
	public List<Task> read(ExtDirectStoreReadRequest request) {
		Filter filter = request.getFirstFilterForField("filter");

		Stream<Task> stream = db.values().stream();
		if (filter != null) {
			String filterValue = ((StringFilter) filter).getValue();
			stream = stream.filter(t -> t.getDescription().toLowerCase()
					.contains(filterValue.toLowerCase()));
		}
		return stream.sorted(Comparator.comparing(Task::getId).reversed()).collect(
				Collectors.toList());
	}

	@ExtDirectMethod(group = "task", jsonView = JsonViews.ALL.class)
	public Task readOne(long id) {
		return db.get(id);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "task")
	public List<Task> create(List<Task> newTasks) {
		return newTasks.stream().peek(t -> {
			maxId.increment();
			t.setId(maxId.longValue());
			db.put(t.getId(), t);
		}).collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "task")
	public List<Task> update(List<Task> updatedTasks) {
		updatedTasks.forEach(t -> db.put(t.getId(), t));
		return updatedTasks;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "task")
	public void destroy(List<Task> destroyTasks) {
		destroyTasks.forEach(t -> db.remove(t.getId()));
	}

}

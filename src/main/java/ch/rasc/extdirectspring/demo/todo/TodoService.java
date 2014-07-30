package ch.rasc.extdirectspring.demo.todo;

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
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;

@Service
public class TodoService {

	private final static LongAdder maxId = new LongAdder();

	private final static Map<Long, TodoItem> db = new ConcurrentHashMap<>();
	static {
		db.put(1L, new TodoItem(1, "Set up alarm to 6am", true));
		db.put(2L, new TodoItem(2, "Get some rest and sleep well", true));
		db.put(3L, new TodoItem(3, "Wake up refreshed", true));
		db.put(4L, new TodoItem(4, "Take shower", true));
		db.put(5L, new TodoItem(5, "Prepare breakfast", false));
		db.put(6L, new TodoItem(6, "Read morning newspapers", false));
		db.put(7L, new TodoItem(7, "Watch Good Morning America", false));
		db.put(8L, new TodoItem(8, "Dress up for the meeting", false));
		db.put(9L, new TodoItem(9, "Check email", false));
		db.put(10L, new TodoItem(10, "Head to the office", false));
		maxId.add(10);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "todo")
	public List<TodoItem> read(ExtDirectStoreReadRequest request) {
		Filter filter = request.getFirstFilterForField("filter");

		Stream<TodoItem> stream = db.values().stream();
		if (filter != null) {
			String filterValue = ((StringFilter) filter).getValue();
			stream = stream.filter(t -> t.getText().toLowerCase()
					.contains(filterValue.toLowerCase()));
		}
		return stream.sorted(Comparator.comparing(TodoItem::getId).reversed()).collect(
				Collectors.toList());

	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "todo")
	public List<TodoItem> create(List<TodoItem> newTodos) {
		return newTodos.stream().peek(t -> {
			maxId.increment();
			t.setId(maxId.longValue());
			db.put(t.getId(), t);
		}).collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "todo")
	public List<TodoItem> update(List<TodoItem> updatedTodos) {
		updatedTodos.forEach(t -> db.put(t.getId(), t));
		return updatedTodos;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "todo")
	public void destroy(List<TodoItem> destroyTodos) {
		destroyTodos.forEach(t -> db.remove(t.getId()));
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_LOAD, group = "todo")
	public UserInfo load(long id) {
		System.out.println("sent parameter: " + id);

		UserInfo ui = new UserInfo();
		ui.setFirstname("John");
		ui.setLastname("Smith");
		ui.setEmail("john.smith@comapny.info");
		return ui;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "todo")
	public ExtDirectFormPostResult submit(UserInfo ui) {

		System.out.println(ui);

		ExtDirectFormPostResult result = new ExtDirectFormPostResult(true);
		result.addResultProperty("msg", "User data updated");
		return result;
	}

}

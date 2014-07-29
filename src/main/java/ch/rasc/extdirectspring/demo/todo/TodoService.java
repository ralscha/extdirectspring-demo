package ch.rasc.extdirectspring.demo.todo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;

@Service
public class TodoService {

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
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "todo")
	public List<TodoItem> read() {
		return db.values().stream()
				.sorted(Comparator.comparing(TodoItem::getId).reversed())
				.collect(Collectors.toList());
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

package ch.ralscha.extdirectspring.demo.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.google.common.collect.ImmutableList;

@Service
public class CalendarService {

	@Autowired
	private EventDb eventDb;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "calendar")
	public ImmutableList<Event> read() {
		return eventDb.getAll();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "calendar")
	public ImmutableList<Event> create(List<Event> events) {
		ImmutableList.Builder<Event> result = ImmutableList.builder();

		for (Event event : events) {
			result.add(eventDb.insert(event));
		}

		return result.build();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "calendar")
	public ImmutableList<Event> update(List<Event> events) {
		ImmutableList.Builder<Event> result = ImmutableList.builder();

		for (Event event : events) {
			result.add(eventDb.update(event));
		}

		return result.build();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "calendar")
	public void destroy(List<Event> events) {
		for (Event event : events) {
			eventDb.delete(event);
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "calendar")
	public ImmutableList<Calendar> readCalendars() {

		ImmutableList.Builder<Calendar> builder = ImmutableList.builder();

		builder.add(new Calendar(1, "Home", 2));
		builder.add(new Calendar(2, "Work", 22));
		builder.add(new Calendar(3, "School", 7));

		return builder.build();
	}

}

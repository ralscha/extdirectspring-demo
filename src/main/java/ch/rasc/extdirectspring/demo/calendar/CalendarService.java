/**
 * Copyright 2010-2017 the original author or authors.
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
package ch.rasc.extdirectspring.demo.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class CalendarService {

	@Autowired
	private EventDb eventDb;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "calendar")
	public Collection<Event> read(
			@RequestParam(required = false) @DateTimeFormat(
					pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(
					pattern = "yyyy-MM-dd") LocalDate endDate) {

		return this.eventDb.getEvents(startDate, endDate);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "calendar")
	public List<Event> create(List<Event> events) {
		return events.stream().map(e -> this.eventDb.insert(e))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "calendar")
	public List<Event> update(List<Event> events) {
		return events.stream().map(e -> this.eventDb.update(e))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "calendar")
	public void destroy(List<Event> events) {
		for (Event event : events) {
			this.eventDb.delete(event);
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "calendar")
	public List<Calendar> readCalendars() {

		List<Calendar> builder = new ArrayList<>();

		builder.add(new Calendar(1, "Home", 2));
		builder.add(new Calendar(2, "Work", 22));
		builder.add(new Calendar(3, "School", 7));

		return Collections.unmodifiableList(builder);
	}

}

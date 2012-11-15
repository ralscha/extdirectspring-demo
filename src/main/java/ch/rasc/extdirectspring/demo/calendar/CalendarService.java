/**
 * Copyright 2010-2012 Ralph Schaer <ralphschaer@gmail.com>
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

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.google.common.collect.ImmutableList;

@Service
public class CalendarService {

	@Autowired
	private EventDb eventDb;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "calendar")
	public ImmutableList<Event> read(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") DateTime startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") DateTime endDate) {

		return eventDb.getEvents(startDate, endDate);
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

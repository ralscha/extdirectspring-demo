/**
 * Copyright 2010-2016 Ralph Schaer <ralphschaer@gmail.com>
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class EventDb {

	private AtomicInteger maxId;

	private Map<Integer, Event> events;

	@PostConstruct
	public void populateData() {
		this.events = new ConcurrentHashMap<>();

		Event event = new Event();
		event.setId(1001);
		event.setCalendarId(1);
		event.setTitle("Vacation");
		event.setStartDate(LocalDateTime.now().minusDays(20).withHour(10).withMinute(0)
				.withSecond(0));
		event.setEndDate(LocalDateTime.now().minusDays(10).withHour(15).withMinute(0)
				.withSecond(0));
		event.setNotes("Have fun");
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1002);
		event.setCalendarId(2);
		event.setTitle("Lunch with Matt");
		event.setStartDate(LocalDateTime.now().withHour(11).withMinute(30).withSecond(0));
		event.setEndDate(LocalDateTime.now().withHour(13).withMinute(0).withSecond(0));
		event.setLocation("Chuy's!");
		event.setUrl("http://chuys.com");
		event.setNotes("Order the queso");
		event.setReminder("15");
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1003);
		event.setCalendarId(3);
		event.setTitle("Project due");
		event.setStartDate(LocalDateTime.now().withHour(15).withMinute(0).withSecond(0));
		event.setEndDate(LocalDateTime.now().withHour(15).withMinute(0).withSecond(0));
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1004);
		event.setCalendarId(1);
		event.setTitle("Sarah's birthday");
		event.setStartDate(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
		event.setEndDate(event.getStartDate());
		event.setNotes("Need to get a gift");
		event.setAllDay(true);
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1005);
		event.setCalendarId(2);
		event.setTitle("A long one...");
		event.setStartDate(LocalDateTime.now().minusDays(12).withHour(0).withMinute(0)
				.withSecond(0));
		event.setEndDate(LocalDateTime.now().plusDays(10).withHour(0).withMinute(0)
				.minusSeconds(0));
		event.setAllDay(true);
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1006);
		event.setCalendarId(3);
		event.setTitle("School holiday");
		event.setStartDate(
				LocalDateTime.now().plusDays(5).withHour(0).withMinute(0).withSecond(0));
		event.setEndDate(LocalDateTime.now().plusDays(7).withHour(0).withMinute(0)
				.minusSeconds(0));
		event.setAllDay(true);
		event.setReminder("2880");
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1007);
		event.setCalendarId(1);
		event.setTitle("Haircut");
		event.setStartDate(LocalDateTime.now().withHour(9).withMinute(0).withSecond(0));
		event.setEndDate(LocalDateTime.now().withHour(9).withMinute(30).minusSeconds(0));
		event.setNotes("Get cash on the way");
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1008);
		event.setCalendarId(3);
		event.setTitle("An old event");
		event.setStartDate(LocalDateTime.now().minusDays(30).withHour(0).withMinute(0)
				.withSecond(0));
		event.setEndDate(LocalDateTime.now().minusDays(28).withHour(0).withMinute(0)
				.minusSeconds(0));
		event.setAllDay(true);
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1009);
		event.setCalendarId(2);
		event.setTitle("Board meeting");
		event.setStartDate(LocalDateTime.now().minusDays(2).withHour(13).withMinute(0)
				.withSecond(0));
		event.setEndDate(LocalDateTime.now().minusDays(2).withHour(18).withMinute(0)
				.minusSeconds(0));
		event.setLocation("ABC Inc.");
		event.setReminder("60");
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1010);
		event.setCalendarId(3);
		event.setTitle("Jenny's final exams");
		event.setStartDate(
				LocalDateTime.now().minusDays(2).withHour(0).withMinute(0).withSecond(0));
		event.setEndDate(LocalDateTime.now().plusDays(3).withHour(0).withMinute(0)
				.minusSeconds(0));
		event.setAllDay(true);
		this.events.put(event.getId(), event);

		event = new Event();
		event.setId(1011);
		event.setCalendarId(1);
		event.setTitle("Movie night");
		event.setStartDate(
				LocalDateTime.now().plusDays(2).withHour(19).withMinute(0).withSecond(0));
		event.setEndDate(LocalDateTime.now().plusDays(3).withHour(23).withMinute(0)
				.minusSeconds(0));
		event.setNotes("Don't forget the tickets!");
		event.setReminder("60");
		this.events.put(event.getId(), event);

		this.maxId = new AtomicInteger(1011);
	}

	public Collection<Event> getEvents(LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			List<Event> foundEvents = new ArrayList<>();
			for (Event event : this.events.values()) {
				if (isOverlapping(startDate, endDate, event.getStartDate().toLocalDate(),
						event.getEndDate().toLocalDate())) {
					foundEvents.add(event);
				}
			}
			return Collections.unmodifiableList(foundEvents);
		}
		return Collections.unmodifiableCollection(this.events.values());
	}

	private static boolean isOverlapping(LocalDate start1, LocalDate end1,
			LocalDate start2, LocalDate end2) {
		return start1.isBefore(end2) && start2.isBefore(end1);
	}

	public Event update(Event event) {
		event.trimToNull();
		this.events.put(event.getId(), event);
		return event;
	}

	public void delete(Event event) {
		this.events.remove(event.getId());
	}

	public Event insert(Event event) {
		event.trimToNull();
		event.setId(this.maxId.incrementAndGet());
		this.events.put(event.getId(), event);
		return event;
	}

}

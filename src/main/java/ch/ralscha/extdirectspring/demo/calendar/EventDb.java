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
package ch.ralscha.extdirectspring.demo.calendar;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

@Service
public class EventDb {

	private AtomicInteger maxId;

	private Map<Integer, Event> events;

	@PostConstruct
	public void populateData() {
		events = Maps.newConcurrentMap();

		Event event = new Event();
		event.setId(1001);
		event.setCalendarId(1);
		event.setTitle("Vacation");
		event.setStartDate(DateTime.now().minusDays(20).withHourOfDay(10).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().minusDays(10).withHourOfDay(15).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setNotes("Have fun");
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1002);
		event.setCalendarId(2);
		event.setTitle("Lunch with Matt");
		event.setStartDate(DateTime.now().withHourOfDay(11).withMinuteOfHour(30).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().withHourOfDay(13).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setLocation("Chuy's!");
		event.setUrl("http://chuys.com");
		event.setNotes("Order the queso");
		event.setReminder("15");
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1003);
		event.setCalendarId(3);
		event.setTitle("Project due");
		event.setStartDate(DateTime.now().withHourOfDay(15).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().withHourOfDay(15).withMinuteOfHour(0).withSecondOfMinute(0));
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1004);
		event.setCalendarId(1);
		event.setTitle("Sarah's birthday");
		event.setStartDate(DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(event.getStartDate());
		event.setNotes("Need to get a gift");
		event.setAllDay(true);
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1005);
		event.setCalendarId(2);
		event.setTitle("A long one...");
		event.setStartDate(DateTime.now().minusDays(12).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().plusDays(10).withHourOfDay(0).withMinuteOfHour(0).minusSeconds(0));
		event.setAllDay(true);
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1006);
		event.setCalendarId(3);
		event.setTitle("School holiday");
		event.setStartDate(DateTime.now().plusDays(5).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().plusDays(7).withHourOfDay(0).withMinuteOfHour(0).minusSeconds(0));
		event.setAllDay(true);
		event.setReminder("2880");
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1007);
		event.setCalendarId(1);
		event.setTitle("Haircut");
		event.setStartDate(DateTime.now().withHourOfDay(9).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().withHourOfDay(9).withMinuteOfHour(30).minusSeconds(0));
		event.setNotes("Get cash on the way");
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1008);
		event.setCalendarId(3);
		event.setTitle("An old event");
		event.setStartDate(DateTime.now().minusDays(30).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().minusDays(28).withHourOfDay(0).withMinuteOfHour(0).minusSeconds(0));
		event.setAllDay(true);
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1009);
		event.setCalendarId(2);
		event.setTitle("Board meeting");
		event.setStartDate(DateTime.now().minusDays(2).withHourOfDay(13).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().minusDays(2).withHourOfDay(18).withMinuteOfHour(0).minusSeconds(0));
		event.setLocation("ABC Inc.");
		event.setReminder("60");
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1010);
		event.setCalendarId(3);
		event.setTitle("Jenny's final exams");
		event.setStartDate(DateTime.now().minusDays(2).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().plusDays(3).withHourOfDay(0).withMinuteOfHour(0).minusSeconds(0));
		event.setAllDay(true);
		events.put(event.getId(), event);

		event = new Event();
		event.setId(1011);
		event.setCalendarId(1);
		event.setTitle("Movie night");
		event.setStartDate(DateTime.now().plusDays(2).withHourOfDay(19).withMinuteOfHour(0).withSecondOfMinute(0));
		event.setEndDate(DateTime.now().plusDays(3).withHourOfDay(23).withMinuteOfHour(0).minusSeconds(0));
		event.setNotes("Don't forget the tickets!");
		event.setReminder("60");
		events.put(event.getId(), event);

		maxId = new AtomicInteger(1011);
	}

	public ImmutableList<Event> getEvents(DateTime startDate, DateTime endDate) {
		if (startDate != null && endDate != null) {
			Interval interval = new Interval(startDate, endDate.plusDays(1));
			ImmutableList.Builder<Event> foundEvents = ImmutableList.builder();
			for (Event event : events.values()) {
				if (interval.overlaps(new Interval(event.getStartDate(), event.getEndDate().plusDays(1)
						.withTimeAtStartOfDay()))) {
					foundEvents.add(event);
				}
			}
			return foundEvents.build();
		}
		return ImmutableList.copyOf(events.values());
	}

	public Event update(Event event) {
		event.trimToNull();
		events.put(event.getId(), event);
		return event;
	}

	public void delete(Event event) {
		events.remove(event.getId());
	}

	public Event insert(Event event) {
		event.trimToNull();
		event.setId(maxId.incrementAndGet());
		events.put(event.getId(), event);
		return event;
	}

}

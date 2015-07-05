/**
 * Copyright 2010-2015 Ralph Schaer <ralphschaer@gmail.com>
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

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeSerializer;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

	private int id;

	private int calendarId;

	private String title;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private String location;

	private String notes;

	private String url;

	private String recurRule;

	private boolean allDay;

	private String reminder;

	public void trimToNull() {
		this.title = trimToNull(this.title);
		this.location = trimToNull(this.location);
		this.notes = trimToNull(this.notes);
		this.url = trimToNull(this.url);
		this.recurRule = trimToNull(this.recurRule);
		this.reminder = trimToNull(this.reminder);
	}

	private final static String trimToNull(String s) {
		String cs = s;
		if (cs != null) {
			cs = cs.trim();
			if (cs.isEmpty()) {
				cs = null;
			}
		}
		return cs;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCalendarId() {
		return this.calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	public LocalDateTime getStartDate() {
		return this.startDate;
	}

	@JsonDeserialize(using = ISO8601LocalDateTimeDeserializer.class)
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	public LocalDateTime getEndDate() {
		return this.endDate;
	}

	@JsonDeserialize(using = ISO8601LocalDateTimeDeserializer.class)
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isAllDay() {
		return this.allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public String getReminder() {
		return this.reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getRecurRule() {
		return this.recurRule;
	}

	public void setRecurRule(String recurRule) {
		this.recurRule = recurRule;
	}

	@Override
	public String toString() {
		return "Event [id=" + this.id + ", calendarId=" + this.calendarId + ", title="
				+ this.title + ", startDate=" + this.startDate + ", endDate="
				+ this.endDate + ", location=" + this.location + ", notes=" + this.notes
				+ ", url=" + this.url + ", recurRule=" + this.recurRule + ", allDay="
				+ this.allDay + ", reminder=" + this.reminder + "]";
	}

}

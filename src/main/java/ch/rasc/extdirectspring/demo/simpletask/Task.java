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
package ch.rasc.extdirectspring.demo.simpletask;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;
import ch.rasc.extdirectspring.demo.util.ISO8601ZonedDateTimeDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601ZonedDateTimeSerializer;

@JsonInclude(Include.NON_NULL)
public class Task {
	private int id;
	private String title;
	private Integer list_id;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	private LocalDate due;

	@JsonSerialize(using = ISO8601ZonedDateTimeSerializer.class)
	@JsonDeserialize(using = ISO8601ZonedDateTimeDeserializer.class)
	private ZonedDateTime reminder;

	private Boolean done;
	private String note;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getList_id() {
		return this.list_id;
	}

	public void setList_id(Integer list_id) {
		this.list_id = list_id;
	}

	public LocalDate getDue() {
		return this.due;
	}

	public void setDue(LocalDate due) {
		this.due = due;
	}

	public ZonedDateTime getReminder() {
		return this.reminder;
	}

	public void setReminder(ZonedDateTime reminder) {
		this.reminder = reminder;
	}

	public Boolean getDone() {
		return this.done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}

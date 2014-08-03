package ch.rasc.extdirectspring.demo.simpletask;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;
import ch.rasc.extdirectspring.demo.util.ISO8601ZonedDateTimeDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601ZonedDateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getList_id() {
		return list_id;
	}

	public void setList_id(Integer list_id) {
		this.list_id = list_id;
	}

	public LocalDate getDue() {
		return due;
	}

	public void setDue(LocalDate due) {
		this.due = due;
	}

	public ZonedDateTime getReminder() {
		return reminder;
	}

	public void setReminder(ZonedDateTime reminder) {
		this.reminder = reminder;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}

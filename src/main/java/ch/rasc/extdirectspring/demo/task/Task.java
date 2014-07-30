package ch.rasc.extdirectspring.demo.task;

import java.time.LocalDate;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Task {
	private long id;

	private String title;

	@JsonView(JsonViews.ALL.class)
	private String description;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	private LocalDate dueDate;

	private Priority priority;

	public Task() {
		// default constructor
	}

	public Task(long id, String title, String description, LocalDate dueDate,
			Priority priority) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}

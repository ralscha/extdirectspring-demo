package ch.ralscha.extdirectspring.demo.bancha;

import org.joda.time.DateTime;

import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeDeserializer;
import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Article {

	private int id;

	private String title;

	private String body;

	private DateTime date;

	private boolean published;

	private int userId;

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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getDate() {
		return date;
	}

	@JsonDeserialize(using = ISO8601DateTimeDeserializer.class)
	public void setDate(DateTime date) {
		this.date = date;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}

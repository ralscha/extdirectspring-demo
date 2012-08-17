package ch.ralscha.extdirectspring.demo.calendar;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeDeserializer;
import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(Include.NON_NULL)
public class Event {

	private int id;

	private int calendarId;

	private String title;

	private DateTime startDate;

	private DateTime endDate;

	private String location;

	private String notes;

	private String url;

	private String recurRule;

	private boolean allDay;

	private String reminder;

	public void trimToNull() {
		StringUtils.trimToNull(title);
		StringUtils.trimToNull(location);
		StringUtils.trimToNull(notes);
		StringUtils.trimToNull(url);
		StringUtils.trimToNull(recurRule);
		StringUtils.trimToNull(reminder);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getStartDate() {
		return startDate;
	}

	@JsonDeserialize(using = ISO8601DateTimeDeserializer.class)
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getEndDate() {
		return endDate;
	}

	@JsonDeserialize(using = ISO8601DateTimeDeserializer.class)
	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getRecurRule() {
		return recurRule;
	}

	public void setRecurRule(String recurRule) {
		this.recurRule = recurRule;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", calendarId=" + calendarId + ", title=" + title + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", location=" + location + ", notes=" + notes + ", url=" + url
				+ ", recurRule=" + recurRule + ", allDay=" + allDay + ", reminder=" + reminder + "]";
	}

}

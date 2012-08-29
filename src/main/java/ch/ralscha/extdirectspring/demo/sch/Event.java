package ch.ralscha.extdirectspring.demo.sch;

import org.joda.time.DateTime;

import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeDeserializer;
import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties({ "id", "Cls" })
public class Event {

	@JsonProperty("Id")
	private String id;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("StartDate")
	private DateTime startDate;

	@JsonProperty("EndDate")
	private DateTime endDate;

	@JsonProperty("ResourceId")
	private String resourceId;

	private boolean resizable;

	private boolean draggable;

	private String cls;

	private String text;

	private int duration;

	private String synopsis;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", resourceId=" + resourceId + ", resizable=" + resizable + ", draggable=" + draggable + ", cls="
				+ cls + ", text=" + text + ", duration=" + duration + ", synopsis=" + synopsis + "]";
	}

}

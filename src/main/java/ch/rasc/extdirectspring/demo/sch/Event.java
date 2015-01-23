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
package ch.rasc.extdirectspring.demo.sch;

import java.time.LocalDateTime;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeSerializer;

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
	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateTimeDeserializer.class)
	private LocalDateTime startDate;

	@JsonProperty("EndDate")
	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateTimeDeserializer.class)
	private LocalDateTime endDate;

	@JsonProperty("ResourceId")
	private String resourceId;

	private boolean resizable;

	private boolean draggable;

	private String cls;

	private String text;

	private int duration;

	private String synopsis;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStartDate() {
		return this.startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return this.endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public boolean isResizable() {
		return this.resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public boolean isDraggable() {
		return this.draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public String getCls() {
		return this.cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getSynopsis() {
		return this.synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	@Override
	public String toString() {
		return "Event [id=" + this.id + ", name=" + this.name + ", startDate="
				+ this.startDate + ", endDate=" + this.endDate + ", resourceId="
				+ this.resourceId + ", resizable=" + this.resizable + ", draggable="
				+ this.draggable + ", cls=" + this.cls + ", text=" + this.text
				+ ", duration=" + this.duration + ", synopsis=" + this.synopsis + "]";
	}

}

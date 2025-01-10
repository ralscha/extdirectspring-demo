/*
 * Copyright the original author or authors.
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Calendar {

	private final int id;

	private final String title;

	private String description;

	private Integer color;

	private Boolean hidden;

	public Calendar(int id, String title, Integer color) {
		this.id = id;
		this.title = title;
		this.color = color;
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getColor() {
		return this.color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public Boolean isHidden() {
		return this.hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}

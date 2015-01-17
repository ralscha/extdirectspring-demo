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
package ch.rasc.extdirectspring.demo.group;

import java.math.BigDecimal;
import java.time.LocalDate;

import ch.rasc.extdirectspring.demo.util.MDYLocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Task {

	private int projectId;

	private String project;

	private int taskId;

	private String description;

	private BigDecimal estimate;

	private BigDecimal rate;

	private LocalDate due;

	public Task(int projectId, String project, int taskId, String description,
			BigDecimal estimate, BigDecimal rate, int dueYear, int dueMonth, int dueDay) {

		this.projectId = projectId;
		this.project = project;
		this.taskId = taskId;
		this.description = description;
		this.estimate = estimate;
		this.rate = rate;
		this.due = LocalDate.of(dueYear, dueMonth, dueDay);
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getEstimate() {
		return estimate;
	}

	public void setEstimate(BigDecimal estimate) {
		this.estimate = estimate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@JsonSerialize(using = MDYLocalDateSerializer.class)
	public LocalDate getDue() {
		return due;
	}

	public void setDue(LocalDate due) {
		this.due = due;
	}

}

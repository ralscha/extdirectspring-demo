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
package ch.rasc.extdirectspring.demo.chart;

import java.io.Serializable;
import java.time.LocalDate;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SiteInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private final LocalDate date;

	private final int visits;

	private final int views;

	private final int veins;

	public SiteInfo(LocalDate date, int visits, int views, int veins) {
		this.date = date;
		this.visits = visits;
		this.views = views;
		this.veins = veins;
	}

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	public LocalDate getDate() {
		return date;
	}

	public int getVisits() {
		return visits;
	}

	public int getViews() {
		return views;
	}

	public int getVeins() {
		return veins;
	}

}

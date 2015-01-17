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
package ch.rasc.extdirectspring.demo.pivot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Economy {

	private final String economy;

	private final String region;

	private final int year;

	private final int procedures;

	private final int time;

	public Economy(String economy, String region, int year, int procedures, int time) {
		this.economy = economy;
		this.region = region;
		this.year = year;
		this.procedures = procedures;
		this.time = time;
	}

	@JsonProperty("e")
	public String getEconomy() {
		return economy;
	}

	@JsonProperty("r")
	public String getRegion() {
		return region;
	}

	@JsonProperty("y")
	public int getYear() {
		return year;
	}

	@JsonProperty("p")
	public int getProcedures() {
		return procedures;
	}

	@JsonProperty("t")
	public int getTime() {
		return time;
	}

}

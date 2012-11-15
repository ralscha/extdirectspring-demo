/**
 * Copyright 2010-2012 Ralph Schaer <ralphschaer@gmail.com>
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Resource {

	@JsonProperty("Id")
	private final String id;

	@JsonProperty("Name")
	private final String name;

	private final String url;

	public Resource(String id, String name) {
		this(id, name, String.format("http://www.bbc.co.uk/%s/programmes/schedules.json", id));
	}

	public Resource(String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	@JsonIgnore
	public String getUrl() {
		return url;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}

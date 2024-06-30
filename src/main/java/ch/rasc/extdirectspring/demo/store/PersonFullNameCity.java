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
package ch.rasc.extdirectspring.demo.store;

public class PersonFullNameCity {
	private final String fullName;

	private final String city;

	public PersonFullNameCity(Person person) {
		this.fullName = person.getFullName();
		this.city = person.getCity();
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getCity() {
		return this.city;
	}

}

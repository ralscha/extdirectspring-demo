/**
 * Copyright 2010-2017 the original author or authors.
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
package ch.rasc.extdirectspring.demo.simpleapp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import ch.rasc.extclassgenerator.Model;

@Model(value = "Simple.model.User", paging = true, readMethod = "userService.load",
		createMethod = "userService.create", updateMethod = "userService.update",
		destroyMethod = "userService.destroy")
public class User {
	private String id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	@Email
	private String email;

	private String city;

	public User() {
		// default constructor
	}

	public User(String[] line) {
		this.id = line[0];
		this.firstName = line[1];
		this.lastName = line[2];
		this.email = line[3];
		this.city = line[4];
	}

	public void update(User newValues) {
		this.firstName = newValues.getFirstName();
		this.lastName = newValues.getLastName();
		this.email = newValues.email;
		this.city = newValues.getCity();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "User [id=" + this.id + ", firstName=" + this.firstName + ", lastName="
				+ this.lastName + ", email=" + this.email + ", city=" + this.city + "]";
	}

}

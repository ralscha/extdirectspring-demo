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

public class Person {

	private String fullName;

	private String firstName;

	private String lastName;

	private String id;

	private String street;

	private String city;

	private String state;

	private String zip;

	private String country;

	public Person() {
		// no action here
	}

	public Person(String[] nextLine) {
		this.id = nextLine[0];
		this.firstName = nextLine[1];
		this.lastName = nextLine[2];
		this.fullName = this.firstName + " " + this.lastName;
		this.street = nextLine[3];
		this.city = nextLine[4];
		this.state = nextLine[5];
		this.zip = nextLine[6];
		this.country = nextLine[7];
	}

	public void update(Person newValues) {
		this.firstName = newValues.getFirstName();
		this.lastName = newValues.getLastName();
		this.fullName = this.firstName + " " + this.lastName;
		this.street = newValues.getStreet();
		this.city = newValues.getCity();
		this.state = newValues.getState();
		this.zip = newValues.getZip();
		this.country = newValues.getCountry();

	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Person [fullName=" + this.fullName + ", firstName=" + this.firstName
				+ ", lastName=" + this.lastName + ", id=" + this.id + ", street="
				+ this.street + ", city=" + this.city + ", state=" + this.state + ", zip="
				+ this.zip + ", country=" + this.country + "]";
	}

}

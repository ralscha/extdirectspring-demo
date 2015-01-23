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
package ch.rasc.extdirectspring.demo.bancha;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class User {

	private int id;

	private String name;

	private String login;

	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateTimeDeserializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime created;

	private String email;

	private Object avatar;

	private int weight;

	private int height;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Object getAvatar() {
		return this.avatar;
	}

	public void setAvatar(Object avatar) {
		this.avatar = avatar;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "User [id=" + this.id + ", name=" + this.name + ", login=" + this.login
				+ ", created=" + this.created + ", email=" + this.email + ", avatar="
				+ this.avatar + ", weight=" + this.weight + ", height=" + this.height
				+ "]";
	}

}

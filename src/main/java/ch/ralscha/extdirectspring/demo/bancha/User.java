package ch.ralscha.extdirectspring.demo.bancha;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeDeserializer;
import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class User {

	private int id;

	private String name;

	private String login;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private DateTime created;

	private String email;

	private String avatar;

	private int weight;

	private int height;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getCreated() {
		return created;
	}

	@JsonDeserialize(using = ISO8601DateTimeDeserializer.class)
	public void setCreated(DateTime created) {
		this.created = created;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", login=" + login + ", created=" + created + ", email=" + email
				+ ", avatar=" + avatar + ", weight=" + weight + ", height=" + height + "]";
	}

}

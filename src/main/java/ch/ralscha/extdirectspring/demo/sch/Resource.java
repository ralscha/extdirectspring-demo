package ch.ralscha.extdirectspring.demo.sch;

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

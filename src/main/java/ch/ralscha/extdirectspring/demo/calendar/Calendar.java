package ch.ralscha.extdirectspring.demo.calendar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Calendar {

	private final int id;

	private final String title;

	private String description;

	private Integer color;

	private Boolean hidden;

	public Calendar(int id, String title, Integer color) {
		this.id = id;
		this.title = title;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public Boolean isHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}

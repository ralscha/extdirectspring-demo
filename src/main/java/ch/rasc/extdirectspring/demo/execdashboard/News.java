package ch.rasc.extdirectspring.demo.execdashboard;

import java.util.List;

public class News {
	private final int id;
	private final String type;
	private final String date;
	private final String time;
	private final String author;
	private final String group;
	private final String image;
	private final String title;
	private final String paragraph;

	public News(List<Object> line) {
		// id, type, date, time, author, group, image, title, paragraph
		id = (Integer) line.get(0);
		type = (String) line.get(1);
		date = (String) line.get(2);
		time = (String) line.get(3);
		author = (String) line.get(4);
		group = (String) line.get(5);
		image = (String) line.get(6);
		title = (String) line.get(7);
		paragraph = (String) line.get(8);
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getAuthor() {
		return author;
	}

	public String getGroup() {
		return group;
	}

	public String getImage() {
		return image;
	}

	public String getTitle() {
		return title;
	}

	public String getParagraph() {
		return paragraph;
	}

}

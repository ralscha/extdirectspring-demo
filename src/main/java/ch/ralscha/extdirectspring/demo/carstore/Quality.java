package ch.ralscha.extdirectspring.demo.carstore;

public class Quality {
	private final String name;

	private final int rating;

	public Quality(String name, int rating) {
		this.name = name;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public int getRating() {
		return rating;
	}

}

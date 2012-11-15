package ch.rasc.extdirectspring.demo.exp;

import java.util.concurrent.atomic.AtomicInteger;

public class Author {

	private final static AtomicInteger idGen = new AtomicInteger();

	private final int id = idGen.incrementAndGet();

	private String title;

	private String firstName;

	private String lastName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

}

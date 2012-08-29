package ch.ralscha.extdirectspring.demo.sch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car extends Resource {

	@JsonProperty("Seats")
	private final int seats;

	public Car(String id, String name, int seats) {
		super(id, name);
		this.seats = seats;
	}

	public int getSeats() {
		return seats;
	}

}

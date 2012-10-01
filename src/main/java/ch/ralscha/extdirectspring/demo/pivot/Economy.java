package ch.ralscha.extdirectspring.demo.pivot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Economy {

	private String economy;

	private String region;

	private int year;

	private int procedures;

	private int time;

	public Economy(String economy, String region, int year, int procedures, int time) {
		this.economy = economy;
		this.region = region;
		this.year = year;
		this.procedures = procedures;
		this.time = time;
	}

	@JsonProperty("e")
	public String getEconomy() {
		return economy;
	}

	@JsonProperty("r")
	public String getRegion() {
		return region;
	}

	@JsonProperty("y")
	public int getYear() {
		return year;
	}

	@JsonProperty("p")
	public int getProcedures() {
		return procedures;
	}

	@JsonProperty("t")
	public int getTime() {
		return time;
	}

}

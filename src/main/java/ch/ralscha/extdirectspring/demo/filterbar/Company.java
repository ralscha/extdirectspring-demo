package ch.ralscha.extdirectspring.demo.filterbar;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import ch.ralscha.extdirectspring.demo.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Company {

	private final int id;

	private final String company;

	private final BigDecimal price;

	private final BigDecimal change;

	private final BigDecimal pctChange;

	private final DateTime lastChange;

	private final String category;

	private final String country;

	private final int not_delete;

	public Company(int id, String company, String price, String change, String pctChange, String category,
			String country, int not_delete) {

		this.id = id;
		this.company = company;
		this.price = new BigDecimal(price);
		this.change = new BigDecimal(change);
		this.pctChange = new BigDecimal(pctChange);
		this.lastChange = DateTime.now();
		this.category = category;
		this.country = country;
		this.not_delete = not_delete;

	}

	public int getId() {
		return id;
	}

	public String getCompany() {
		return company;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getChange() {
		return change;
	}

	public BigDecimal getPctChange() {
		return pctChange;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getLastChange() {
		return lastChange;
	}

	public String getCategory() {
		return category;
	}

	public String getCountry() {
		return country;
	}

	public int getNot_delete() {
		return not_delete;
	}

}

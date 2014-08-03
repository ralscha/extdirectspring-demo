package ch.rasc.extdirectspring.demo.execdashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class StockOHLC {
	private int id;
	private String company;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	private LocalDate time;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;

	public StockOHLC(int id, String company, String[] nextLine) {
		this.id = id;
		this.company = company;
		this.time = LocalDate.parse(nextLine[0]);
		this.open = new BigDecimal(nextLine[1]);
		this.high = new BigDecimal(nextLine[2]);
		this.low = new BigDecimal(nextLine[3]);
		this.close = new BigDecimal(nextLine[4]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public LocalDate getTime() {
		return time;
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

}

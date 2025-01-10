/*
 * Copyright the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.extdirectspring.demo.execdashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;

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
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public LocalDate getTime() {
		return this.time;
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

	public BigDecimal getOpen() {
		return this.open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return this.high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return this.low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return this.close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

}

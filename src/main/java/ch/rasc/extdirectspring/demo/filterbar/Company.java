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
package ch.rasc.extdirectspring.demo.filterbar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;

public class Company {

	private final int id;

	private final String company;

	private final BigDecimal price;

	private final BigDecimal change;

	private final BigDecimal pctChange;

	private final LocalDate lastChange;

	private final String category;

	private final String country;

	private final int not_delete;

	public Company(int id, String company, String price, String change, String pctChange,
			String category, String country, int not_delete) {

		this.id = id;
		this.company = company;
		this.price = new BigDecimal(price);
		this.change = new BigDecimal(change);
		this.pctChange = new BigDecimal(pctChange);
		this.lastChange = LocalDate.now()
				.plusDays(ThreadLocalRandom.current().nextInt(-10, 10));
		this.category = category;
		this.country = country;
		this.not_delete = not_delete;

	}

	public int getId() {
		return this.id;
	}

	public String getCompany() {
		return this.company;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public BigDecimal getChange() {
		return this.change;
	}

	public BigDecimal getPctChange() {
		return this.pctChange;
	}

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	public LocalDate getLastChange() {
		return this.lastChange;
	}

	public String getCategory() {
		return this.category;
	}

	public String getCountry() {
		return this.country;
	}

	public int getNot_delete() {
		return this.not_delete;
	}

}

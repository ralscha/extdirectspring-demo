/**
 * Copyright 2010-2015 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo.are;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Model(value = "Are.Company", readMethod = "areService.read", idProperty = "coId")
public class Company {

	private final String coId = UUID.randomUUID().toString();

	private final String company;

	@ModelField(convert = "null", defaultValue = "undefined")
	private final BigDecimal price;

	@ModelField(convert = "null", defaultValue = "undefined")
	private final BigDecimal change;

	@ModelField(convert = "null", defaultValue = "undefined")
	private final BigDecimal pctChange;

	@ModelField(dateFormat = "c", defaultValue = "undefined")
	private final LocalDateTime lastChange;

	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = History.class,
			foreignKey = "companyId", autoLoad = true)
	@JsonView(AreService.WITH_HISTORY_VIEW.class)
	private final List<History> history = new ArrayList<>();

	public Company(String company) {
		this.company = company;

		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		this.price = new BigDecimal(rnd.nextDouble(10, 90)).setScale(2,
				RoundingMode.HALF_DOWN);
		this.change = new BigDecimal(rnd.nextDouble(0.1, 2.9)).setScale(2,
				RoundingMode.HALF_DOWN);
		this.pctChange = new BigDecimal(rnd.nextDouble(0.1, 9.9)).setScale(2,
				RoundingMode.HALF_DOWN);
		this.lastChange = LocalDateTime.now().minusSeconds(rnd.nextInt(86400));

		addHistory(new History(this, LocalDateTime.now().minusSeconds(rnd.nextInt(3600)),
				"Test"));
		addHistory(new History(this,
				LocalDateTime.now().minusSeconds(rnd.nextInt(86400)), "Initial"));

	}

	public String getCoId() {
		return coId;
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

	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	public LocalDateTime getLastChange() {
		return lastChange;
	}

	public List<History> getHistory() {
		return history;
	}

	public void addHistory(History h) {
		this.history.add(h);
	}
}

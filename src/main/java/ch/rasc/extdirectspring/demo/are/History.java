/**
 * Copyright 2010-2017 Ralph Schaer <ralphschaer@gmail.com>
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

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeSerializer;

@Model(value = "Are.History", readMethod = "areService.historyRead")
public class History {
	private final String id = UUID.randomUUID().toString();

	@ModelField(dateFormat = "c")
	private final LocalDateTime date;

	private final String text;

	private final String companyId;

	@JsonIgnore
	@ModelAssociation(value = ModelAssociationType.BELONGS_TO, foreignKey = "companyId")
	private final Company company;

	@JsonIgnore
	@ModelAssociation(value = ModelAssociationType.HAS_ONE, foreignKey = "companyId")
	private Company companyOne;

	public History(Company company, LocalDateTime date, String text) {
		this.company = company;
		this.companyId = company.getCoId();
		this.date = date;
		this.text = text;
	}

	public String getId() {
		return this.id;
	}

	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	public LocalDateTime getDate() {
		return this.date;
	}

	public String getText() {
		return this.text;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public Company getCompany() {
		return this.company;
	}

}

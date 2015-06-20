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
package ch.rasc.extdirectspring.demo.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;
import ch.rasc.extdirectspring.demo.util.LocalDateYYSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Company {
	private int id;

	private String company;

	private BigDecimal price;

	private LocalDate date;

	private LocalDate dateyy;

	private boolean visible;

	private SizeEnum size;

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

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@JsonSerialize(using = LocalDateYYSerializer.class)
	public LocalDate getDateyy() {
		return this.dateyy;
	}

	public void setDateyy(LocalDate dateyy) {
		this.dateyy = dateyy;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@JsonSerialize(using = SizeSerializer.class)
	public SizeEnum getSize() {
		return this.size;
	}

	public void setSize(SizeEnum size) {
		this.size = size;
	}

}

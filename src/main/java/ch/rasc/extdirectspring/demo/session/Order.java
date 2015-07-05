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
package ch.rasc.extdirectspring.demo.session;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateDeserializer;
import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateSerializer;

@JsonInclude(Include.NON_NULL)
public class Order {
	private int id;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	private LocalDate date;

	private Boolean shipped;

	private Integer customerId;

	public Order() {
		// default constructor
	}

	public Order(int id, int customerId, LocalDate date, boolean shipped) {
		this.id = id;
		this.customerId = customerId;
		this.date = date;
		this.shipped = shipped;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Boolean getShipped() {
		return this.shipped;
	}

	public void setShipped(Boolean shipped) {
		this.shipped = shipped;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Order [id=" + this.id + ", date=" + this.date + ", shipped="
				+ this.shipped + ", customerId=" + this.customerId + "]";
	}

}

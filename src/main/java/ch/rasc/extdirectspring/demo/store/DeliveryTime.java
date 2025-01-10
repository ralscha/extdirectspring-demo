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
package ch.rasc.extdirectspring.demo.store;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = LabelValueSerializer.class)
public enum DeliveryTime implements LabelValue {

	BeginningOfMonth("Beginning of Month"),
	BeginningToMiddleOfMonth("Beginning to Middle of Month"),
	MiddleOfMonth("Middle of Month"), MiddleToEndOfMonth("Middle to End of Month"),
	EndOfMonth("End of Month"), Unknown("Unknown");

	private String label;

	DeliveryTime(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public String getValue() {
		return name();
	}

}
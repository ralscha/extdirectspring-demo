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
import java.util.List;

public class Kpi {
	private final int id;
	private final String category;
	private final String name;
	private final BigDecimal data1;
	private final BigDecimal data2;

	public Kpi(List<Object> line) {
		// [0,"clicks", "May 2010", 53.34321776, 100.1152082 ],
		this.id = (Integer) line.get(0);
		this.category = (String) line.get(1);
		this.name = (String) line.get(2);
		this.data1 = (BigDecimal) line.get(3);
		this.data2 = (BigDecimal) line.get(4);

	}

	public int getId() {
		return this.id;
	}

	public String getCategory() {
		return this.category;
	}

	public String getName() {
		return this.name;
	}

	public BigDecimal getData1() {
		return this.data1;
	}

	public BigDecimal getData2() {
		return this.data2;
	}

}

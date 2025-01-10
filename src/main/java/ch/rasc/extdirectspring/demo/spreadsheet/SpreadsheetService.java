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
package ch.rasc.extdirectspring.demo.spreadsheet;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class SpreadsheetService {

	private final static Random random = new Random();

	private final Map<Integer, MonthlySales> sales = new TreeMap<>();

	public SpreadsheetService() {
		int id = 0;
		int currentYear = LocalDate.now().getYear();
		for (int year = 1900; year <= currentYear; year++) {
			MonthlySales sale = new MonthlySales();
			sale.setId(id++);
			sale.setYear(year);
			sale.setJan(random.nextInt(110) - 10);
			sale.setFeb(random.nextInt(110) - 10);
			sale.setMar(random.nextInt(210) - 10);
			sale.setApr(random.nextInt(210) - 10);
			sale.setMay(random.nextInt(210) - 10);
			sale.setJun(random.nextInt(310) - 10);
			sale.setJul(random.nextInt(310) - 10);
			sale.setAug(random.nextInt(310) - 10);
			sale.setSep(random.nextInt(610) - 10);
			sale.setOct(random.nextInt(510) - 10);
			sale.setNov(random.nextInt(210) - 10);
			sale.setDec(random.nextInt(110) - 10);

			this.sales.put(year, sale);
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "spreadsheet")
	public Collection<MonthlySales> read() {
		return this.sales.values();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "spreadsheet")
	public Collection<MonthlySales> update(List<MonthlySales> modifiedSales) {
		modifiedSales.forEach(s -> this.sales.put(s.getYear(), s));
		return this.sales.values();
	}

}

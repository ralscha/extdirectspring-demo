/**
 * Copyright 2010-2016 Ralph Schaer <ralphschaer@gmail.com>
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

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class StockHistoryData {

	public static void main(String[] args) {
		Map<String, Object> urlVariables = new HashMap<>();

		urlVariables.put("ticker", "GOOG");
		urlVariables.put("toMonth", 6);
		urlVariables.put("toDay", 31);
		urlVariables.put("toYear", 2014);
		urlVariables.put("fromMonth", 0);
		urlVariables.put("fromDay", 1);
		urlVariables.put("fromYear", 2012);
		urlVariables.put("type", "m"); // d for day, m for month, y for yearly

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(
				"http://real-chart.finance.yahoo.com/table.csv?s={ticker}&d={toMonth}&e={toDay}&f={toYear}&g={type}&a={fromMonth}&b={fromDay}&c={fromYear}&ignore=.csv",
				String.class, urlVariables);
		System.out.println(result);

	}

}

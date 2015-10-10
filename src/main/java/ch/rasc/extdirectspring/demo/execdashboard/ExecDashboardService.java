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
package ch.rasc.extdirectspring.demo.execdashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.bytecode.opencsv.CSVReader;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.ListFilter;
import ch.ralscha.extdirectspring.filter.StringFilter;

@Service
public class ExecDashboardService {

	private static final List<String> QUARTERS = Arrays.asList("q1_2010", "q2_2010",
			"q3_2010", "q4_2010", "q1_2011", "q2_2011", "q3_2011", "q4_2011", "q1_2012",
			"q2_2012");

	private static final List<String> ACCOUNTS = Arrays.asList("Total Revenue",
			"Cost Of Revenue", "Gross Profit", "Research Development", "Administrative",
			"Non Recurring", "Others", "Total Operating Expenses",
			"Operating Income or Loss", "Other Income/Expenses", "Earnings",
			"Interest Expense", "Income Before Tax", "Income Tax Expense",
			"Minority Interest", "Net Income - Continuing Ops", "Discontinued Operations",
			"Extraordinary Items", "Accounting Changes", "Other Items", "Net Income",
			"Preferred Stock Adjustments", "Net Income - Common Shares");

	private static final List<String> REGIONS = Arrays.asList("Asia", "Australia",
			"Central America", "Europe", "Middle East", "North America", "South America",
			"Africa");

	private final List<StockOHLC> aapl = new ArrayList<>();
	private final List<StockOHLC> googl = new ArrayList<>();
	private final List<StockOHLC> msft = new ArrayList<>();
	private List<News> news;
	private List<Kpi> kpi;

	public ExecDashboardService() throws IOException {
		readData("aapl", this.aapl);
		readData("googl", this.googl);
		readData("msft", this.msft);

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

		try (InputStream is = new ClassPathResource("news.json").getInputStream()) {
			List<List<Object>> newsLines = mapper.readValue(is, List.class);
			this.news = newsLines.stream().map(News::new).collect(Collectors.toList());
		}

		try (InputStream is = new ClassPathResource("kpi.json").getInputStream()) {
			List<List<Object>> kpiArrays = mapper.readValue(is, List.class);
			this.kpi = kpiArrays.stream().map(Kpi::new).collect(Collectors.toList());
		}
	}

	private static void readData(String ticker, List<StockOHLC> result)
			throws IOException {
		try (InputStream is = new ClassPathResource("stock_" + ticker + ".csv")
				.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				CSVReader reader = new CSVReader(br, ',')) {

			reader.readNext(); // ignore first line

			String[] nextLine;
			int ix = 1;
			while ((nextLine = reader.readNext()) != null) {
				if (nextLine.length == 7) {
					StockOHLC ohlc = new StockOHLC(ix++, ticker.toUpperCase(), nextLine);
					result.add(ohlc);
				}
			}
		}

	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "dashboard")
	public List<StockOHLC> readStockOHLC(ExtDirectStoreReadRequest request) {
		StringFilter filter = request.getFirstFilterForField("company");
		if (filter != null && filter.getValue() != null) {
			if (filter.getValue().equals("AAPL")) {
				return this.aapl;
			}
			else if (filter.getValue().equals("GOOG")) {
				return this.googl;
			}
			else if (filter.getValue().equals("MSFT")) {
				return this.msft;
			}
		}

		return null;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "dashboard")
	public List<Map<String, Object>> readStatements() {
		List<Map<String, Object>> result = new ArrayList<>();

		result.add(createStatement("q4", "QUARTERLY STATEMENT 4 2013", "Q42013Report.pdf",
				"2013 STATEMENTS", "DECEMBER 20, 2013"));
		result.add(createStatement("q3", "QUARTERLY STATEMENT 3 2013", "Q32013Report.pdf",
				"2013 STATEMENTS", "SEPTEMBER 20, 2013"));
		result.add(createStatement("q2", "QUARTERLY STATEMENT 2 2013", "Q22013Report.pdf",
				"2013 STATEMENTS", "JUNE 20, 2013"));
		result.add(createStatement("q1", "QUARTERLY STATEMENT 1 2013", "Q12013Report.pdf",
				"2013 STATEMENTS", "MARCH 20, 2013"));
		result.add(createStatement("q4", "QUARTERLY STATEMENT 4 2012", "Q42012Report.pdf",
				"2012 STATEMENTS", "DECEMBER 20, 2012"));
		result.add(createStatement("q3", "QUARTERLY STATEMENT 3 2012", "Q32012Report.pdf",
				"2012 STATEMENTS", "SEPTEMBER 20, 2012"));
		result.add(createStatement("q2", "QUARTERLY STATEMENT 2 2012", "Q22012Report.pdf",
				"2012 STATEMENTS", "JUNE 20, 2012"));
		result.add(createStatement("q1", "QUARTERLY STATEMENT 1 2012", "Q12012Report.pdf",
				"2012 STATEMENTS", "MARCH 20, 2012"));

		return result;
	}

	private static Map<String, Object> createStatement(String name, String title,
			String file, String type, String uploaded) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("title", title);
		map.put("thumb", "pdf.png");
		map.put("url", file);
		map.put("type", type);
		map.put("uploaded", uploaded);
		return map;
	}

	@ExtDirectMethod(group = "dashboard")
	public Map<String, Object> readCompanyData(String company) {
		Map<String, Object> result = new HashMap<>();
		if ("AAPL".equals(company)) {
			result.put("symbol", "AAPL");
			result.put("change", "0.12");
			result.put("changePercentage", "1.29");
			result.put("price", "9.38");
			result.put("maxMin", "9.39/9.30");
			result.put("volume", "154.4 m");
			result.put("label", "APPLE, INC");
		}
		else if ("GOOG".equals(company)) {
			result.put("symbol", "GOOG");
			result.put("change", "+13.25");
			result.put("changePercentage", "+2.40");
			result.put("price", "565.95");
			result.put("maxMin", "566.00/554.35");
			result.put("volume", "171.1 m");
			result.put("label", "GOOGLE, INC");
		}
		else if ("MSFT".equals(company)) {
			result.put("symbol", "MSFT");
			result.put("change", "-1.05");
			result.put("changePercentage", "-0.5");
			result.put("price", "29.45");
			result.put("maxMin", "33.33/29.45");
			result.put("volume", "29.3 m");
			result.put("label", "MICROSOFT, INC");
		}

		return result;
	}

	@ExtDirectMethod(group = "dashboard")
	public Map<String, List<String>> readMetaProfitloss() {
		Map<String, List<String>> result = new HashMap<>();
		result.put("quarters", QUARTERS);
		result.put("regions", REGIONS);
		return result;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "dashboard")
	public List<Map<String, Object>> readProfitloss() {
		List<Map<String, Object>> result = new ArrayList<>();

		ThreadLocalRandom random = ThreadLocalRandom.current();

		for (String account : ACCOUNTS) {
			for (String region : REGIONS) {
				Map<String, Object> row = new HashMap<>();
				row.put("region", region);
				row.put("account", account);

				for (String quarter : QUARTERS) {
					row.put(quarter, random.nextDouble(3000, 10000));
				}

				result.add(row);
			}
		}

		return result;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "dashboard")
	public List<News> readNews(ExtDirectStoreReadRequest request) {
		ListFilter<String> typeFilter = request.getFirstFilterForField("type");
		return this.news.stream().filter(n -> typeFilter.getValue().contains(n.getType()))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "dashboard")
	public List<Kpi> readKpi(ExtDirectStoreReadRequest request) {
		StringFilter categoryFilter = request.getFirstFilterForField("category");
		return this.kpi.stream()
				.filter(k -> categoryFilter.getValue().equals(k.getCategory()))
				.collect(Collectors.toList());
	}
}

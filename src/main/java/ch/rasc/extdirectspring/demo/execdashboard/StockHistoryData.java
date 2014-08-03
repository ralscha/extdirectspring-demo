package ch.rasc.extdirectspring.demo.execdashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class StockHistoryData {

	public static void main(String[] args) {
		Map<String,Object> urlVariables = new HashMap<>();

		urlVariables.put("ticker", "GOOGL");
		urlVariables.put("toMonth", 6);
		urlVariables.put("toDay", 31);
		urlVariables.put("toYear", 2014);
		urlVariables.put("fromMonth", 0);
		urlVariables.put("fromDay", 1);
		urlVariables.put("fromYear", 2012);
		urlVariables.put("type", "m"); //d for day, m for month, y for yearly

		RestTemplate restTemplate = new RestTemplate();
		 String result = restTemplate.getForObject("http://real-chart.finance.yahoo.com/table.csv?s={ticker}&d={toMonth}&e={toDay}&f={toYear}&g={type}&a={fromMonth}&b={fromDay}&c={fromYear}&ignore=.csv",
				 String.class,
				 urlVariables);
		 System.out.println(result);

	}

}

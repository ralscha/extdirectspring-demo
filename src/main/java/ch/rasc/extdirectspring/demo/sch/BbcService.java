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
package ch.rasc.extdirectspring.demo.sch;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class BbcService {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36";

	private final static ObjectMapper mapper = new ObjectMapper();

	private final static List<Resource> stations = Arrays.asList(
			new Resource("radio1", "BBC Radio 1",
					"http://www.bbc.co.uk/radio1/programmes/schedules/england.json"),
			new Resource("1xtra", "BBC Radio 1 Xtra"),
			new Resource("radio2", "BBC Radio 2"), new Resource("radio3", "BBC Radio 3"),
			new Resource("radio4", "BBC Radio 4",
					"http://www.bbc.co.uk/radio4/programmes/schedules/fm.json"),
			new Resource("5live", "BBC Radio 5"));

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "sch")
	public List<Resource> fetchStations() {
		return stations;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "sch")
	public List<Event> fetchSchedule() throws IOException {

		try (CloseableHttpAsyncClient backend = HttpAsyncClients.custom()
				.setUserAgent(USER_AGENT).build()) {
			backend.start();

			return stations.stream()
					.map(station -> backend.execute(new HttpGet(station.getUrl()), null))
					.flatMap(BbcService::deserializeJson)
					.map(BbcService::createEventObject).collect(Collectors.toList());
		}
	}

	@SuppressWarnings("unchecked")
	private static Stream<Map<String, Object>> deserializeJson(
			Future<HttpResponse> futureResponse) {
		try {
			HttpResponse response = futureResponse.get();
			String json = EntityUtils.toString(response.getEntity());
			Map<String, Object> schedules = mapper.readValue(json, Map.class);
			Map<String, Object> schedule = (Map<String, Object>) schedules
					.get("schedule");
			Map<String, Object> day = (Map<String, Object>) schedule.get("day");
			return ((List<Map<String, Object>>) day.get("broadcasts")).stream();
		}
		catch (ParseException | InterruptedException | ExecutionException
				| IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static Event createEventObject(Map<String, Object> broadcast) {
		Map<String, Object> programme = (Map<String, Object>) broadcast.get("programme");
		Map<String, Object> displayTitles = (Map<String, Object>) programme
				.get("display_titles");
		Map<String, Object> ownership = (Map<String, Object>) programme.get("ownership");
		Map<String, Object> service = (Map<String, Object>) ownership.get("service");

		Event event = new Event();
		event.setResourceId(String.valueOf(service.get("key")));
		event.setStartDate(
				ZonedDateTime.parse((String) broadcast.get("start")).toLocalDateTime());
		event.setEndDate(
				ZonedDateTime.parse((String) broadcast.get("end")).toLocalDateTime());
		event.setText(String.valueOf(displayTitles.get("title")));
		event.setDuration((Integer) broadcast.get("duration"));
		event.setId(String.valueOf(programme.get("pid")));
		event.setSynopsis(String.valueOf(programme.get("short_synopsis")));

		return event;

	}

}

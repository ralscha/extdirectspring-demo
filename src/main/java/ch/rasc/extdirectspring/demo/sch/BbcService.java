/**
 * Copyright 2010-2013 Ralph Schaer <ralphschaer@gmail.com>
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

@Service
public class BbcService {

	private final static ObjectMapper mapper = new ObjectMapper();

	private final static ImmutableList<Resource> stations = ImmutableList.of(new Resource("radio1", "BBC Radio 1",
			"http://www.bbc.co.uk/radio1/programmes/schedules/england.json"),
			new Resource("1xtra", "BBC Radio 1 Xtra"), new Resource("radio2", "BBC Radio 2"), new Resource("radio3",
					"BBC Radio 3"), new Resource("radio4", "BBC Radio 4",
					"http://www.bbc.co.uk/radio4/programmes/schedules/fm.json"), new Resource("5live", "BBC Radio 5"));

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "sch")
	public ImmutableList<Resource> fetchStations() {
		return stations;
	}

	@SuppressWarnings("unchecked")
	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "sch")
	public ImmutableList<Event> fetchSchedule() throws JsonParseException, JsonMappingException, IOException,
			InterruptedException, ExecutionException {

		ImmutableList.Builder<Event> eBuilder = ImmutableList.builder();

		List<Future<Response>> responses = Lists.newArrayList();

		for (Resource station : stations) {
			try (AsyncHttpClient asyncHttpClient = new AsyncHttpClient()) {
				responses.add(asyncHttpClient.prepareGet(station.getUrl()).execute());
			}
		}

		for (Future<Response> future : responses) {

			Map<String, Object> schedules = mapper.readValue(future.get().getResponseBody(), Map.class);
			Map<String, Object> schedule = (Map<String, Object>) schedules.get("schedule");
			Map<String, Object> day = (Map<String, Object>) schedule.get("day");
			List<Map<String, Object>> broadcasts = (List<Map<String, Object>>) day.get("broadcasts");

			for (Map<String, Object> broadcast : broadcasts) {

				Map<String, Object> programme = (Map<String, Object>) broadcast.get("programme");
				Map<String, Object> displayTitles = (Map<String, Object>) programme.get("display_titles");
				Map<String, Object> ownership = (Map<String, Object>) programme.get("ownership");
				Map<String, Object> service = (Map<String, Object>) ownership.get("service");

				Event e = new Event();
				e.setResourceId(String.valueOf(service.get("key")));
				e.setStartDate(DateTime.parse((String) broadcast.get("start")));
				e.setEndDate(DateTime.parse((String) broadcast.get("end")));
				e.setText(String.valueOf(displayTitles.get("title")));
				e.setDuration((Integer) broadcast.get("duration"));
				e.setId(String.valueOf(programme.get("pid")));
				e.setSynopsis(String.valueOf(programme.get("short_synopsis")));

				eBuilder.add(e);
			}
		}

		return eBuilder.build();

	}

}

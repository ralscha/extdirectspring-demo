package ch.ralscha.extdirectspring.demo.sch;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

@Service
public class BbcService {

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
	public ImmutableList<Event> fetchSchedule() throws JsonParseException, JsonMappingException, IOException {

		ImmutableList.Builder<Event> eBuilder = ImmutableList.builder();

		ObjectMapper mapper = new ObjectMapper();
		for (Resource station : stations) {
			Map<String, Object> schedules = mapper.readValue(new URL(station.getUrl()), Map.class);
			Map<String, Object> schedule = (Map<String, Object>) schedules.get("schedule");
			Map<String, Object> day = (Map<String, Object>) schedule.get("day");
			List<Map<String, Object>> broadcasts = (List<Map<String, Object>>) day.get("broadcasts");

			for (Map<String, Object> broadcast : broadcasts) {

				Map<String, Object> programme = (Map<String, Object>) broadcast.get("programme");
				Map<String, Object> displayTitles = (Map<String, Object>) programme.get("display_titles");
				Map<String, Object> ownership = (Map<String, Object>) programme.get("ownership");
				Map<String, Object> service = (Map<String, Object>) ownership.get("service");

				Event e = new Event();
				e.setResourceId((String) service.get("key"));
				e.setStartDate(DateTime.parse((String) broadcast.get("start")));
				e.setEndDate(DateTime.parse((String) broadcast.get("end")));
				e.setText((String) displayTitles.get("title"));
				e.setDuration((Integer) broadcast.get("duration"));
				e.setId((String) programme.get("pid"));
				e.setSynopsis((String) programme.get("short_synopsis"));

				eBuilder.add(e);
			}
		}

		return eBuilder.build();

	}

}

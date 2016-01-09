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
package ch.rasc.extdirectspring.demo.sch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service(value = "schCarService")
public class CarService {

	private final static AtomicInteger maxId = new AtomicInteger(7);

	private final static List<Car> cars = Arrays.asList(
			new Car("c1", "BMW #1", 4, LocalDate.now().plusDays(105)),
			new Car("c2", "BMW #2", 4, LocalDate.now().plusDays(70)),
			new Car("c3", "BMW #3", 2, LocalDate.now().plusDays(10)),
			new Car("c4", "BMW #4", 2, LocalDate.now().plusDays(35)),
			new Car("c5", "BMW #5", 2, LocalDate.now().plusDays(41)),
			new Car("c6", "BMW #6", 4, LocalDate.now().plusDays(11)));

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "sch")
	public List<Car> readCars() {
		return cars;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "sch")
	public List<Event> readEvents() {
		List<Event> eBuilder = new ArrayList<>();

		Event e = new Event();
		e.setId("1");
		e.setResourceId("c1");
		e.setName("Mike");
		e.setStartDate(LocalDateTime.now().withHour(9).withMinute(45).withSecond(0));
		e.setEndDate(LocalDateTime.now().withHour(11).withMinute(0).withSecond(0));
		eBuilder.add(e);

		e = new Event();
		e.setId("2");
		e.setResourceId("c2");
		e.setName("Linda");
		e.setStartDate(LocalDateTime.now().withHour(10).withMinute(15).withSecond(0));
		e.setEndDate(LocalDateTime.now().withHour(12).withMinute(0).withSecond(0));
		eBuilder.add(e);

		e = new Event();
		e.setId("3");
		e.setResourceId("c3");
		e.setName("Don");
		e.setStartDate(LocalDateTime.now().withHour(13).withMinute(0).withSecond(0));
		e.setEndDate(LocalDateTime.now().withHour(15).withMinute(0).withSecond(0));
		eBuilder.add(e);

		e = new Event();
		e.setId("4");
		e.setResourceId("c4");
		e.setName("Karen");
		e.setStartDate(LocalDateTime.now().withHour(16).withMinute(0).withSecond(0));
		e.setEndDate(LocalDateTime.now().withHour(18).withMinute(0).withSecond(0));
		eBuilder.add(e);

		e = new Event();
		e.setId("5");
		e.setResourceId("c5");
		e.setName("Doug");
		e.setStartDate(LocalDateTime.now().withHour(12).withMinute(0).withSecond(0));
		e.setEndDate(LocalDateTime.now().withHour(13).withMinute(0).withSecond(0));
		eBuilder.add(e);

		e = new Event();
		e.setId("6");
		e.setResourceId("c6");
		e.setName("Peter");
		e.setStartDate(LocalDateTime.now().withHour(14).withMinute(0).withSecond(0));
		e.setEndDate(LocalDateTime.now().withHour(16).withMinute(0).withSecond(0));
		eBuilder.add(e);

		return Collections.unmodifiableList(eBuilder);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "sch")
	public Event createEvent(Event e) {
		e.setId(String.valueOf(maxId.incrementAndGet()));
		System.out.println("create: " + e);
		return e;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "sch")
	public Event updateEvent(Event e) {
		System.out.println("update: " + e);
		return e;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "sch")
	public void destroyEvent(Event e) {
		System.out.println("destroy: " + e);
	}

}

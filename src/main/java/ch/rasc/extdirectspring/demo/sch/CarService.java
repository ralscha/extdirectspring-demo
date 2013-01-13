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

import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.google.common.collect.ImmutableList;

@Service(value = "schCarService")
public class CarService {

	private final static AtomicInteger maxId = new AtomicInteger(7);

	private final static ImmutableList<Car> cars = ImmutableList.of(new Car("c1", "BMW #1", 4), new Car("c2", "BMW #2",
			4), new Car("c3", "BMW #3", 2), new Car("c4", "BMW #4", 2), new Car("c5", "BMW #5", 2), new Car("c6",
			"BMW #6", 4));

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "sch")
	public ImmutableList<Car> readCars() {
		return cars;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "sch")
	public ImmutableList<Event> readEvents() {
		ImmutableList.Builder<Event> eBuilder = ImmutableList.builder();

		Event e = new Event();
		e.setId("1");
		e.setResourceId("c1");
		e.setName("Mike");
		e.setStartDate(DateTime.now().withTime(9, 45, 0, 0));
		e.setEndDate(DateTime.now().withTime(11, 0, 0, 0));
		eBuilder.add(e);

		e = new Event();
		e.setId("2");
		e.setResourceId("c2");
		e.setName("Linda");
		e.setStartDate(DateTime.now().withTime(10, 15, 0, 0));
		e.setEndDate(DateTime.now().withTime(12, 0, 0, 0));
		eBuilder.add(e);

		e = new Event();
		e.setId("3");
		e.setResourceId("c3");
		e.setName("Don");
		e.setStartDate(DateTime.now().withTime(13, 0, 0, 0));
		e.setEndDate(DateTime.now().withTime(15, 0, 0, 0));
		eBuilder.add(e);

		e = new Event();
		e.setId("4");
		e.setResourceId("c4");
		e.setName("Karen");
		e.setStartDate(DateTime.now().withTime(16, 0, 0, 0));
		e.setEndDate(DateTime.now().withTime(18, 0, 0, 0));
		eBuilder.add(e);

		e = new Event();
		e.setId("5");
		e.setResourceId("c5");
		e.setName("Doug");
		e.setStartDate(DateTime.now().withTime(12, 0, 0, 0));
		e.setEndDate(DateTime.now().withTime(13, 0, 0, 0));
		eBuilder.add(e);

		e = new Event();
		e.setId("6");
		e.setResourceId("c6");
		e.setName("Peter");
		e.setStartDate(DateTime.now().withTime(14, 0, 0, 0));
		e.setEndDate(DateTime.now().withTime(16, 0, 0, 0));
		eBuilder.add(e);

		return eBuilder.build();
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

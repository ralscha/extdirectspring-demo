/**
 * Copyright 2010-2017 the original author or authors.
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
package ch.rasc.extdirectspring.demo.carstore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class CarService {

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "carstore",
			batched = false)
	public List<CarData> read() {
		List<CarData> cars = new ArrayList<>();

		cars.add(new CarData("Porsche", "911", 135000,
				"//en.wikipedia.org/wiki/Porsche_911",
				"2004_Porsche_911_Carrera_type_997.jpg", new Quality("overall", 1),
				new Quality("mechanical", 4), new Quality("powertrain", 2),
				new Quality("body", 4), new Quality("interior", 3),
				new Quality("accessories", 2)));

		cars.add(new CarData("Nissan", "GT-R", 80000,
				"//en.wikipedia.org/wiki/Nissan_Gt-r", "250px-Nissan_GT-R.jpg",
				new Quality("overall", 2), new Quality("mechanical", 3),
				new Quality("powertrain", 5), new Quality("body", 4),
				new Quality("interior", 2), new Quality("accessories", 2)));

		cars.add(new CarData("BMW", "M3", 60500, "//en.wikipedia.org/wiki/Bmw_m3",
				"250px-BMW_M3_E92.jpg", new Quality("overall", 3),
				new Quality("mechanical", 5), new Quality("powertrain", 3),
				new Quality("body", 4), new Quality("interior", 5),
				new Quality("accessories", 3)));

		cars.add(new CarData("Audi", "S5", 53000,
				"//en.wikipedia.org/wiki/Audi_S5#Audi_S5", "250px-Audi_S5.jpg",
				new Quality("overall", 4), new Quality("mechanical", 1),
				new Quality("powertrain", 1), new Quality("body", 4),
				new Quality("interior", 1), new Quality("accessories", 5)));

		cars.add(new CarData("Audi", "TT", 40000, "//en.wikipedia.org/wiki/Audi_TT",
				"250px-2007_Audi_TT_Coupe.jpg", new Quality("overall", 5),
				new Quality("mechanical", 2), new Quality("powertrain", 2),
				new Quality("body", 3), new Quality("interior", 4),
				new Quality("accessories", 1)));

		return Collections.unmodifiableList(cars);
	}
}
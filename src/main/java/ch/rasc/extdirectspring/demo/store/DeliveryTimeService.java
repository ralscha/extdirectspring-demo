/**
 * Copyright 2010-2014 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo.store;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.StringFilter;

@Service
public class DeliveryTimeService {

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "combobox")
	public DeliveryTime[] getDeliveryTimes() {
		return DeliveryTime.values();
	}

	public List<String> actresses = Arrays.asList("Natalie Portman", "Evangeline Lilly",
			"Kate Beckinsale", "Keira Knightley", "Zoe Saldana", "Olivia Wilde",
			"Morena Baccarin", "Olga Kurylenko", "Liv Tyler", "Charlize Theron",
			"Mila Kunis", "Katie Cassidy", "Rosario Dawson", "Christina Hendricks",
			"Kristen Bell", "Nicole Kidman", "Michelle Pfeiffer", "Monica Bellucci",
			"Emmanuelle Vaugier", "Angelina Jolie", "Eva Green", "Cate Blanchett",
			"Cobie Smulders", "Kelly Reilly", "Yvonne Strahovski", "Marion Cotillard",
			"Emily Blunt", "Connie Nielsen", "Ni Ni", "Carly Pope");

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "combobox")
	public List<Map<String, String>> readActresses(ExtDirectStoreReadRequest request) {

		StringFilter filter;

		Stream<String> stream = actresses.stream();
		if (StringUtils.hasText(request.getQuery())) {
			stream = stream.filter(a -> a.toLowerCase().contains(
					request.getQuery().toLowerCase()));
		}
		else if ((filter = request.getFirstFilterForField("actress")) != null) {
			stream = stream.filter(a -> a.toLowerCase().contains(
					filter.getValue().toLowerCase()));
		}

		return stream.map(a -> Collections.singletonMap("actress", a)).collect(
				Collectors.toList());
	}
}

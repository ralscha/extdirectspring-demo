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
package ch.rasc.extdirectspring.demo.touch;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.rasc.extdirectspring.demo.util.PropertyComparatorFactory;

@Service
public class TouchTestAction {

	@ExtDirectMethod(group = "touchdirect")
	public long multiply(Long num) {
		if (num != null) {
			return num * 8;
		}
		return 0;
	}

	@ExtDirectMethod(group = "touchdirect")
	public String doEcho(String message) {
		return message;
	}

	private final static DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("uuuu/MM/dd 'at' hh:mm:ss");

	@ExtDirectMethod(value = ExtDirectMethodType.POLL, event = "message",
			group = "touchdirect")
	public String handleMessagePoll() {
		return "Successfully polled at: " + LocalDateTime.now().format(formatter);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.SIMPLE_NAMED, group = "touchdirect")
	public String showDetails(String firstName, String lastName, int age) {
		return String.format("Hi %s %s, you are %d years old.", firstName, lastName, age);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "touchdirect")
	public List<Turnover> getGrid(ExtDirectStoreReadRequest request) {
		List<Turnover> result = new ArrayList<>();

		result.add(new Turnover("ABC Accounting", new BigDecimal("50000")));
		result.add(new Turnover("Ezy Video Rental", new BigDecimal("106300")));
		result.add(new Turnover("Greens Fruit Grocery", new BigDecimal("120000")));
		result.add(new Turnover("Icecream Express", new BigDecimal("73000")));
		result.add(new Turnover("Ripped Gym", new BigDecimal("88400")));
		result.add(new Turnover("Smith Auto Mechanic", new BigDecimal("222980")));

		Comparator<Turnover> comparator = PropertyComparatorFactory
				.createComparatorFromSorters(request.getSorters());
		if (comparator != null) {
			return result.stream().sorted(comparator).collect(Collectors.toList());
		}

		return result;
	}

}

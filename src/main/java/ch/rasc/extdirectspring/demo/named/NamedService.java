/*
 * Copyright the original author or authors.
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
package ch.rasc.extdirectspring.demo.named;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class NamedService {

	@ExtDirectMethod(value = ExtDirectMethodType.SIMPLE_NAMED, group = "named")
	public String showDetails(List<Business> bo, String firstName, String lastName,
			int age) {
		bo.forEach(System.out::println);
		return String.format("Hi %s %s, you are %d years old.", firstName, lastName, age);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.SIMPLE_NAMED, group = "named")
	public boolean nonStrict(Map<String, Object> parameters) {
		parameters.forEach((k, v) -> System.out.println(k + "-->" + v));
		return true;
	}
}

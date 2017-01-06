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
package ch.rasc.extdirectspring.demo.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import au.com.bytecode.opencsv.CSVReader;

@Service
public class RandomDataBean {

	@Autowired
	private Resource randomdata;

	private int maxId;

	private Map<Integer, Person> persons;

	@PostConstruct
	public void readData() throws IOException {
		this.persons = new HashMap<>();
		try (InputStream is = this.randomdata.getInputStream();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is, StandardCharsets.UTF_8));
				CSVReader reader = new CSVReader(br, '|')) {
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				Person p = new Person(nextLine);
				this.persons.put(Integer.valueOf(p.getId()), p);
				this.maxId = Math.max(this.maxId, Integer.parseInt(p.getId()));
			}
		}

	}

	public List<Person> findPersons(final String query) {
		if (StringUtils.hasText(query)) {
			String lowerCaseQuery = query.toLowerCase();
			return this.persons.values().stream()
					.filter(p -> p.getLastName().toLowerCase().startsWith(lowerCaseQuery))
					.collect(Collectors.toList());
		}

		return new ArrayList<>(this.persons.values());
	}

	public Person findPerson(String id) {
		return this.persons.get(Integer.valueOf(id));
	}

	public void deletePerson(int personId) {
		this.persons.remove(personId);
	}

	public void deletePerson(Person person) {
		this.persons.remove(Integer.valueOf(person.getId()));
	}

	public Person insert(Person p) {
		this.maxId = this.maxId + 1;
		p.setId(String.valueOf(this.maxId));
		this.persons.put(this.maxId, p);
		return p;
	}

	public int totalSize() {
		return this.persons.size();
	}

}

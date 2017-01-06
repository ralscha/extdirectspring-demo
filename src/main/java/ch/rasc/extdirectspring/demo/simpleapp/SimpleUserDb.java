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
package ch.rasc.extdirectspring.demo.simpleapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;

@Service
public class SimpleUserDb {

	@Autowired
	private Resource userdata;

	private volatile int maxId;

	private Map<String, User> users;

	@PostConstruct
	public void readData() throws IOException {
		this.users = new ConcurrentHashMap<>();
		try (InputStream is = this.userdata.getInputStream();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is, StandardCharsets.UTF_8));
				CSVReader reader = new CSVReader(br, '|')) {
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				User u = new User(nextLine);
				this.users.put(u.getId(), u);
				this.maxId = Math.max(this.maxId, Integer.parseInt(u.getId()));
			}
		}
	}

	public Collection<User> getAll() {
		return this.users.values();
	}

	public List<User> filter(String filter) {
		String lowerCaseFilter = filter.toLowerCase();

		return this.users.values().stream()
				.filter(user -> user.getLastName().toLowerCase().contains(lowerCaseFilter)
						|| user.getFirstName().toLowerCase().contains(lowerCaseFilter)
						|| user.getEmail().toLowerCase().contains(lowerCaseFilter)
						|| user.getCity().toLowerCase().contains(lowerCaseFilter))
				.collect(Collectors.toList());

	}

	public User findUser(String id) {
		return this.users.get(id);
	}

	public void deleteUser(User user) {
		this.users.remove(user.getId());
	}

	public User insert(User p) {
		this.maxId = this.maxId + 1;
		p.setId(String.valueOf(this.maxId));
		this.users.put(String.valueOf(this.maxId), p);
		return p;
	}

}

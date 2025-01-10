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
package ch.rasc.extdirectspring.demo.bigdata;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeDb {

	@Autowired
	private Resource employees;

	private Map<String, Employee> employeesStore;

	@PostConstruct
	public void readData() throws IOException {
		this.employeesStore = new HashMap<>();
		try (InputStream is = this.employees.getInputStream()) {

			ObjectMapper om = new ObjectMapper();
			List<Employee> emps = om.readValue(is, new TypeReference<List<Employee>>() {
				/* nothing_here */
			});

			for (Employee employee : emps) {
				this.employeesStore.put(employee.getEmployeeNo(), employee);
			}
		}
	}

	public Stream<Employee> getAll() {
		return this.employeesStore.values().stream();
	}

	public Employee findEmployee(String employeeNo) {
		return this.employeesStore.get(employeeNo);
	}

}

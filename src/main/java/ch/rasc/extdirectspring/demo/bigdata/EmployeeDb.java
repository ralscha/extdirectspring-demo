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
package ch.rasc.extdirectspring.demo.bigdata;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

@Service
public class EmployeeDb {

	@Autowired
	private Resource employees;

	private Map<String, Employee> employeesStore;

	@PostConstruct
	public void readData() throws IOException {
		employeesStore = Maps.newHashMap();
		try (InputStream is = employees.getInputStream()) {

			ObjectMapper om = new ObjectMapper();
			List<Employee> emps = om.readValue(is, new TypeReference<List<Employee>>() {
				/* nothing_here */
			});

			for (Employee employee : emps) {
				employeesStore.put(employee.getEmployeeNo(), employee);
			}
		}
	}

	public List<Employee> getAll() {
		return ImmutableList.copyOf(employeesStore.values());
	}

	public Employee findEmployee(String employeeNo) {
		return employeesStore.get(employeeNo);
	}

}

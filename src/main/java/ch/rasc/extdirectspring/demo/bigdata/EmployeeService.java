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
package ch.rasc.extdirectspring.demo.bigdata;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.rasc.extdirectspring.demo.util.PropertyComparatorFactory;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDb employeeDb;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "bigdata")
	public List<Employee> read(ExtDirectStoreReadRequest request) {

		Stream<Employee> employees = employeeDb.getAll();
		Comparator<Employee> comparator = PropertyComparatorFactory
				.createComparatorFromSorters(request.getSorters());
		if (comparator != null) {
			employees = employees.sorted(comparator);
		}

		return employees.collect(Collectors.toList());

	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bigdata")
	public List<Employee> update(List<Employee> modifiedEmployees) {
		List<Employee> updatedRecords = new ArrayList<>();
		for (Employee modifiedEmployee : modifiedEmployees) {
			Employee e = employeeDb.findEmployee(modifiedEmployee.getEmployeeNo());
			if (e != null) {
				e.update(modifiedEmployee);
				updatedRecords.add(e);
			}
		}
		return updatedRecords;
	}

}

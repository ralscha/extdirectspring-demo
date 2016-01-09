/**
 * Copyright 2010-2016 Ralph Schaer <ralphschaer@gmail.com>
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.DataType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.bean.Field;
import ch.ralscha.extdirectspring.bean.MetaData;
import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.rasc.extdirectspring.demo.util.PropertyComparatorFactory;

@Service
public class PersonAction {

	@Autowired
	private RandomDataBean dataBean;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "store")
	public List<Person> load(ExtDirectStoreReadRequest request) {
		return this.dataBean.findPersons(request.getQuery());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "store")
	public ExtDirectStoreResult<Person> loadWithPaging(
			ExtDirectStoreReadRequest request) {

		List<Person> persons = this.dataBean.findPersons(request.getQuery());
		int totalSize = persons.size();

		Comparator<Person> comparator = null;

		if (StringUtils.hasText(request.getGroupBy())) {
			comparator = PropertyComparatorFactory.createComparator(request.getGroupBy());
			if (comparator != null) {
				if (request.isDescendingGroupSort()) {
					comparator = comparator.reversed();
				}
			}
		}

		Stream<Person> personsStream = persons.stream();

		Comparator<Person> comparatorFromSorters = PropertyComparatorFactory
				.createComparatorFromSorters(request.getSorters());

		if (comparatorFromSorters != null) {
			if (comparator == null) {
				comparator = comparatorFromSorters;
			}
			else {
				comparator = comparator.thenComparing(comparatorFromSorters);
			}
		}

		if (comparator != null) {
			personsStream = personsStream.sorted(comparator);
		}

		if (request.getStart() != null && request.getLimit() != null) {
			personsStream = personsStream.skip(request.getStart())
					.limit(request.getLimit());
		}

		return new ExtDirectStoreResult<>(totalSize,
				personsStream.collect(Collectors.toList()));
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "store")
	public ExtDirectStoreResult<Person> create(List<Person> newPersons) {
		List<Person> insertedPersons = new ArrayList<>();

		for (Person newPerson : newPersons) {
			this.dataBean.insert(newPerson);
			insertedPersons.add(newPerson);
		}

		return new ExtDirectStoreResult<>(insertedPersons);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "store")
	public ExtDirectStoreResult<Person> update(List<Person> modifiedPersons) {

		List<Person> updatedRecords = new ArrayList<>();

		for (Person modifiedPerson : modifiedPersons) {
			Person p = this.dataBean.findPerson(modifiedPerson.getId());
			if (p != null) {
				p.update(modifiedPerson);
				updatedRecords.add(p);
			}
		}

		return new ExtDirectStoreResult<>(updatedRecords);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "store")
	public ExtDirectStoreResult<Integer> destroy(List<Integer> destroyIds) {
		List<Integer> deletedPersonsId = new ArrayList<>();

		for (Integer id : destroyIds) {
			this.dataBean.deletePerson(id);
			deletedPersonsId.add(id);
		}

		return new ExtDirectStoreResult<>(deletedPersonsId);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "store")
	public Set<State> getStates() {
		return this.dataBean.findPersons(null).stream().map(p -> new State(p.getState()))
				.collect(Collectors.toSet());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "metadata")
	public ExtDirectStoreResult<PersonFullName> loadPersonFullName(
			ExtDirectStoreReadRequest request) {

		List<Person> persons = this.dataBean.findPersons(null);
		int totalSize = persons.size();

		Stream<Person> personsStream = persons.stream()
				.sorted(Comparator.comparing(Person::getFullName).reversed());

		if (request.getStart() != null && request.getLimit() != null) {
			personsStream = personsStream.skip(request.getStart())
					.limit(request.getLimit());
		}
		else {
			personsStream = personsStream.limit(100);
		}

		List<PersonFullName> personFullNameList = personsStream.map(PersonFullName::new)
				.collect(Collectors.toList());

		ExtDirectStoreResult<PersonFullName> response = new ExtDirectStoreResult<>(
				totalSize, personFullNameList);

		// Send metadata only the first time
		if (request.getStart() == null || request.getStart() == 0) {
			MetaData metaData = new MetaData();

			metaData.setPagingParameter(0, 100);
			metaData.setSortInfo("fullName", SortDirection.DESCENDING);

			Field field = new Field("fullName");
			field.setType(DataType.STRING);
			field.addCustomProperty("header", "Full Name");
			field.addCustomProperty("width", 60);
			field.addCustomProperty("sortable", Boolean.FALSE);
			field.addCustomProperty("resizable", Boolean.TRUE);
			field.addCustomProperty("hideable", Boolean.FALSE);
			metaData.addField(field);

			response.setMetaData(metaData);
		}
		return response;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "metadata")
	public ExtDirectStoreResult<PersonFullNameCity> loadPersonFullNameCity(
			ExtDirectStoreReadRequest request) {

		List<Person> persons = this.dataBean.findPersons(null);
		int totalSize = persons.size();

		Stream<Person> personsStream = persons.stream()
				.sorted(Comparator.comparing(Person::getCity));

		if (request.getStart() != null && request.getLimit() != null) {
			personsStream = personsStream.skip(request.getStart())
					.limit(request.getLimit());
		}
		else {
			personsStream = personsStream.limit(50);
		}

		ExtDirectStoreResult<PersonFullNameCity> response = new ExtDirectStoreResult<>(
				totalSize,
				personsStream.map(PersonFullNameCity::new).collect(Collectors.toList()));

		// Send metadata only the first time
		if (request.getStart() == null || request.getStart() == 0) {
			MetaData metaData = new MetaData();

			metaData.setPagingParameter(0, 50);
			metaData.setSortInfo("city", SortDirection.ASCENDING);

			Field field = new Field("fullName");
			field.setType(DataType.STRING);
			field.addCustomProperty("header", "Full Name");
			field.addCustomProperty("width", 120);
			field.addCustomProperty("sortable", Boolean.FALSE);
			field.addCustomProperty("resizable", Boolean.TRUE);
			field.addCustomProperty("hideable", Boolean.FALSE);
			metaData.addField(field);

			field = new Field("city");
			field.setType(DataType.STRING);
			field.addCustomProperty("header", "City");
			field.addCustomProperty("width", 60);
			field.addCustomProperty("sortable", Boolean.FALSE);
			field.addCustomProperty("resizable", Boolean.TRUE);
			field.addCustomProperty("hideable", Boolean.TRUE);
			metaData.addField(field);

			response.setMetaData(metaData);
		}
		return response;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "metadata")
	public ExtDirectStoreResult<Person> loadPersonEverything(
			ExtDirectStoreReadRequest request) {

		List<Person> persons = this.dataBean.findPersons(request.getQuery());
		int totalSize = persons.size();

		Comparator<Person> comparator;
		if (!request.getSorters().isEmpty()) {
			comparator = PropertyComparatorFactory
					.createComparatorFromSorters(request.getSorters());
		}
		else {
			comparator = Comparator.comparing(Person::getLastName);
		}

		Stream<Person> personsStream = persons.stream();

		if (comparator != null) {
			personsStream = personsStream.sorted(comparator);
		}

		if (request.getStart() != null && request.getLimit() != null) {
			personsStream = personsStream.skip(request.getStart())
					.limit(request.getLimit());
		}
		else {
			personsStream = personsStream.limit(60);
		}

		ExtDirectStoreResult<Person> response = new ExtDirectStoreResult<>(totalSize,
				personsStream.collect(Collectors.toList()));

		// Send metadata only the first time
		if (request.getStart() == null || request.getStart() == 0) {
			MetaData metaData = new MetaData();

			metaData.setPagingParameter(0, 60);
			metaData.setSortInfo("lastName", SortDirection.ASCENDING);

			Field field = new Field("lastName");
			field.setType(DataType.STRING);
			field.addCustomProperty("header", "Last Name");
			field.addCustomProperty("width", 60);
			field.addCustomProperty("sortable", Boolean.TRUE);
			field.addCustomProperty("resizable", Boolean.TRUE);
			field.addCustomProperty("hideable", Boolean.FALSE);
			metaData.addField(field);

			field = new Field("firstName");
			field.setType(DataType.STRING);
			field.addCustomProperty("header", "First Name");
			field.addCustomProperty("width", 60);
			field.addCustomProperty("sortable", Boolean.TRUE);
			field.addCustomProperty("resizable", Boolean.TRUE);
			field.addCustomProperty("hideable", Boolean.FALSE);
			metaData.addField(field);

			field = new Field("street");
			field.setType(DataType.STRING);
			field.addCustomProperty("header", "Street");
			field.addCustomProperty("width", 60);
			field.addCustomProperty("sortable", Boolean.TRUE);
			field.addCustomProperty("resizable", Boolean.TRUE);
			field.addCustomProperty("hideable", Boolean.TRUE);
			metaData.addField(field);

			field = new Field("city");
			field.setType(DataType.STRING);
			field.addCustomProperty("header", "City");
			field.addCustomProperty("width", 60);
			field.addCustomProperty("sortable", Boolean.FALSE);
			field.addCustomProperty("resizable", Boolean.TRUE);
			field.addCustomProperty("hideable", Boolean.TRUE);
			metaData.addField(field);

			field = new Field("country");
			field.setType(DataType.STRING);
			field.addCustomProperty("header", "Country");
			field.addCustomProperty("width", 60);
			field.addCustomProperty("sortable", Boolean.FALSE);
			field.addCustomProperty("resizable", Boolean.TRUE);
			field.addCustomProperty("hideable", Boolean.TRUE);
			metaData.addField(field);

			response.setMetaData(metaData);
		}

		return response;

	}
}

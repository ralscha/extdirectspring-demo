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
package ch.rasc.extdirectspring.demo.touchgrid;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.extdirectspring.demo.util.PropertyComparatorFactory;

@Service
public class GridContactService {

	@Autowired
	private GridContactDb gridContactDb;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "touchgrid")
	public ExtDirectStoreResult<GridContact> read(ExtDirectStoreReadRequest request) {

		Stream<GridContact> contacts = gridContactDb.getAll();
		Comparator<GridContact> comparator = PropertyComparatorFactory
				.createComparatorFromSorters(request.getSorters());
		if (comparator != null) {
			contacts = contacts.sorted(comparator);
		}

		List<GridContact> result = contacts.collect(Collectors.toList());
		return new ExtDirectStoreResult<>(result.size(), result);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "touchgrid")
	public List<GridContact> update(List<GridContact> updatedContacts) {
		for (GridContact gridContact : updatedContacts) {
			gridContactDb.addOrUpdate(gridContact);
		}
		return updatedContacts;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "touchgrid")
	public void destroy(List<GridContact> deletedContacts) {
		for (GridContact gridContact : deletedContacts) {
			gridContactDb.delete(gridContact);
		}
	}

}

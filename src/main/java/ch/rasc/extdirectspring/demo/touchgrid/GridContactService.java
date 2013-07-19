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
package ch.rasc.extdirectspring.demo.touchgrid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import ch.rasc.extdirectspring.demo.util.PropertyOrderingFactory;

import com.google.common.collect.Ordering;

@Service
public class GridContactService {

	@Autowired
	private GridContactDb gridContactDb;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "touchgrid")
	public ExtDirectStoreReadResult<GridContact> read(ExtDirectStoreReadRequest request) {

		List<GridContact> contacts = gridContactDb.getAll();
		Ordering<GridContact> ordering = PropertyOrderingFactory.createOrderingFromSorters(request.getSorters());
		if (ordering != null) {
			contacts = ordering.sortedCopy(contacts);
		}

		// contacts = contacts.subList(request.getStart(),
		// Math.min(gridContactDb.getTotalSize(), request.getStart() +
		// request.getLimit()));

		return new ExtDirectStoreReadResult<>(gridContactDb.getTotalSize(), contacts);

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

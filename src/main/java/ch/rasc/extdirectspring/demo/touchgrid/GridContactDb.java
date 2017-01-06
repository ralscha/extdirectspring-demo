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
package ch.rasc.extdirectspring.demo.touchgrid;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GridContactDb {

	@Autowired
	private Resource contacts;

	private Map<Integer, GridContact> gridContactStore;

	private int totalSize;

	@PostConstruct
	public void readData() throws IOException {
		this.gridContactStore = new HashMap<>();
		try (InputStream is = this.contacts.getInputStream()) {

			ObjectMapper om = new ObjectMapper();
			List<GridContact> ci = om.readValue(is,
					new TypeReference<List<GridContact>>() {
						/* nothing_here */
					});

			for (GridContact contact : ci) {
				this.gridContactStore.put(contact.getId(), contact);
			}
		}

		this.totalSize = this.gridContactStore.size();
	}

	public Stream<GridContact> getAll() {
		return this.gridContactStore.values().stream();
	}

	public int getTotalSize() {
		return this.totalSize;
	}

	public void delete(GridContact contact) {
		this.gridContactStore.remove(contact.getId());
	}

	public void addOrUpdate(GridContact contact) {
		if (contact.getId() <= 0) {
			int id = this.gridContactStore.size() + 1;
			contact.setId(id);
			this.gridContactStore.put(id, contact);
		}
		else {
			this.gridContactStore.put(contact.getId(), contact);
		}
	}

}

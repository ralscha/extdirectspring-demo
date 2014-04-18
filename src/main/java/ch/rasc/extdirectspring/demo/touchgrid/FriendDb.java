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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FriendDb {

	@Autowired
	private Resource friends;

	private Map<Integer, Friend> friendStore;

	private int totalSize;

	@PostConstruct
	public void readData() throws IOException {
		friendStore = new HashMap<>();
		try (InputStream is = friends.getInputStream()) {

			ObjectMapper om = new ObjectMapper();
			List<Friend> fs = om.readValue(is, new TypeReference<List<Friend>>() {
				/* nothing_here */
			});

			for (Friend friend : fs) {
				friendStore.put(friend.getId(), friend);
			}
		}

		totalSize = friendStore.size();
	}

	public Collection<Friend> getAll() {
		return friendStore.values();
	}

	public int getTotalSize() {
		return totalSize;
	}

}

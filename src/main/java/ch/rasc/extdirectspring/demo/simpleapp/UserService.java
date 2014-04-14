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
package ch.rasc.extdirectspring.demo.simpleapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.extdirectspring.demo.util.PropertyComparatorFactory;

@Service
public class UserService {

	@Autowired
	private SimpleUserDb userDb;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "simpleapp")
	public ExtDirectStoreResult<User> load(ExtDirectStoreReadRequest request) {

		StringFilter filter = request.getFirstFilterForField("filter");
		Stream<User> usersStream;
		int totalSize;
		if (filter == null || StringUtils.isEmpty(filter.getValue())) {
			Collection<User> users = userDb.getAll();
			totalSize = users.size();
			usersStream = users.stream();
		} else {
			List<User> users = userDb.filter(filter.getValue());
			totalSize = users.size();
			usersStream = users.stream();
		}

		Comparator<User> comparator = PropertyComparatorFactory.createComparatorFromSorters(request.getSorters());
		if (comparator != null) {
			usersStream = usersStream.sorted(comparator);
		}

		if (request.getPage() != null && request.getLimit() != null) {
			usersStream = usersStream.skip(request.getStart()).limit(request.getLimit());
		}

		return new ExtDirectStoreResult<>(totalSize, usersStream.collect(Collectors.toList()), true);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simpleapp")
	public List<User> create(List<User> newUsers) {
		List<User> insertedUsers = new ArrayList<>();

		for (User newUser : newUsers) {
			userDb.insert(newUser);
			insertedUsers.add(newUser);
		}

		return insertedUsers;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simpleapp")
	public List<User> update(List<User> modifiedUsers) {
		List<User> updatedRecords = new ArrayList<>();
		for (User modifiedUser : modifiedUsers) {
			User u = userDb.findUser(modifiedUser.getId());
			if (u != null) {
				u.update(modifiedUser);
				updatedRecords.add(u);
			}
		}
		return updatedRecords;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simpleapp")
	public void destroy(List<User> destroyUsers) {
		for (User user : destroyUsers) {
			userDb.deleteUser(user);
		}
	}
}

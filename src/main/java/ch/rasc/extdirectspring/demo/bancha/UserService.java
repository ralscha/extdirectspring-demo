/**
 * Copyright 2010-2015 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo.bancha;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;

@Service("banchaUserService")
public class UserService {

	private final static Map<Integer, User> userDb = new ConcurrentHashMap<>();

	private final static AtomicInteger maxId = new AtomicInteger(4);

	@PostConstruct
	public void init() {

		User user = new User();
		user.setId(1);
		user.setName("Joe");
		user.setLogin("joe");
		user.setCreated(LocalDateTime.of(2012, 7, 28, 8, 54, 20));
		user.setEmail("joe@test.com");
		user.setAvatar(null);
		user.setWeight(76);
		user.setHeight(187);
		userDb.put(1, user);

		user = new User();
		user.setId(2);
		user.setName("Dan");
		user.setLogin("dan");
		user.setCreated(LocalDateTime.of(2012, 7, 29, 11, 5, 20));
		user.setEmail("dan@test.com");
		user.setAvatar(null);
		user.setWeight(70);
		user.setHeight(230);
		userDb.put(2, user);

		user = new User();
		user.setId(3);
		user.setName("Ralph");
		user.setLogin("ralph");
		user.setCreated(LocalDateTime.of(2012, 7, 30, 16, 11, 44));
		user.setEmail("ralph@test.com");
		user.setAvatar(null);
		user.setWeight(72);
		user.setHeight(180);
		userDb.put(3, user);

		user = new User();
		user.setId(4);
		user.setName("Nils");
		user.setLogin("nils");
		user.setCreated(LocalDateTime.of(2012, 7, 31, 18, 0, 1));
		user.setEmail("nils@test.com");
		user.setAvatar(null);
		user.setWeight(82);
		user.setHeight(186);
		userDb.put(4, user);

	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "bancha")
	public Collection<User> read() {
		return userDb.values();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public List<User> create(List<User> newUsers) {
		List<User> result = new ArrayList<>();
		for (User user : newUsers) {
			user.setId(maxId.incrementAndGet());
			userDb.put(user.getId(), user);
			result.add(user);
		}
		return Collections.unmodifiableList(result);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public List<User> update(List<User> updatedUsers) {
		for (User user : updatedUsers) {
			userDb.put(user.getId(), user);
		}
		return updatedUsers;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public void destroy(List<User> destroyUsers) {
		for (User user : destroyUsers) {
			userDb.remove(user.getId());
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_LOAD, group = "bancha")
	public User load(int id) {
		return userDb.get(id);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "bancha")
	public ExtDirectFormPostResult submit(@Valid User user, BindingResult result) {
		if (!result.hasErrors()) {
			if (user.getId() > 0) {
				userDb.put(user.getId(), user);
			}
			else {
				user.setId(maxId.incrementAndGet());
				userDb.put(user.getId(), user);
			}
		}
		System.out.println(user);
		user.setAvatar(null);
		return new ExtDirectFormPostResult(result);
	}

}

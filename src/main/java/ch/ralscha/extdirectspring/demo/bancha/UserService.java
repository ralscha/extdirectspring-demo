package ch.ralscha.extdirectspring.demo.bancha;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

@Service("banchaUserService")
public class UserService {

	private final static Map<Integer, User> userDb = Maps.newConcurrentMap();

	private final static AtomicInteger maxId = new AtomicInteger(4);

	@PostConstruct
	public void init() {

		User user = new User();
		user.setId(1);
		user.setName("Joe");
		user.setLogin("joe");
		user.setCreated(new DateTime(2012, 7, 28, 8, 54, 20));
		user.setEmail("joe@test.com");
		user.setAvatar("joe.png");
		user.setWeight(76);
		user.setHeight(187);
		userDb.put(1, user);

		user = new User();
		user.setId(2);
		user.setName("Dan");
		user.setLogin("dan");
		user.setCreated(new DateTime(2012, 7, 29, 11, 5, 20));
		user.setEmail("dan@test.com");
		user.setAvatar("dan.png");
		user.setWeight(70);
		user.setHeight(230);
		userDb.put(2, user);

		user = new User();
		user.setId(3);
		user.setName("Ralph");
		user.setLogin("ralph");
		user.setCreated(new DateTime(2012, 7, 30, 16, 11, 44));
		user.setEmail("ralph@test.com");
		user.setAvatar("ralph.png");
		user.setWeight(72);
		user.setHeight(180);
		userDb.put(3, user);

		user = new User();
		user.setId(4);
		user.setName("Nils");
		user.setLogin("nils");
		user.setCreated(new DateTime(2012, 7, 31, 18, 0, 1));
		user.setEmail("nils@test.com");
		user.setAvatar("nils.png");
		user.setWeight(82);
		user.setHeight(186);
		userDb.put(4, user);

	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "bancha")
	public List<User> read() {
		return ImmutableList.copyOf(userDb.values());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public List<User> create(List<User> newUsers) {
		ImmutableList.Builder<User> result = ImmutableList.builder();
		for (User user : newUsers) {
			user.setId(maxId.incrementAndGet());
			userDb.put(user.getId(), user);
			result.add(user);
		}
		return result.build();
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

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "bancha")
	public ExtDirectFormPostResult submit(User user) {
		user.setId(maxId.incrementAndGet());
		userDb.put(user.getId(), user);

		return new ExtDirectFormPostResult(true);
	}

}

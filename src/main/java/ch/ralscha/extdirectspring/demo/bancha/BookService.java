package ch.ralscha.extdirectspring.demo.bancha;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

@Service
public class BookService {

	@Autowired
	private UserService userService;

	private final static Map<Integer, Book> bookDb = Maps.newConcurrentMap();

	@PostConstruct
	public void init() {
		for (int i = 0; i < 35; i++) {
			Book b = new Book();
			b.setId(i + 1);
			b.setTitle("Book " + (i + 1));
			b.setPublished(i % 3 == 0);
			b.setUser_id((i % 4) + 1);
			bookDb.put(b.getId(), b);
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "bancha")
	public List<Book> read() {
		return ImmutableList.copyOf(bookDb.values());
	}

}

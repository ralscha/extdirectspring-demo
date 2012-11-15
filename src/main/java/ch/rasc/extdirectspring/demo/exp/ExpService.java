package ch.rasc.extdirectspring.demo.exp;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.google.common.collect.ImmutableList;

@Service
public class ExpService {

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "exp")
	public List<Book> readBooks() {

		Book b = new Book();
		b.setTitle("Ext JS in Action, Second Edition");
		b.setPublisher("Manning");
		b.setIsbn("9781617290329");
		b.setPublishDate(new LocalDate(2013, 1, 31));
		b.setNumberOfPages(600);
		b.setRead(false);

		Author author1 = new Author();
		author1.setFirstName("Jesus");
		author1.setLastName("Garcia");
		author1.setTitle("Mr.");

		Author author2 = new Author();
		author2.setFirstName("Jacob K.");
		author2.setLastName("Andresen");
		author2.setTitle("Mr.");

		Author author3 = new Author();
		author3.setFirstName("Grgur");
		author3.setLastName("Grisogono");
		author3.setTitle("Mr.");

		b.setAuthors(ImmutableList.of(author1, author2, author3));

		return ImmutableList.of(b);
	}

}

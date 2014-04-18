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
package ch.rasc.extdirectspring.demo.exp;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class ExpService {

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "exp")
	public List<Book> readBooks() {

		Book b = new Book();
		b.setTitle("Ext JS in Action, Second Edition");
		b.setPublisher("Manning");
		b.setIsbn("9781617290329");
		b.setPublishDate(LocalDate.of(2013, 1, 31));
		b.setNumberOfPages(600);
		b.setRead(false);

		return Collections.singletonList(b);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "exp")
	public List<Author> readAuthors() {
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

		return Arrays.asList(author1, author2, author3);
	}

}

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
package ch.rasc.extdirectspring.demo.bancha;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.extdirectspring.demo.util.PropertyComparatorFactory;

@Service
public class ArticleService {

	@Autowired
	private UserService userService;

	private final static Map<Integer, Article> articleDb = new ConcurrentHashMap<>();

	private final static AtomicInteger maxId = new AtomicInteger(35);

	@PostConstruct
	public void init() {
		for (int i = 0; i < 35; i++) {
			Article a = new Article();
			a.setId(i + 1);
			a.setTitle("Title " + (i + 1));
			a.setBody("This is the body " + (i + 1));
			a.setDate(LocalDateTime.of(2012, 11, i % 30 + 1, 11, 51, 55));
			a.setPublished(i % 3 == 0);
			a.setUser_id(i % 4 + 1);
			articleDb.put(a.getId(), a);
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "bancha")
	public ExtDirectStoreResult<Article> read(ExtDirectStoreReadRequest request) {

		Collection<Article> result = articleDb.values();
		int totalSize = result.size();

		Stream<Article> resultStream = result.stream();

		Comparator<Article> comparator = PropertyComparatorFactory
				.createComparatorFromSorters(request.getSorters());
		if (comparator != null) {
			resultStream = resultStream.sorted(comparator);
		}

		if (request.getStart() != null && request.getLimit() != null) {
			resultStream = resultStream.skip(request.getStart())
					.limit(request.getLimit());
		}

		return new ExtDirectStoreResult<>(totalSize, resultStream.collect(Collectors
				.toList()));
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public ExtDirectStoreResult<Article> create(List<Article> newArticles) {
		return new ExtDirectStoreResult<>(newArticles.stream().map(a -> {
			a.setId(maxId.incrementAndGet());
			articleDb.put(a.getId(), a);
			return a;
		}).collect(Collectors.toList()));
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public ExtDirectStoreResult<Article> update(List<Article> updatedArticles) {
		for (Article article : updatedArticles) {
			articleDb.put(article.getId(), article);
		}
		return new ExtDirectStoreResult<>(updatedArticles);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public void destroy(List<Article> destroyArticles) {
		for (Article article : destroyArticles) {
			articleDb.remove(article.getId());
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "bancha")
	public ExtDirectFormPostResult submit(Article article) {
		article.setId(maxId.incrementAndGet());
		articleDb.put(article.getId(), article);

		return new ExtDirectFormPostResult(true);
	}
}

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
package ch.rasc.extdirectspring.demo.bancha;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.extdirectspring.demo.util.PropertyOrderingFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

@Service
public class ArticleService {

	@Autowired
	private UserService userService;

	private final static Map<Integer, Article> articleDb = Maps.newConcurrentMap();

	private final static AtomicInteger maxId = new AtomicInteger(35);

	@PostConstruct
	public void init() {
		for (int i = 0; i < 35; i++) {
			Article a = new Article();
			a.setId(i + 1);
			a.setTitle("Title " + (i + 1));
			a.setBody("This is the body " + (i + 1));
			a.setDate(new DateTime(2012, 11, i % 30 + 1, 11, 51, 55));
			a.setPublished(i % 3 == 0);
			a.setUser_id((i % 4) + 1);
			articleDb.put(a.getId(), a);
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "bancha")
	public ExtDirectStoreResult<Article> read(ExtDirectStoreReadRequest request) {

		List<Article> result = ImmutableList.copyOf(articleDb.values());

		Ordering<Article> ordering = PropertyOrderingFactory.createOrderingFromSorters(request.getSorters());
		if (ordering != null) {
			result = ordering.sortedCopy(result);
		}

		if (request.getStart() != null && request.getLimit() != null) {
			result = result.subList(request.getStart(),
					Math.min(articleDb.size(), request.getStart() + request.getLimit()));
		}

		return new ExtDirectStoreResult<>(articleDb.size(), result);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public List<Article> create(List<Article> newArticles) {
		ImmutableList.Builder<Article> result = ImmutableList.builder();
		for (Article article : newArticles) {
			article.setId(maxId.incrementAndGet());
			articleDb.put(article.getId(), article);
			result.add(article);
		}
		return result.build();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public List<Article> update(List<Article> updatedArticles) {
		for (Article article : updatedArticles) {
			articleDb.put(article.getId(), article);
		}
		return updatedArticles;
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

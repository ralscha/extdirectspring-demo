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
package ch.rasc.extdirectspring.demo.store;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rometools.rome.feed.synd.SyndEntry;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.extdirectspring.demo.FeedCache;
import ch.rasc.extdirectspring.demo.feed.FeedService;

@Service
public class DeliveryTimeService {

	private final FeedCache feedCache;

	@Autowired
	public DeliveryTimeService(FeedCache feedCache) {
		this.feedCache = feedCache;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "combobox")
	public DeliveryTime[] getDeliveryTimes() {
		return DeliveryTime.values();
	}

	public List<String> actresses = Arrays.asList("Natalie Portman", "Evangeline Lilly",
			"Kate Beckinsale", "Keira Knightley", "Zoe Saldana", "Olivia Wilde",
			"Morena Baccarin", "Olga Kurylenko", "Liv Tyler", "Charlize Theron",
			"Mila Kunis", "Katie Cassidy", "Rosario Dawson", "Christina Hendricks",
			"Kristen Bell", "Nicole Kidman", "Michelle Pfeiffer", "Monica Bellucci",
			"Emmanuelle Vaugier", "Angelina Jolie", "Eva Green", "Cate Blanchett",
			"Cobie Smulders", "Kelly Reilly", "Yvonne Strahovski", "Marion Cotillard",
			"Emily Blunt", "Connie Nielsen", "Ni Ni", "Carly Pope");

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "combobox")
	public List<Map<String, String>> readActresses(ExtDirectStoreReadRequest request) {

		StringFilter filter;

		Stream<String> stream = actresses.stream();
		if (StringUtils.hasText(request.getQuery())) {
			stream = stream.filter(a -> a.toLowerCase().contains(
					request.getQuery().toLowerCase()));
		}
		else if ((filter = request.getFirstFilterForField("actress")) != null) {
			stream = stream.filter(a -> a.toLowerCase().contains(
					filter.getValue().toLowerCase()));
		}

		return stream.map(a -> Collections.singletonMap("actress", a)).collect(
				Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "combobox")
	public List<ForumPost> readSenchaForum(ExtDirectStoreReadRequest request)
			throws MalformedURLException {

		List<SyndEntry> bugs = feedCache.getFeedInfo(FeedService.SENCHA_FORUM_EXT5_BUGS)
				.getSyndFeed().getEntries();
		List<SyndEntry> qas = feedCache.getFeedInfo(FeedService.SENCHA_FORUM_EXT5_QA)
				.getSyndFeed().getEntries();
		List<SyndEntry> all = new ArrayList<>();
		all.addAll(bugs);
		all.addAll(qas);

		return all.stream().map(ForumPost::new).collect(Collectors.toList());
		// for (SyndEntry entry : ) {
		//
		// Ext.define('KitchenSink.model.form.ForumPost', {
		// extend: 'KitchenSink.model.Base',
		// idProperty: 'post_id',
		// fields: [
		// {name: 'postId', mapping: 'post_id'},
		// {name: 'title', mapping: 'topic_title'},
		// {name: 'topicId', mapping: 'topic_id'},
		// {name: 'author', mapping: 'author'},
		// {name: 'lastPost', mapping: 'post_time', type: 'date', dateFormat:
		// 'timestamp'},
		// {name: 'excerpt', mapping: 'post_text'}
		// ]
		// });
	}
}

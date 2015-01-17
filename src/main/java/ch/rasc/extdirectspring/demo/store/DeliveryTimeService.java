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
package ch.rasc.extdirectspring.demo.store;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.extdirectspring.demo.FeedCache;
import ch.rasc.extdirectspring.demo.feed.FeedService;

import com.rometools.rome.feed.synd.SyndEntry;

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
	public List<ForumPost> readSenchaForum() throws MalformedURLException {

		List<SyndEntry> bugs = feedCache.getFeedInfo(FeedService.SENCHA_FORUM_EXT5_BUGS)
				.getSyndFeed().getEntries();
		List<SyndEntry> qas = feedCache.getFeedInfo(FeedService.SENCHA_FORUM_EXT5_QA)
				.getSyndFeed().getEntries();
		List<SyndEntry> all = new ArrayList<>();
		all.addAll(bugs);
		all.addAll(qas);

		return all.stream().map(ForumPost::new).collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "combobox")
	public List<Map<String, Object>> readCategory() {
		List<Map<String, Object>> result = new ArrayList<>();

		result.add(createMap(1, "Animals"));
		result.add(createMap(2, "Sports"));
		result.add(createMap(3, "Food"));

		return result;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "combobox")
	public List<Map<String, Object>> readDependant(ExtDirectStoreReadRequest request) {
		List<Map<String, Object>> result = new ArrayList<>();

		NumericFilter nf = request.getFirstFilterForField("categoryId");
		if (nf.getValue().intValue() == 1) {
			result.add(createMap(1, "Dog"));
			result.add(createMap(2, "Cat"));
			result.add(createMap(3, "Horse"));
		}
		else if (nf.getValue().intValue() == 2) {
			result.add(createMap(1, "Tennis"));
			result.add(createMap(2, "Swimming"));
			result.add(createMap(3, "Basketball"));
		}
		else if (nf.getValue().intValue() == 3) {
			result.add(createMap(1, "Pancakes"));
			result.add(createMap(2, "Pizza"));
			result.add(createMap(3, "Chinese"));
		}

		return result;
	}

	private static Map<String, Object> createMap(Integer id, String name) {
		Map<String, Object> entry = new HashMap<>();
		entry.put("id", id);
		entry.put("name", name);
		return entry;
	}

}

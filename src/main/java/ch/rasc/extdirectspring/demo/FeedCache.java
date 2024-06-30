/*
 * Copyright the original author or authors.
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
package ch.rasc.extdirectspring.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class FeedCache {

	private final static Map<String, SyndFeed> FEEDS = new ConcurrentHashMap<>();

	public FeedCache() {
	}

	public SyndFeed add(String url) {

		if (FEEDS.containsKey(url)) {
			return FEEDS.get(url);
		}

		return fetchFeed(url);
	}

	private static SyndFeed fetchFeed(String url) {
		try (CloseableHttpClient client = HttpClients.createMinimal()) {
			HttpUriRequest method = new HttpGet(url);
			try (CloseableHttpResponse response = client.execute(method);
					InputStream stream = response.getEntity().getContent()) {
				SyndFeedInput input = new SyndFeedInput();
				try (XmlReader reader = new XmlReader(stream)) {
					SyndFeed syndFeed = input.build(reader);
					FEEDS.put(url, syndFeed);
					return syndFeed;
				}
			}
		}

		catch (IllegalArgumentException | IOException | FeedException e) {
			LoggerFactory.getLogger(FeedCache.class).error("fetch rss feed", e);
			return null;
		}
	}

	public void remove(String url) {
		FEEDS.remove(url);
	}

	@Scheduled(initialDelay = 1000, fixedRate = 1000 * 60 * 60 * 24)
	public void refreshFeeds() {
		for (String url : FEEDS.keySet()) {
			FEEDS.put(url, fetchFeed(url));
		}
	}

	public SyndFeed getFeedInfo(String feedUrl) {
		return FEEDS.get(feedUrl);
	}
}

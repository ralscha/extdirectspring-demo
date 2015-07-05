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
package ch.rasc.extdirectspring.demo.feed;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rometools.fetcher.impl.SyndFeedInfo;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.extdirectspring.demo.FeedCache;

@Service
public class FeedService {

	public static final String SENCHA_FORUM_EXT5_QA = "https://www.sencha.com/forum/external.php?type=RSS2&forumids=129";

	public static final String SENCHA_FORUM_EXT5_BUGS = "https://www.sencha.com/forum/external.php?type=RSS2&forumids=130";

	private final FeedCache feedCache;

	private final Map<String, Feed> FEED_DB = new ConcurrentHashMap<>();

	@Autowired
	public FeedService(FeedCache feedCache) {
		this.feedCache = feedCache;

		Feed feed = new Feed(UUID.randomUUID().toString(), "Sencha Blog",
				"http://www.sencha.com/feed/");
		this.FEED_DB.put(feed.getId(), feed);
		feedCache.add(feed.getUrl());

		feed = new Feed(UUID.randomUUID().toString(), "Sencha Forums",
				"https://www.sencha.com/forum/external.php?type=RSS2");
		this.FEED_DB.put(feed.getId(), feed);
		feedCache.add(feed.getUrl());

		feed = new Feed(UUID.randomUUID().toString(), "Sencha Forum - Ext 5: Bugs",
				SENCHA_FORUM_EXT5_BUGS);
		this.FEED_DB.put(feed.getId(), feed);
		feedCache.add(feed.getUrl());

		feed = new Feed(UUID.randomUUID().toString(), "Sencha Forum - Ext 5: Q&A",
				SENCHA_FORUM_EXT5_QA);
		this.FEED_DB.put(feed.getId(), feed);
		feedCache.add(feed.getUrl());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "feed")
	public List<Feed> read() {
		return this.FEED_DB.values().stream().sorted(Comparator.comparing(Feed::getTitle))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "feed")
	public List<FeedItem> readFeedItem(String feedUrl) throws MalformedURLException {
		SyndFeedInfo info = this.feedCache.getFeedInfo(feedUrl);

		List<FeedItem> items = new ArrayList<>();
		int id = 0;
		for (SyndEntry entry : info.getSyndFeed().getEntries()) {
			FeedItem item = new FeedItem(id++, entry);
			items.add(item);
		}

		return items;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "feed")
	public void destroy(Feed destroyFeed) {
		Feed feed = this.FEED_DB.remove(destroyFeed.getId());
		this.feedCache.remove(feed.getUrl());
	}

	@ExtDirectMethod(group = "feed")
	public boolean verifyUrl(String feedUrl) {
		SyndFeed syndFeed = this.feedCache.add(feedUrl);
		if (syndFeed != null) {
			Feed newFeed = new Feed(UUID.randomUUID().toString(), syndFeed.getTitle(),
					feedUrl);
			this.FEED_DB.put(newFeed.getId(), newFeed);
			return true;
		}
		return false;
	}

}

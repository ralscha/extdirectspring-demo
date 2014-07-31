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

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.extdirectspring.demo.FeedCache;

import com.rometools.fetcher.impl.SyndFeedInfo;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

@Service
public class FeedService {

	private final FeedCache feedCache;

	private final Map<String, Feed> FEED_DB = new ConcurrentHashMap<>();

	@Autowired
	public FeedService(FeedCache feedCache) {
		this.feedCache = feedCache;

		Feed feed = new Feed(UUID.randomUUID().toString(), "Sencha Blog",
				"http://feeds.feedburner.com/extblog");
		FEED_DB.put(feed.getId(), feed);
		feedCache.add(feed.getUrl());

		feed = new Feed(UUID.randomUUID().toString(), "Sencha Forums",
				"http://sencha.com/forum/external.php?type=RSS2");
		FEED_DB.put(feed.getId(), feed);
		feedCache.add(feed.getUrl());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "feed")
	public List<Feed> read() {
		return FEED_DB.values().stream().sorted(Comparator.comparing(Feed::getTitle))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "feed")
	public List<FeedItem> readFeedItem(String feedUrl) throws MalformedURLException {
		SyndFeedInfo info = feedCache.getFeedInfo(feedUrl);

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
		Feed feed = FEED_DB.remove(destroyFeed.getId());
		feedCache.remove(feed.getUrl());
	}

	@ExtDirectMethod(group = "feed")
	public boolean verifyUrl(String feedUrl) {
		SyndFeed syndFeed = feedCache.add(feedUrl);
		if (syndFeed != null) {
			Feed newFeed = new Feed(UUID.randomUUID().toString(), syndFeed.getTitle(),
					feedUrl);
			FEED_DB.put(newFeed.getId(), newFeed);
			return true;
		}
		return false;
	}

}

package ch.rasc.extdirectspring.demo.feed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.jooq.lambda.Unchecked;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.FetcherException;
import com.rometools.fetcher.impl.FeedFetcherCache;
import com.rometools.fetcher.impl.HashMapFeedInfoCache;
import com.rometools.fetcher.impl.HttpURLFeedFetcher;
import com.rometools.fetcher.impl.SyndFeedInfo;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;

@Service
public class FeedService {

	private final static FeedFetcherCache FEED_CACHE = HashMapFeedInfoCache.getInstance();

	private final static Map<String, Feed> FEED_DB = new ConcurrentHashMap<>();
	{
		Feed feed = new Feed(UUID.randomUUID().toString(), "Sencha Blog",
				"http://feeds.feedburner.com/extblog");
		FEED_DB.put(feed.getId(), feed);

		feed = new Feed(UUID.randomUUID().toString(), "Sencha Forums",
				"http://sencha.com/forum/external.php?type=RSS2");
		FEED_DB.put(feed.getId(), feed);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "feed")
	public List<Feed> read() {
		return FEED_DB.values().stream().sorted(Comparator.comparing(Feed::getTitle))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "feed")
	public List<FeedItem> readFeedItem(String feedUrl) throws MalformedURLException {
		SyndFeedInfo info = FEED_CACHE.getFeedInfo(new URL(feedUrl));

		List<FeedItem> items = new ArrayList<>();
		int id = 0;
		for (SyndEntry entry : info.getSyndFeed().getEntries()) {
			FeedItem item = new FeedItem(id++, entry);
			items.add(item);
		}

		return items;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "feed")
	public Feed create(Feed newFeed) {
		newFeed.setId(UUID.randomUUID().toString());
		FEED_DB.put(newFeed.getId(), newFeed);
		return newFeed;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "feed")
	public void destroy(Feed destroyFeed) {
		FEED_DB.remove(destroyFeed.getId());
	}

	@ExtDirectMethod(group = "feed")
	public boolean verifyUrl(String feedUrl) {

		if (FEED_DB.values().stream().anyMatch(f -> f.getUrl().equals(feedUrl))) {
			return true;
		}

		try {
			FeedFetcher feedFetcher = new HttpURLFeedFetcher(FEED_CACHE);
			SyndFeed synFeed = feedFetcher.retrieveFeed(new URL(feedUrl));

			Feed newFeed = new Feed(UUID.randomUUID().toString(), synFeed.getTitle(),
					feedUrl);
			FEED_DB.put(newFeed.getId(), newFeed);
			return true;
		}
		catch (FetcherException | IllegalArgumentException | IOException | FeedException e) {
			return false;
		}
	}

	@Scheduled(initialDelay = 3000, fixedRate = 1000 * 60 * 60 * 24)
	public void refreshFeeds() {
		FeedFetcher feedFetcher = new HttpURLFeedFetcher(FEED_CACHE);
		FEED_DB.values().stream().map(Unchecked.function(f -> new URL(f.getUrl())))
				.forEach(Unchecked.consumer(u -> feedFetcher.retrieveFeed(u)));
	}

}

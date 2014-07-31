package ch.rasc.extdirectspring.demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.FetcherException;
import com.rometools.fetcher.impl.HttpURLFeedFetcher;
import com.rometools.fetcher.impl.LinkedHashMapFeedInfoCache;
import com.rometools.fetcher.impl.SyndFeedInfo;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;

@Service
public class FeedCache {

	private final static Map<String, SyndFeed> FEEDS = new ConcurrentHashMap<>();

	public FeedCache() {
		((LinkedHashMapFeedInfoCache) LinkedHashMapFeedInfoCache.getInstance())
				.setMaxEntries(2000);
	}

	public SyndFeed add(String url) {

		if (FEEDS.containsKey(url)) {
			return FEEDS.get(url);
		}

		try {
			FeedFetcher feedFetcher = new HttpURLFeedFetcher(
					LinkedHashMapFeedInfoCache.getInstance());
			SyndFeed syndFeed = feedFetcher.retrieveFeed(new URL(url));

			FEEDS.put(url, syndFeed);
			return syndFeed;
		}
		catch (FetcherException | IllegalArgumentException | IOException | FeedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void remove(String url) {
		FEEDS.remove(url);
	}

	@Scheduled(initialDelay = 1000, fixedRate = 1000 * 60 * 60 * 24)
	public void refreshFeeds() {
		HttpURLFeedFetcher feedFetcher = new HttpURLFeedFetcher(
				LinkedHashMapFeedInfoCache.getInstance());

		for (String url : FEEDS.keySet()) {
			try {
				feedFetcher.retrieveFeed(new URL(url));
			}
			catch (IllegalArgumentException | IOException | FeedException
					| FetcherException e) {
				LoggerFactory.getLogger(FeedCache.class).error("refreshFeeds", e);
			}
		}
	}

	public SyndFeedInfo getFeedInfo(String feedUrl) throws MalformedURLException {
		return LinkedHashMapFeedInfoCache.getInstance().getFeedInfo(new URL(feedUrl));
	}
}

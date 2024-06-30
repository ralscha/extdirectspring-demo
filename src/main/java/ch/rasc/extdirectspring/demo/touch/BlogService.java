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
package ch.rasc.extdirectspring.demo.touch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.extdirectspring.demo.FeedCache;

@Service
public class BlogService {

	private static final String FEED_URL = "https://www.sencha.com/feed/";

	private final FeedCache feedCache;

	public BlogService(FeedCache feedCache) {
		this.feedCache = feedCache;
		feedCache.add(FEED_URL);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "blog")
	public List<Post> getBlogPosts() throws IllegalArgumentException {
		List<Post> posts = new ArrayList<>();

		for (SyndEntry entry : this.feedCache.getFeedInfo(FEED_URL).getEntries()) {

			Post post = new Post();
			post.setTitle(entry.getTitle());
			post.setLeaf(true);

			if (!entry.getContents().isEmpty()) {
				post.setContent(entry.getContents().iterator().next().getValue());
			}
			else {
				post.setContent(entry.getDescription().getValue());
			}

			posts.add(post);
		}

		return posts;

	}

}

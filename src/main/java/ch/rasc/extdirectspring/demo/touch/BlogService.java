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
package ch.rasc.extdirectspring.demo.touch;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class BlogService {

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "blog")
	public List<Post> getBlogPosts() throws IllegalArgumentException, FeedException,
			IOException {
		List<Post> posts = new ArrayList<>();

		URL feedUrl = new URL("http://feeds.feedburner.com/SenchaBlog");

		SyndFeedInput input = new SyndFeedInput();
		try (XmlReader reader = new XmlReader(feedUrl)) {
			SyndFeed feed = input.build(reader);

			List<SyndEntry> entries = feed.getEntries();
			for (SyndEntry entry : entries) {

				Post post = new Post();
				post.setTitle(entry.getTitle());
				post.setLeaf(true);
				post.setContent(((SyndContentImpl) entry.getContents().iterator().next())
						.getValue());
				posts.add(post);
			}
		}
		return posts;

	}

}

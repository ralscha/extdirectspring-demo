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
package ch.rasc.extdirectspring.demo.feed;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rometools.rome.feed.synd.SyndEntry;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeSerializer;

public class FeedItem {

	private final int id;

	private final String title;

	private final String author;

	private final String link;

	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	private final LocalDateTime pubDate;

	private final String description;

	private final String content;

	public FeedItem(int id, SyndEntry entry) {
		this.id = id;
		this.title = entry.getTitle();
		this.author = entry.getAuthor();
		this.link = entry.getLink();

		if (entry.getPublishedDate() != null) {
			this.pubDate = LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(),
					ZoneOffset.UTC);
		}
		else {
			this.pubDate = null;
		}

		if (entry.getDescription() != null) {
			this.description = entry.getDescription().getValue();
		}
		else {
			this.description = null;
		}

		if (!entry.getContents().isEmpty()) {
			this.content = entry.getContents().iterator().next().getValue();
		}
		else if (entry.getDescription() != null) {
			this.content = entry.getDescription().getValue();
		}
		else {
			this.content = null;
		}
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getLink() {
		return this.link;
	}

	public LocalDateTime getPubDate() {
		return this.pubDate;
	}

	public String getDescription() {
		return this.description;
	}

	public String getContent() {
		return this.content;
	}

}

/**
 * Copyright 2010-2017 Ralph Schaer <ralphschaer@gmail.com>
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeSerializer;

public class ForumPost {

	public final String excerpt;

	public final String id;

	public final String title;

	public final String author;

	public final String link;

	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	public final LocalDateTime pubDate;

	public ForumPost(SyndEntry syndEntry) {
		this.id = UUID.randomUUID().toString();
		this.title = syndEntry.getTitle();
		this.author = syndEntry.getAuthor();
		this.link = syndEntry.getLink();
		this.pubDate = LocalDateTime.ofInstant(syndEntry.getPublishedDate().toInstant(),
				ZoneOffset.UTC);
		SyndContent description = syndEntry.getDescription();
		if (description != null) {
			this.excerpt = description.getValue();
		}
		else {
			this.excerpt = null;
		}
	}

}

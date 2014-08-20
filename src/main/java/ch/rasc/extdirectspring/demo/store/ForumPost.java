package ch.rasc.extdirectspring.demo.store;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import ch.rasc.extdirectspring.demo.util.ISO8601LocalDateTimeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

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

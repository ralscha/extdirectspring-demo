package ch.ralscha.extdirectspring.demo.exp;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.LocalDate;

import ch.ralscha.extdirectspring.demo.util.ISO8601LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Book {

	private final static AtomicInteger idGen = new AtomicInteger();

	private final int id = idGen.incrementAndGet();

	private String title;

	private String publisher;

	private String isbn;

	private LocalDate publishDate;

	private int numberOfPages;

	private boolean read;

	private List<Author> authors;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

}

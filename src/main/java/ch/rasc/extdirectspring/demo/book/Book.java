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
package ch.rasc.extdirectspring.demo.book;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Contains some data to represent a book. POJO class
 *
 * @author Loiane Groner http://loianegroner.com
 */
@JsonAutoDetect
public class Book {

	private int id;

	private String title;

	private String publisher;

	private String ISBN10;

	private String ISBN13;

	private String link;

	private String description;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getISBN10() {
		return this.ISBN10;
	}

	public void setISBN10(String iSBN10) {
		this.ISBN10 = iSBN10;
	}

	public String getISBN13() {
		return this.ISBN13;
	}

	public void setISBN13(String iSBN13) {
		this.ISBN13 = iSBN13;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
/**
 * Copyright 2010-2017 the original author or authors.
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

import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class NotesDb {

	private final AtomicInteger lastId = new AtomicInteger(0);

	private final ConcurrentHashMap<Integer, Note> map = new ConcurrentHashMap<>();

	public void addOrUpdate(Note note) {
		if (note.getId() == null || note.getId() < 0) {
			int id = this.lastId.incrementAndGet();
			note.setId(id);
			this.map.put(id, note);
		}
		else {
			this.map.put(note.getId(), note);
		}
	}

	public void delete(Note note) {
		this.map.remove(note.getId());
	}

	@PostConstruct
	public void addTestData() {
		Note n = new Note();
		n.setDateCreated(LocalDate.now());
		n.setTitle("Test Note");
		n.setNarrative("This is a simple test note");
		addOrUpdate(n);

		n = new Note();
		n.setDateCreated(LocalDate.now().plusDays(1));
		n.setTitle("Test Note 2 ");
		n.setNarrative("This is a second test note");
		addOrUpdate(n);

		n = new Note();
		n.setDateCreated(LocalDate.now().plusDays(2));
		n.setTitle("Test Note 3 ");
		n.setNarrative("This is a third test note");
		addOrUpdate(n);
	}

	public Collection<Note> readAll() {
		return this.map.values();
	}
}

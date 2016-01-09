/**
 * Copyright 2010-2016 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class SseController {
	private final static DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("uuuu/MM/dd 'at' hh:mm:ss");

	private final CopyOnWriteArraySet<Client> emitters;

	public SseController() {
		this.emitters = new CopyOnWriteArraySet<>();
	}

	@RequestMapping("/testsse")
	public SseEmitter testsse() {
		Client c = new Client();
		c.emitter = new SseEmitter();
		this.emitters.add(c);
		return c.emitter;
	}

	@Scheduled(fixedDelay = 3000)
	public void sendMessages() {
		if (this.emitters.isEmpty()) {
			return;
		}

		Set<Client> remove = new HashSet<>();
		for (Client c : this.emitters) {
			try {

				if (c.count < 20) {
					c.emitter.send("Successfully polled with EventSource at: "
							+ LocalDateTime.now().format(formatter));
					c.count++;
				}
				else {
					c.emitter.send("LAST: Successfully polled with EventSource at: "
							+ LocalDateTime.now().format(formatter));
					c.emitter.complete();
					remove.add(c);
				}
			}
			catch (Exception e) {
				c.emitter.completeWithError(e);
				remove.add(c);
			}
		}
		if (!remove.isEmpty()) {
			this.emitters.removeAll(remove);
		}

	}

	private static class Client {
		String id = UUID.randomUUID().toString();
		SseEmitter emitter;
		int count = 0;

		@Override
		public int hashCode() {
			return this.id.hashCode();
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (other == null) {
				return false;
			}
			if (getClass() != other.getClass()) {
				return false;
			}

			return this.id.equals(((Client) other).id);
		}

	}

}

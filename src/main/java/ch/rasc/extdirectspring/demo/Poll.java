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
package ch.rasc.extdirectspring.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class Poll {
	private final static DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("uuuu/MM/dd 'at' hh:mm:ss");

	@ExtDirectMethod(value = ExtDirectMethodType.POLL, event = "message",
			group = "example")
	public String handleMessagePoll() {
		return "Successfully polled at: " + LocalDateTime.now().format(formatter);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.POLL, event = "pollWithParams",
			group = "example")
	public String pollingWithParams(@RequestParam(value = "no") int no,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "dummy", defaultValue = "CH") String dummy,
			HttpServletRequest request) {
		return request.getRequestURI() + ":  POST PARAMETERS: no=" + no + ", name=" + name
				+ ", dummy=" + dummy;
	}

}
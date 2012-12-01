/**
 * Copyright 2010-2012 Ralph Schaer <ralphschaer@gmail.com>
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.SSEvent;
import ch.ralscha.extdirectspring.controller.SSEWriter;

@Service
public class Poll {

	@ExtDirectMethod(value = ExtDirectMethodType.POLL, event = "message", group = "example")
	public String handleMessagePoll() {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd 'at' hh:mm:ss");
		return "Successfully polled at: " + formatter.format(now);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.POLL, event = "pollWithParams", group = "example")
	public String pollingWithParams(@RequestParam(value = "no") int no, @RequestParam(value = "name") String name,
			@RequestParam(value = "dummy", defaultValue = "CH") String dummy, HttpServletRequest request) {
		return request.getRequestURI() + ":  POST PARAMETERS: no=" + no + ", name=" + name + ", dummy=" + dummy;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.SSE, group = "example")
	public SSEvent sse(SSEWriter sseWriter) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd 'at' hh:mm:ss");

		for (int i = 0; i < 20; i++) {

			try {
				sseWriter.write("Successfully polled with EventSource at: " + formatter.format(new Date()));
			} catch (Exception e) {
				return null;
			}

			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				return null;
			}
		}

		SSEvent event = new SSEvent();
		event.setRetry(3000);
		event.setData("LAST: Successfully polled with EventSource at: " + formatter.format(new Date()));
		return event;

	}

}
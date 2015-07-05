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
package ch.rasc.extdirectspring.demo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.SSEvent;
import ch.ralscha.extdirectspring.controller.SSEWriter;

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

	@ExtDirectMethod(value = ExtDirectMethodType.SSE, group = "example")
	public SSEvent sse(@RequestHeader("User-Agent") String userAgent,
			HttpServletResponse response, SSEWriter sseWriter) throws IOException {

		// we have to send 2K bytes of nothing first if the client is IE6-IE9.
		// IE10 no longer need this
		if (isIE(userAgent)) {
			System.out.println("Write FIX");
			writeIEHeadersAndPadding(response);
		}

		for (int i = 0; i < 20; i++) {

			try {
				sseWriter.write("Successfully polled with EventSource at: "
						+ LocalDateTime.now().format(formatter));
			}
			catch (Exception e) {
				return null;
			}

			try {
				TimeUnit.SECONDS.sleep(3);
			}
			catch (InterruptedException e) {
				return null;
			}
		}

		SSEvent event = new SSEvent();
		event.setRetry(3000);
		event.setData("LAST: Successfully polled with EventSource at: "
				+ LocalDateTime.now().format(formatter));
		return event;

	}

	private static void writeIEHeadersAndPadding(HttpServletResponse response)
			throws IOException {
		// If you use Yaffle / EventSource then these two headers are necessary
		// to make it work in IE6-IE9
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");

		// 2kb padding
		byte[] spaces = new byte[2048];
		Arrays.fill(spaces, (byte) ' ');

		response.getOutputStream().write(':');
		response.getOutputStream().write(spaces);
		response.getOutputStream().write('\n');
		response.getOutputStream().flush();
	}

	private static boolean isIE(String userAgent) {
		if (userAgent != null && userAgent.contains("MSIE ")) {
			String ver = userAgent.substring(userAgent.indexOf("MSIE ") + 5);
			ver = ver.substring(0, ver.indexOf(";")).trim();

			int version = Integer.parseInt(ver.substring(0, ver.indexOf(".")));
			if (version < 10) {
				return true;
			}
		}

		return false;

	}

}
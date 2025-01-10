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
package ch.rasc.extdirectspring.demo.simple;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import jakarta.servlet.http.HttpServletRequest;

@Service("simple")
public class SimpleMethodProvider {

	@ExtDirectMethod(group = "simple")
	public String method1() {
		return LocalDateTime.now().toString();
	}

	@ExtDirectMethod(group = "simple")
	public String method2(String ping) {
		return ping.toUpperCase();
	}

	@ExtDirectMethod(group = "simple")
	public String method3(String ping, HttpServletRequest request) {
		return ping + " request uri: " + request.getRequestURI();
	}

	@ExtDirectMethod(group = "simple")
	public String method4() {
		throw new IllegalArgumentException("shit happens");
	}

}

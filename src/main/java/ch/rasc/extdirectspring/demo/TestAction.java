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

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.rasc.extdirectspring.demo.todo.UserInfo;

@Service
public class TestAction {

	@ExtDirectMethod(group = "example")
	public long multiply(Long num) {
		if (num != null) {
			return num * 8;
		}
		return 0;
	}

	@ExtDirectMethod(group = "example")
	public String doEcho(String message) {
		return message;
	}
	
	@ExtDirectMethod(group = "example")
	public int wait45seconds(int a) throws InterruptedException {
		TimeUnit.SECONDS.sleep(45);
		return a+1;
	}
	
	
	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST)
	public ExtDirectFormPostResult wait45secondsFormPost(String name) throws InterruptedException {
		TimeUnit.SECONDS.sleep(45);

		ExtDirectFormPostResult result = new ExtDirectFormPostResult(true);
		result.addResultProperty("msg", name.toUpperCase());
		return result;
	}

}

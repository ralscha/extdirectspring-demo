/**
 * Copyright 2010-2017 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo.form;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;

@Service
public class Profile {

	public static class PhoneInfo {

		public String cell;

		public String office;

		public String home;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_LOAD, group = "form")
	public BasicInfo getBasicInfo(@RequestParam(value = "foo") String foo) {
		BasicInfo basicInfo = new BasicInfo();
		basicInfo.setFoo(foo);
		basicInfo.setName("Aaron Conran");
		basicInfo.setCompany("Ext JS, LLC");
		basicInfo.setEmail("aaron@extjs.com");
		return basicInfo;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_LOAD, group = "form")
	public PhoneInfo getPhoneInfo() {
		PhoneInfo phoneInfo = new PhoneInfo();
		phoneInfo.cell = "443-555-1234";
		phoneInfo.office = "1-800-CALLEXT";
		phoneInfo.home = "";
		return phoneInfo;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_LOAD, group = "form")
	public Map<String, String> getLocationInfo() {
		Map<String, String> data = new HashMap<>();
		data.put("street", "1234 Red Dog Rd.");
		data.put("city", "Seminole");
		data.put("state", "FL");
		data.put("zip", "33776");
		return data;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "form")
	public ExtDirectFormPostResult updateBasicInfo(@Valid BasicInfo basicInfo,
			BindingResult result) {

		if (!result.hasErrors()) {
			if (basicInfo.getEmail().equals("aaron@extjs.com")) {
				result.rejectValue("email", "", "email already taken");
			}
		}

		return new ExtDirectFormPostResult(result);
	}
}

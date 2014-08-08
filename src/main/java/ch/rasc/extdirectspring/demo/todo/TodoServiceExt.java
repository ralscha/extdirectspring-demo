/**
 * Copyright 2010-2014 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo.todo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.rasc.extdirectspring.demo.tree.TreeProvider.Node;

@Service
public class TodoServiceExt {

	@ExtDirectMethod(value = ExtDirectMethodType.SIMPLE_NAMED, group = "todo")
	public String testMe(@RequestParam(value = "test", required = false) Boolean test) {
		return "Hello World. Param test = " + test;
	}

	@ExtDirectMethod(group = "todo")
	public int testException() {
		return 1 / 0;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "todo")
	public ExtDirectFormPostResult upload(String description, MultipartFile afile) {
		ExtDirectFormPostResult result = new ExtDirectFormPostResult(true);
		result.addResultProperty("filename", afile.getOriginalFilename());
		result.addResultProperty("size", afile.getSize());
		result.addResultProperty("description", description);
		return result;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.SIMPLE_NAMED, group = "todo")
	public boolean login(HttpServletResponse response, String username, String password) {

		Cookie cookie = new Cookie("login", "successful");
		if ("admin".equals(username) && "admin".equals(password)) {
			cookie.setMaxAge(-120);
			response.addCookie(cookie);
			response.setHeader("anotherHeader", "10");

			return true;
		}
		cookie.setMaxAge(0);
		response.addCookie(cookie);

		return false;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.TREE_LOAD, group = "todo")
	public List<Node> getTree(@RequestParam(value = "node", required = false) String id) {

		List<Node> result = new ArrayList<>();

		if (id == null || id.equals("root")) {
			for (int i = 1; i <= 5; ++i) {
				Node node = new Node();
				node.id = "n" + i;
				node.text = "Node: " + i;
				node.leaf = false;
				result.add(node);
			}
		}
		else if (id.length() == 2) {
			String num = id.substring(1);
			for (int i = 1; i <= 5; ++i) {
				Node node = new Node();
				node.id = id + "_id" + i;
				node.text = "Node :" + num + "." + i;
				node.leaf = true;
				result.add(node);
			}
		}
		return result;
	}

}

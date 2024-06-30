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
package ch.rasc.extdirectspring.demo.upload;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;

@Service
public class UploadController {

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "upload")
	public ExtDirectFormPostResult uploadTest(
			@RequestParam("fileUpload") MultipartFile file) throws IOException {

		ExtDirectFormPostResult resp = new ExtDirectFormPostResult(true);

		if (file != null && !file.isEmpty()) {
			resp.addResultProperty("fileContents",
					new String(file.getBytes(), StandardCharsets.ISO_8859_1));
		}
		return resp;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "upload4")
	public ExtDirectFormPostResult uploadTest4(
			@RequestParam("fileUpload1") MultipartFile file1,
			@RequestParam("fileUpload2") MultipartFile file2) throws IOException {

		ExtDirectFormPostResult resp = new ExtDirectFormPostResult(true);

		if (file1 != null && !file1.isEmpty()) {
			System.out.println("File1 Name : " + file1.getName());
			System.out.println("File1 Bytes: " + file1.getSize());
		}

		if (file2 != null && !file2.isEmpty()) {
			System.out.println("File2 Name : " + file2.getName());
			System.out.println("File2 Bytes: " + file2.getSize());

			String txt = new String(file2.getBytes(), StandardCharsets.ISO_8859_1);
			System.out.println(txt);
			resp.addResultProperty("fileContents", txt);
		}

		return resp;
	}

}

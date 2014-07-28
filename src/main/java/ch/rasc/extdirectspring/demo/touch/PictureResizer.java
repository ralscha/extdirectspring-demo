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
package ch.rasc.extdirectspring.demo.touch;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PictureResizer {

	@RequestMapping(value = "/picresize", method = RequestMethod.GET)
	public void resize(@RequestParam("url") String url, @RequestParam(value = "width",
			required = false) Integer width, @RequestParam(value = "height",
			required = false) Integer height, HttpServletRequest request,
			final HttpServletResponse response) throws MalformedURLException, IOException {

		File servletTmpDir = (File) request.getServletContext().getAttribute(
				"javax.servlet.context.tempdir");
		File picturesDir = new File(servletTmpDir, "pictures");
		picturesDir.mkdirs();

		String sha = org.apache.commons.codec.digest.DigestUtils.sha256Hex(url);
		File pictureFile = new File(picturesDir, sha);

		if (!pictureFile.exists()) {
			try (InputStream input = new URL(url).openStream()) {
				Files.copy(input, pictureFile.toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			}
		}

		try (OutputStream out = response.getOutputStream()) {

			if (width != null && height != null) {
				BufferedImage image = ImageIO.read(pictureFile);
				if (image.getWidth() > width || image.getHeight() > height) {
					BufferedImage resizedImage = Scalr.resize(image,
							Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, height,
							Scalr.OP_ANTIALIAS);

					int pos = url.lastIndexOf(".");
					String format = url.substring(pos + 1).toUpperCase();

					File tempFile = File.createTempFile("resized", format);
					tempFile.deleteOnExit();
					ImageIO.write(resizedImage, format, tempFile);

					Files.copy(tempFile.toPath(), out);
					tempFile.delete();
				}
				else {
					Files.copy(pictureFile.toPath(), out);
				}
			}
			else {
				Files.copy(pictureFile.toPath(), out);
			}
		}

	}

}

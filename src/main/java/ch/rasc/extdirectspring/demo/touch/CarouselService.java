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
package ch.rasc.extdirectspring.demo.touch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.extdirectspring.demo.FeedCache;

import com.rometools.rome.feed.synd.SyndEntry;

@Service
public class CarouselService {

	private static final String RSS_URL = "http://www.acme.com/jef/apod/rss.xml?num=20";

	private static Pattern IMG_PATTERN = Pattern.compile(".*img src=\"([^\"]+)\".*",
			Pattern.DOTALL);

	private final FeedCache feedCache;

	@Autowired
	public CarouselService(FeedCache feedCache) {
		this.feedCache = feedCache;
		feedCache.add(RSS_URL);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "touchcarousel")
	public List<CarouselPicture> readPictures(HttpServletRequest request)
			throws IllegalArgumentException, IOException {

		List<CarouselPicture> pictures = new ArrayList<>();

		for (SyndEntry entry : this.feedCache.getFeedInfo(RSS_URL).getSyndFeed()
				.getEntries()) {
			CarouselPicture pic = new CarouselPicture();
			pic.setId(entry.getUri());
			pic.setAuthor(entry.getAuthor());
			pic.setLink(entry.getLink());
			pic.setTitle(entry.getTitle());

			Matcher matcher = IMG_PATTERN.matcher(entry.getDescription().getValue());
			if (matcher.matches()) {
				String imageUrl = matcher.group(1);
				if (imageUrl.startsWith("http://apod.nasa.gov/apod/http://")) {
					imageUrl = imageUrl.replace("http://apod.nasa.gov/apod/http://",
							"http://");
				}
				pic.setImage(request.getContextPath() + "/picresize?url=" + imageUrl);
			}
			pictures.add(pic);
		}

		return pictures;
	}

}

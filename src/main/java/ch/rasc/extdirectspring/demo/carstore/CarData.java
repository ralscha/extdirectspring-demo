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
package ch.rasc.extdirectspring.demo.carstore;

import java.util.Arrays;
import java.util.List;

public class CarData {

	private final String manufacturer;

	private final String model;

	private final int price;

	private final String wiki;

	private final String img;

	private final List<Quality> quality;

	public CarData(String manufacturer, String model, int price, String wiki, String img,
			Quality... quality) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.price = price;
		this.wiki = wiki;
		this.img = img;
		this.quality = Arrays.asList(quality);
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public String getModel() {
		return this.model;
	}

	public int getPrice() {
		return this.price;
	}

	public String getWiki() {
		return this.wiki;
	}

	public String getImg() {
		return this.img;
	}

	public List<Quality> getQuality() {
		return this.quality;
	}

}

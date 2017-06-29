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
package ch.rasc.extdirectspring.demo.infographic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class InfographicService {

	private final List<Unemployment> unemployments = new ArrayList<>();

	InfographicService() {
		this.unemployments.add(new Unemployment("year", 100));
		// Northeast region
		this.unemployments.add(new Unemployment("CT", 100, 0.2, 1.0, 2.6, 1.1, -0.4, -0.6,
				"Connecticut"));
		this.unemployments.add(
				new Unemployment("DE", 100, 0.0, 1.4, 3.0, 0.1, -0.6, -0.3, "Delaware"));
		this.unemployments.add(
				new Unemployment("ME", 100, 0.0, 0.7, 2.7, 0.1, -0.5, -0.5, "Maine"));
		this.unemployments.add(
				new Unemployment("MD", 100, -0.4, 0.9, 3.1, 0.5, -0.6, -0.4, "Maryland"));
		this.unemployments.add(new Unemployment("MA", 100, -0.3, 0.8, 2.9, 0.1, -1.0,
				-0.5, "Massachusetts"));
		this.unemployments.add(new Unemployment("NH", 100, 0.0, 0.4, 2.3, 0.0, -0.7, 0.0,
				"New Hampshire"));
		this.unemployments.add(new Unemployment("NJ", 100, -0.3, 1.2, 3.5, 0.6, -0.3, 0.0,
				"New Jersey"));
		this.unemployments.add(
				new Unemployment("NY", 100, 0.0, 0.8, 2.9, 0.3, -0.4, 0.3, "New York"));
		this.unemployments.add(new Unemployment("PA", 100, -0.1, 0.9, 2.6, 0.6, -0.5,
				-0.1, "Pennsylvania"));
		this.unemployments.add(new Unemployment("RI", 100, 0.1, 2.5, 3.2, 0.8, -0.5, -0.9,
				"Rhode Island"));
		this.unemployments.add(
				new Unemployment("VT", 100, 0.2, 0.6, 2.4, -0.5, -0.8, -0.7, "Vermont"));

		this.unemployments.add(new Unemployment("", 20));

		this.unemployments.add(
				new Unemployment("AL", 100, -0.1, 1.6, 4.7, -0.5, -0.7, -1.4, "Alabama"));
		this.unemployments.add(new Unemployment("DC", 100, -0.2, 1.1, 3.1, 0.4, 0.1, -1.1,
				"District of Columbia"));
		this.unemployments.add(
				new Unemployment("FL", 100, 0.7, 2.3, 4.1, 0.9, -1.0, -1.5, "Florida"));
		this.unemployments.add(
				new Unemployment("GA", 100, -0.1, 1.7, 3.4, 0.5, -0.3, -0.9, "Georgia"));
		this.unemployments.add(new Unemployment("LA", 100, -0.1, 0.6, 2.2, 0.8, -0.2,
				-0.7, "Louisiana"));
		this.unemployments.add(new Unemployment("MS", 100, -0.5, 0.5, 2.7, 1.1, 0.0, -0.9,
				"Mississippi"));
		this.unemployments.add(new Unemployment("NC", 100, 0.0, 1.5, 4.1, 0.4, -0.6, -1.0,
				"North Carolina"));
		this.unemployments.add(new Unemployment("SC", 100, -0.8, 1.2, 4.6, -0.3, -0.8,
				-1.3, "South Carolina"));
		this.unemployments.add(new Unemployment("TN", 100, -0.4, 1.8, 4.0, -0.7, -0.6,
				-1.1, "Tennessee"));
		this.unemployments.add(
				new Unemployment("VA", 100, 0.1, 0.9, 3.0, 0.1, -0.7, -0.5, "Virginia"));

		this.unemployments.add(new Unemployment("", 20));

		this.unemployments.add(new Unemployment("WI", 100, 0.1, 0.0, 3.9, -0.2, -1.0,
				-0.6, "Wisconsin"));
		this.unemployments.add(new Unemployment("WV", 100, -0.3, 0.0, 3.4, 0.9, -0.7,
				-0.6, "West Virginia"));
		this.unemployments.add(new Unemployment("SD", 100, -0.2, 0.1, 2.2, -0.1, -0.4,
				-0.5, "South Dakota"));
		this.unemployments.add(
				new Unemployment("OH", 100, 0.2, 1.0, 3.6, -0.2, -1.3, -1.3, "Ohio"));
		this.unemployments.add(new Unemployment("ND", 100, -0.1, 0.0, 1.0, -0.3, -0.4,
				-0.4, "North Dakota"));
		this.unemployments.add(
				new Unemployment("NE", 100, 0.0, 0.3, 1.4, 0.0, -0.2, -0.5, "Nebraska"));
		this.unemployments.add(
				new Unemployment("MO", 100, 0.2, 0.9, 3.5, -0.1, -0.8, -1.5, "Missouri"));
		this.unemployments.add(new Unemployment("MN", 100, 0.6, 0.7, 2.6, -0.6, -0.9,
				-0.9, "Minnesota"));
		this.unemployments.add(
				new Unemployment("MI", 100, 0.2, 1.2, 5.2, -0.8, -2.3, -1.3, "Michigan"));
		this.unemployments.add(new Unemployment("KY", 100, -0.3, 1.0, 3.7, -0.1, -0.7,
				-1.2, "Kentucky"));
		this.unemployments.add(
				new Unemployment("KS", 100, -0.3, 0.3, 2.7, 0.0, -0.6, -0.7, "Kansas"));
		this.unemployments
				.add(new Unemployment("IA", 100, 0.1, 0.2, 2.3, 0.0, -0.5, -0.6, "Iowa"));
		this.unemployments.add(
				new Unemployment("IN", 100, -0.4, 1.2, 4.5, -0.3, -1.2, -0.7, "Indiana"));
		this.unemployments.add(
				new Unemployment("IL", 100, 0.5, 1.3, 3.6, 0.5, -0.8, -0.8, "Illinois"));
		this.unemployments.add(
				new Unemployment("AR", 100, 0.0, 0.1, 2.1, 0.4, 0.1, -0.5, "Arkansas"));

		this.unemployments.add(new Unemployment("", 20));

		// Southwest region
		this.unemployments.add(
				new Unemployment("AZ", 100, -0.4, 2.3, 3.8, 0.6, -1.0, -1.1, "Arizona"));
		this.unemployments.add(new Unemployment("CA", 100, 0.5, 1.8, 4.1, 1.1, -0.6, -1.4,
				"California"));
		this.unemployments.add(
				new Unemployment("CO", 100, -0.5, 1.0, 3.3, 0.9, -0.5, -0.7, "Colorado"));
		this.unemployments.add(
				new Unemployment("HI", 100, 0.2, 1.4, 2.7, -0.1, -0.2, -0.8, "Hawaii"));
		this.unemployments.add(
				new Unemployment("NV", 100, 0.5, 2.4, 4.6, 2.1, -0.6, -1.7, "Nevada"));
		this.unemployments.add(new Unemployment("NM", 100, -0.6, 1.0, 2.4, 1.1, -0.4,
				-0.5, "New Mexico"));
		this.unemployments.add(
				new Unemployment("OK", 100, 0.0, -0.4, 3.0, 0.2, -1.0, -0.5, "Oklahoma"));
		this.unemployments.add(
				new Unemployment("TX", 100, -0.5, 0.5, 2.6, 0.7, -0.3, -1.1, "Texas"));
		this.unemployments.add(
				new Unemployment("UT", 100, -0.3, 0.7, 4.5, 0.3, -1.3, -1.4, "Utah"));

		this.unemployments.add(new Unemployment("", 20));

		// Northwest region
		this.unemployments.add(
				new Unemployment("AK", 100, -0.4, 0.3, 1.3, 0.3, -0.4, -0.7, "Alaska"));
		this.unemployments.add(
				new Unemployment("ID", 100, 0.0, 1.8, 2.6, 1.3, -0.3, -1.1, "Idaho"));
		this.unemployments.add(
				new Unemployment("MT", 100, 0.2, 1.1, 1.5, 0.7, -0.2, -0.5, "Montana"));
		this.unemployments.add(
				new Unemployment("OR", 100, -0.1, 1.3, 4.6, -0.3, -1.1, -0.9, "Oregon"));
		this.unemployments.add(new Unemployment("WA", 100, -0.3, 0.8, 3.9, 0.6, -0.7,
				-1.1, "Washington"));
		this.unemployments.add(
				new Unemployment("WY", 100, -0.4, 0.3, 3.2, 0.7, -0.9, -0.7, "Wyoming"));
	}

	@ExtDirectMethod(group = "infographic")
	public List<Unemployment> getUnemployments() {
		return this.unemployments;
	}

}

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
package ch.rasc.extdirectspring.demo.chart;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class ReportService {

	private final Random randomGenerator = new Random();

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "pie")
	public List<SeasonData> getSeasonData(
			@RequestParam(defaultValue = "50") int minRandomValue,
			@RequestParam(value = "maxRandomValue",
					defaultValue = "250") int maxRandomValue) {

		return Arrays.asList(
				new SeasonData("Summer",
						this.randomGenerator.nextInt(maxRandomValue - minRandomValue)
								+ minRandomValue),
				new SeasonData("Fall",
						this.randomGenerator.nextInt(maxRandomValue - minRandomValue)
								+ minRandomValue),
				new SeasonData("Winter",
						this.randomGenerator.nextInt(maxRandomValue - minRandomValue)
								+ minRandomValue),
				new SeasonData("Spring",
						this.randomGenerator.nextInt(maxRandomValue - minRandomValue)
								+ minRandomValue));
	}
}

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
package ch.rasc.extdirectspring.demo.chart;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class ChartService {

	private static final Random rnd = new Random();

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "area")
	public List<AreaData> getAreaData() {
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		return Arrays.stream(months).filter(StringUtils::hasText).map(AreaData::new)
				.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "live",
			synchronizeOnSession = true)
	public List<SiteInfo> getSiteInfo(HttpSession session) {

		List<SiteInfo> siteInfo = (List<SiteInfo>) session.getAttribute("siteInfos");
		if (siteInfo == null) {
			siteInfo = new ArrayList<>();
			session.setAttribute("siteInfos", siteInfo);

			LocalDate ld = LocalDate.of(2011, 1, 1);
			siteInfo.add(new SiteInfo(ld, rnd.nextInt(100) + 1, rnd.nextInt(100) + 1,
					rnd.nextInt(100) + 1));
		}
		else {
			SiteInfo lastSiteInfo = siteInfo.get(siteInfo.size() - 1);

			LocalDate nextDate = lastSiteInfo.getDate().plusDays(1);
			int nextVisits = Math.min(100, Math.max(
					(int) (lastSiteInfo.getVisits() + (rnd.nextDouble() - 0.5) * 20), 0));
			int nextViews = Math.min(100, Math.max(
					(int) (lastSiteInfo.getViews() + (rnd.nextDouble() - 0.5) * 10), 0));
			int nextVeins = Math.min(100, Math.max(
					(int) (lastSiteInfo.getVeins() + (rnd.nextDouble() - 0.5) * 20), 0));
			siteInfo.add(new SiteInfo(nextDate, nextVisits, nextViews, nextVeins));

			if (siteInfo.size() > 7) {
				siteInfo.remove(0);
			}
		}

		return siteInfo;
	}

	@ExtDirectMethod(group = "live")
	public SiteInfo getNextSiteInfo(HttpSession session) {
		SiteInfo lastSiteInfo = (SiteInfo) session.getAttribute("lastSiteInfo");

		SiteInfo newSiteInfo;

		if (lastSiteInfo == null) {
			LocalDate ld = LocalDate.of(2011, 1, 1);
			newSiteInfo = new SiteInfo(ld, rnd.nextInt(100) + 1, rnd.nextInt(100) + 1,
					rnd.nextInt(100) + 1);
		}
		else {

			LocalDate nextDate = lastSiteInfo.getDate().plusDays(1);
			int nextVisits = Math.min(100, Math.max(lastSiteInfo.getVisits()
					+ ThreadLocalRandom.current().nextInt(-20, 20), 0));
			int nextViews = Math.min(100, Math.max(lastSiteInfo.getVisits()
					+ ThreadLocalRandom.current().nextInt(-10, 10), 0));
			int nextVeins = Math.min(100, Math.max(lastSiteInfo.getVisits()
					+ ThreadLocalRandom.current().nextInt(-20, 20), 0));

			newSiteInfo = new SiteInfo(nextDate, nextVisits, nextViews, nextVeins);
		}

		session.setAttribute("lastSiteInfo", newSiteInfo);
		return newSiteInfo;

	}

	@ExtDirectMethod(group = "live")
	public List<SiteInfo> getFirst10SiteInfos(HttpSession session) {
		session.removeAttribute("lastSiteInfo");
		List<SiteInfo> result = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			result.add(getNextSiteInfo(session));
		}
		return result;
	}
}

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
package ch.rasc.extdirectspring.demo.are;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.extclassgenerator.ModelGenerator;
import ch.rasc.extclassgenerator.OutputFormat;
import ch.rasc.extdirectspring.demo.util.Constants;

@Controller
public class AreService {

	static final class WITH_HISTORY_VIEW {
	}

	static final class WO_HISTORY_VIEW {
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "are",
			jsonView = WO_HISTORY_VIEW.class)
	public Collection<Company> read(@RequestParam(required = false) String coId,
			ExtDirectStoreReadRequest edsRequest) {
		if (coId != null) {
			return Collections.singleton(companyDb.get(coId));
		}

		if (!edsRequest.getFilters().isEmpty()) {
			return companyDb.values().stream()
					.filter(getPredicates(edsRequest.getFilters()))
					.collect(Collectors.toList());
		}

		return companyDb.values();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "are",
			jsonView = WITH_HISTORY_VIEW.class)
	public Collection<Company> readWithHistory(
			@RequestParam(required = false) String coId,
			ExtDirectStoreReadRequest edsRequest) {
		return read(coId, edsRequest);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "are",
			jsonView = WO_HISTORY_VIEW.class)
	public Collection<History> historyRead(ExtDirectStoreReadRequest request) {
		StringFilter filter = request.getFirstFilterForField("companyId");
		if (filter != null) {
			return companyDb.get(filter.getValue()).getHistory();
		}

		return companyDb.values().stream().flatMap(c -> c.getHistory().stream())
				.collect(Collectors.toList());
	}

	private static Map<String, Company> companyDb;
	{
		Map<String, Company> builder = new HashMap<>();

		Company c = new Company("3m Co");
		builder.put(c.getCoId(), c);

		c = new Company("Alcoa Inc");
		builder.put(c.getCoId(), c);

		c = new Company("Altria Group Inc");
		builder.put(c.getCoId(), c);
		c = new Company("American Express Company");
		builder.put(c.getCoId(), c);
		c = new Company("American International Group, Inc.");
		builder.put(c.getCoId(), c);
		c = new Company("Boeing Co.");
		builder.put(c.getCoId(), c);
		c = new Company("Caterpillar Inc.");
		builder.put(c.getCoId(), c);
		c = new Company("Citigroup, Inc.");
		builder.put(c.getCoId(), c);
		c = new Company("E.I. du Pont de Nemours and Company");
		builder.put(c.getCoId(), c);
		c = new Company("Exxon Mobil Corp");
		builder.put(c.getCoId(), c);
		c = new Company("General Electric Company");
		builder.put(c.getCoId(), c);
		c = new Company("General Motors Corporation");
		builder.put(c.getCoId(), c);
		c = new Company("Hewlett-Packard Co.");
		builder.put(c.getCoId(), c);
		c = new Company("Honeywell Intl Inc");
		builder.put(c.getCoId(), c);
		c = new Company("Intel Corporation");
		builder.put(c.getCoId(), c);
		c = new Company("International Business Machines");
		builder.put(c.getCoId(), c);
		c = new Company("Johnson & Johnson");
		builder.put(c.getCoId(), c);
		c = new Company("JP Morgan & Chase & Co");
		builder.put(c.getCoId(), c);
		c = new Company("McDonald Corporation");
		builder.put(c.getCoId(), c);
		c = new Company("Merck & Co., Inc.");
		builder.put(c.getCoId(), c);
		c = new Company("Microsoft Corporation");
		builder.put(c.getCoId(), c);
		c = new Company("Pfizer Inc");
		builder.put(c.getCoId(), c);
		c = new Company("The Coca-Cola Company");
		builder.put(c.getCoId(), c);
		c = new Company("The Home Depot, Inc.");
		builder.put(c.getCoId(), c);
		c = new Company("The Procter & Gamble Company");
		builder.put(c.getCoId(), c);
		c = new Company("United Technologies Corporation");
		builder.put(c.getCoId(), c);
		c = new Company("Verizon Communications");
		builder.put(c.getCoId(), c);
		c = new Company("Wal-Mart Stores, Inc.");
		builder.put(c.getCoId(), c);

		companyDb = Collections.unmodifiableMap(builder);
	}

	@RequestMapping({ "/extjs42/associationrowexpander/models.js",
			"/extjs41/associationrowexpander/models.js" })
	public void models(HttpServletResponse response) throws IOException {
		String company = ModelGenerator.generateJavascript(Company.class,
				OutputFormat.EXTJS4, false);
		String history = ModelGenerator.generateJavascript(History.class,
				OutputFormat.EXTJS4, false);
		byte[] code = (company + history).getBytes(StandardCharsets.UTF_8);

		response.setContentType("application/javascript");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentLength(code.length);

		@SuppressWarnings("resource")
		OutputStream out = response.getOutputStream();
		out.write(code);
	}

	public Predicate<Company> getPredicates(Collection<Filter> filters) {

		Predicate<Company> predicates = c -> true;

		for (Filter filter : filters) {

			String value = ((StringFilter) filter).getValue().trim().toLowerCase();
			if (filter.getField().equals("company")) {
				predicates = predicates.and(c -> c.getCompany().toLowerCase()
						.startsWith(value));
			}
			else if (filter.getField().equals("price")) {
				predicates = predicates.and(c -> c.getPrice().compareTo(
						new BigDecimal(value)) == 0);
			}
			else if (filter.getField().equals("change")) {
				predicates = predicates.and(c -> c.getChange().compareTo(
						new BigDecimal(value)) == 0);
			}
			else if (filter.getField().equals("pctChange")) {
				predicates = predicates.and(c -> c.getPctChange().compareTo(
						new BigDecimal(value)) == 0);
			}
			else if (filter.getField().equals("lastChange")) {
				LocalDate localDateValue;
				if (value.length() > 10) {
					localDateValue = LocalDate.parse(value.substring(0, 10));
				}
				else {
					localDateValue = LocalDate.parse(value, Constants.MMddYYYY_FORMATTER);
				}
				predicates = predicates.and(c -> c.getLastChange().toLocalDate()
						.compareTo(localDateValue) == 0);
			}
		}

		return predicates;
	}

}

/**
 * Copyright 2010-2013 Ralph Schaer <ralphschaer@gmail.com>
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
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.ralscha.extdirectspring.generator.ModelGenerator;
import ch.ralscha.extdirectspring.generator.OutputFormat;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

@Controller
public class AreService {

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "are")
	public ImmutableCollection<Company> read(@RequestParam(required = false) String coId,
			ExtDirectStoreReadRequest edsRequest) {
		if (coId != null) {
			return ImmutableList.of(companyDb.get(coId));
		}

		if (!edsRequest.getFilters().isEmpty()) {
			List<Predicate<Company>> predicates = getPredicates(edsRequest.getFilters());
			return ImmutableList.copyOf(Collections2.filter(companyDb.values(), Predicates.and(predicates)));
		}

		return companyDb.values();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "are")
	public ImmutableCollection<History> historyRead(ExtDirectStoreReadRequest request) {
		StringFilter filter = request.getFirstFilterForField("companyId");
		if (filter != null) {
			return ImmutableList.copyOf(companyDb.get(filter.getValue()).getHistory());
		}

		ImmutableList.Builder<History> allHistory = ImmutableList.builder();
		for (Company company : companyDb.values()) {
			allHistory.addAll(company.getHistory());
		}

		return allHistory.build();
	}

	private static ImmutableMap<String, Company> companyDb;
	{
		ImmutableMap.Builder<String, Company> builder = ImmutableMap.builder();

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

		companyDb = builder.build();
	}

	@RequestMapping({ "/extjs42/associationrowexpander/models.js", "/extjs41/associationrowexpander/models.js" })
	public void models(HttpServletResponse response) throws IOException {
		String company = ModelGenerator.generateJavascript(Company.class, OutputFormat.EXTJS4, false);
		String history = ModelGenerator.generateJavascript(History.class, OutputFormat.EXTJS4, false);

		byte[] code = (company + history).getBytes(StandardCharsets.UTF_8);

		response.setContentType("application/javascript");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentLength(code.length);

		@SuppressWarnings("resource")
		OutputStream out = response.getOutputStream();
		out.write(code);
	}

	public List<Predicate<Company>> getPredicates(Collection<Filter> filters) {

		List<Predicate<Company>> predicates = Lists.newArrayList();
		for (Filter filter : filters) {

			if (filter.getField().equals("company")) {
				predicates.add(new CompanyPredicate(((StringFilter) filter).getValue()));
			} else if (filter.getField().equals("price")) {
				predicates.add(new PricePredicate(((StringFilter) filter).getValue()));
			} else if (filter.getField().equals("change")) {
				predicates.add(new ChangePredicate(((StringFilter) filter).getValue()));
			} else if (filter.getField().equals("pctChange")) {
				predicates.add(new PctChangePredicate(((StringFilter) filter).getValue()));
			} else if (filter.getField().equals("lastChange")) {
				predicates.add(new LastChangePredicate(((StringFilter) filter).getValue()));
			}
		}

		return predicates;

	}

	private static class CompanyPredicate implements Predicate<Company> {
		private final String value;

		CompanyPredicate(String value) {
			this.value = value;
		}

		@Override
		public boolean apply(Company company) {
			return company.getCompany().toLowerCase().startsWith(value.trim().toLowerCase());
		}
	}

	private static class PricePredicate implements Predicate<Company> {
		private final BigDecimal value;

		PricePredicate(String value) {
			this.value = new BigDecimal(value);
		}

		@Override
		public boolean apply(Company company) {
			return company.getPrice().compareTo(value) == 0;
		}

	}

	private static class ChangePredicate implements Predicate<Company> {

		private final BigDecimal value;

		ChangePredicate(String value) {
			this.value = new BigDecimal(value);
		}

		@Override
		public boolean apply(Company company) {
			return company.getChange().compareTo(value) == 0;
		}
	}

	private static class PctChangePredicate implements Predicate<Company> {
		private final BigDecimal value;

		PctChangePredicate(String value) {
			this.value = new BigDecimal(value);
		}

		@Override
		public boolean apply(Company company) {
			return company.getPctChange().compareTo(value) == 0;
		}
	}

	private static class LastChangePredicate implements Predicate<Company> {
		private final DateTimeFormatter formatter1 = DateTimeFormat.forPattern("MM/dd/yyyy");

		private final DateTimeFormatter formatter2 = DateTimeFormat.forPattern("yyy-MM-dd");

		private final LocalDate value;

		LastChangePredicate(String value) {
			if (value.length() > 10) {
				this.value = LocalDate.parse(value.substring(0, 10), formatter2);
			} else {
				this.value = LocalDate.parse(value, formatter1);
			}
		}

		@Override
		public boolean apply(Company company) {
			return company.getLastChange().toLocalDate().compareTo(value) == 0;
		}
	}
}

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
package ch.rasc.extdirectspring.demo.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;
import ch.ralscha.extdirectspring.filter.BooleanFilter;
import ch.ralscha.extdirectspring.filter.Comparison;
import ch.ralscha.extdirectspring.filter.DateFilter;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.ListFilter;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.extdirectspring.demo.util.Constants;

@Service
public class CompanyDataBean {

	@Autowired
	private Resource randomdata;

	private Map<Integer, Company> companies;

	@PostConstruct
	public void readData() throws IOException {
		Random rand = new Random();

		this.companies = new HashMap<>();
		try (InputStream is = this.randomdata.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				CSVReader reader = new CSVReader(br, '|')) {

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				Company company = new Company();
				company.setId(Integer.parseInt(nextLine[0]));
				company.setCompany(nextLine[2]);

				company.setDate(LocalDate.of(rand.nextInt(55) + 1950,
						rand.nextInt(12) + 1, rand.nextInt(27) + 1));
				company.setPrice(new BigDecimal(rand.nextFloat() * 100).setScale(2,
						RoundingMode.HALF_EVEN));
				company.setSize(SizeEnum.values()[rand.nextInt(4)]);
				company.setVisible(rand.nextBoolean());

				this.companies.put(company.getId(), company);
			}
		}
	}

	public Collection<Company> findAllCompanies() {
		return Collections.unmodifiableCollection(this.companies.values());
	}

	public List<Company> findCompanies(Collection<Filter> filters) {

		Predicate<Company> predicates = c -> true;
		for (Filter filter : filters) {
			if (filter.getField().equals("company")) {
				String value = ((StringFilter) filter).getValue().trim().toLowerCase();
				predicates = predicates.and(c -> c.getCompany().toLowerCase()
						.startsWith(value));
			}
			else if (filter.getField().equals("visible")) {
				boolean flag = ((BooleanFilter) filter).getValue();
				predicates = predicates.and(c -> c.isVisible() == flag);
			}
			else if (filter.getField().equals("id")) {
				NumericFilter numericFilter = (NumericFilter) filter;
				predicates = predicates.and(new IdPredicate(
						numericFilter.getComparison(), numericFilter.getValue()));
			}
			else if (filter.getField().equals("price")) {
				NumericFilter numericFilter = (NumericFilter) filter;
				predicates = predicates.and(new PricePredicate(numericFilter
						.getComparison(), numericFilter.getValue()));
			}
			else if (filter.getField().equals("size")) {
				@SuppressWarnings("unchecked")
				ListFilter<String> listFilter = (ListFilter<String>) filter;
				predicates = predicates.and(c -> listFilter.getValue().contains(
						c.getSize().getLabel()));
			}
			else if (filter.getField().equals("date")) {
				LocalDate ld;
				Comparison comparison;
				if (filter instanceof DateFilter) {
					DateFilter dateFilter = (DateFilter) filter;
					comparison = dateFilter.getComparison();
					ld = LocalDate.parse(dateFilter.getValue(),
							Constants.MMddYYYY_FORMATTER);
				}
				else if (filter instanceof StringFilter) {
					StringFilter dateFilter = (StringFilter) filter;
					comparison = dateFilter.getComparison();
					ld = LocalDate.parse(dateFilter.getValue(),
							Constants.MMddYYYY_FORMATTER);
				}
				else {
					NumericFilter numericFilter = (NumericFilter) filter;
					comparison = numericFilter.getComparison();
					ld = LocalDateTime.ofInstant(
							Instant.ofEpochMilli(numericFilter.getValue().longValue()),
							ZoneOffset.UTC).toLocalDate();
				}
				predicates = predicates.and(new DatePredicate(comparison, ld));
			}
		}

		return this.companies.values().stream().filter(predicates)
				.collect(Collectors.toList());
	}

	private static class IdPredicate implements Predicate<Company> {
		private final Comparison comparison;

		private final Number value;

		IdPredicate(Comparison comparison, Number value) {
			this.comparison = comparison;
			this.value = value;
		}

		@SuppressWarnings("incomplete-switch")
		@Override
		public boolean test(Company company) {
			switch (this.comparison) {
			case EQUAL:
				return company.getId() == this.value.intValue();
			case GREATER_THAN:
				return company.getId() > this.value.intValue();
			case LESS_THAN:
				return company.getId() < this.value.intValue();
			}
			return false;
		}
	}

	private static class PricePredicate implements Predicate<Company> {
		private final Comparison comparison;

		private final Number value;

		PricePredicate(Comparison comparison, Number value) {
			this.comparison = comparison;
			this.value = value;
		}

		@SuppressWarnings("incomplete-switch")
		@Override
		public boolean test(Company company) {
			switch (this.comparison) {
			case EQUAL:
				return company.getPrice().compareTo(
						new BigDecimal(this.value.doubleValue()).setScale(2,
								RoundingMode.HALF_UP)) == 0;
			case GREATER_THAN:
				return company.getPrice().compareTo(
						new BigDecimal(this.value.doubleValue()).setScale(2,
								RoundingMode.HALF_UP)) > 0;
			case LESS_THAN:
				return company.getPrice().compareTo(
						new BigDecimal(this.value.doubleValue()).setScale(2,
								RoundingMode.HALF_UP)) < 0;
			}
			return false;
		}
	}

	private static class DatePredicate implements Predicate<Company> {
		private final Comparison comparison;

		private final LocalDate value;

		DatePredicate(Comparison comparison, LocalDate value) {
			this.comparison = comparison;
			this.value = value;
		}

		@SuppressWarnings("incomplete-switch")
		@Override
		public boolean test(Company company) {
			switch (this.comparison) {
			case EQUAL:
				return company.getDate().compareTo(this.value) == 0;
			case GREATER_THAN:
				return company.getDate().compareTo(this.value) > 0;
			case LESS_THAN:
				return company.getDate().compareTo(this.value) < 0;
			}
			return false;
		}
	}

}

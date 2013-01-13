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
package ch.rasc.extdirectspring.demo.filterbar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.Comparison;
import ch.ralscha.extdirectspring.filter.DateFilter;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.ListFilter;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.extdirectspring.demo.util.PropertyOrderingFactory;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

@Service
public class CompanyService {

	private final static ImmutableList<Company> companies;

	private final static Map<String, Collection<String>> autoStore;

	static {
		ImmutableList.Builder<Company> builder = ImmutableList.builder();
		builder.add(new Company(1, "3m Co", "71.72", "0.02", "0.03", "Category 1", "Country 1", 0));
		builder.add(new Company(2, "Alcoa Inc", "29.01", "0.42", "1.47", "Category 2", "Country 10", 0));
		builder.add(new Company(3, "Altria Group Inc", "83.81", "0.28", "0.34", "Category 3", "Country 9", 0));
		builder.add(new Company(4, "American Express Company", "52.55", "0.01", "0.02", "Category 4", "Country 6", 1));
		builder.add(new Company(5, "American International Group, Inc.", "64.13", "0.31", "0.49", "Category 5",
				"Country 1", 0));
		builder.add(new Company(6, "AT&T Inc.", "31.61", "-0.48", "-1.54", "Category 6", "Country 10", 0));
		builder.add(new Company(7, "Boeing Co.", "75.43", "0.53", "0.71", "Category 1", "Country 1", 1));
		builder.add(new Company(8, "Caterpillar Inc.", "67.27", "0.92", "1.39", "Category 1", "Country 1", 0));
		builder.add(new Company(9, "Citigroup, Inc.", "49.37", "0.02", "0.04", "Category 7", "Country 5", 0));
		builder.add(new Company(10, "E.I. du Pont de Nemours and Company", "40.48", "0.51", "1.28", "Category 1",
				"Country 9", 0));
		builder.add(new Company(11, "Exxon Mobil Corp", "68.1", "-0.43", "-0.64", "Category 1", "Country 1", 0));
		builder.add(new Company(12, "General Electric Company", "34.14", "-0.08", "-0.23", "Category 2", "Country 200",
				0));
		builder.add(new Company(13, "General Motors Corporation", "30.27", "1.09", "3.74", "Category 1", "Country 1", 0));
		builder.add(new Company(14, "Hewlett-Packard Co.", "36.53", "-0.03", "-0.08", "Category 4", "Country 1", 1));
		builder.add(new Company(15, "Honeywell Intl Inc", "38.77", "0.05", "0.13", null, "Country 10", 0));
		builder.add(new Company(16, "Intel Corporation", "19.88", "0.31", "1.58", "Category 1", "Country 1", 0));
		builder.add(new Company(17, "International Business Machines", "81.41", "0.44", "0.54", "Category 1",
				"Country 3", 0));
		builder.add(new Company(18, "Johnson & Johnson", "64.72", "0.06", "0.09", "Category 1", null, 0));
		builder.add(new Company(19, "JP Morgan & Chase & Co", "45.73", "0.07", "0.15", "Category 1", "Country 1", 1));
		builder.add(new Company(20, "McDonald\"s Corporation", "36.76", "0.86", "2.40", "Category 2", "Country 1", 0));
		builder.add(new Company(21, "Merck & Co., Inc.", "40.96", "0.41", "1.01", "Category 1", "Country 7", 0));
		builder.add(new Company(22, "Microsoft Corporation", "25.84", "0.14", "0.54", "Category 1", "Country 1", 0));
		builder.add(new Company(23, "Pfizer Inc", "27.96", "0.4", "1.45", "Category 5", "Country 5", 0));
		builder.add(new Company(24, "The Coca-Cola Company", "45.07", "0.26", "0.58", "Category 1", "Country 400", 1));
		builder.add(new Company(25, "The Home Depot, Inc.", "34.64", "0.35", "1.02", "Category 1", "Country 1", 0));
		builder.add(new Company(26, "The Procter & Gamble Company", "61.91", "0.01", "0.02", "Category 3", "Country 2",
				1));
		builder.add(new Company(27, "United Technologies Corporation", "63.26", "0.55", "0.88", "Category 5", null, 0));
		builder.add(new Company(28, "Verizon Communications", "35.57", "0.39", "1.11", "Category 6", "Country 0", 0));
		builder.add(new Company(29, "Wal-Mart Stores, Inc.", "45.45", "0.73", "1.63", "Category 1", "Country 1", 0));
		companies = builder.build();

		autoStore = Maps.newHashMap();

		SortedSet<String> countries = Sets.newTreeSet();
		SortedSet<String> categories = Sets.newTreeSet();
		for (Company company : companies) {
			if (company.getCountry() != null) {
				countries.add(company.getCountry());
			} else {
				countries.add("");
			}
			if (company.getCategory() != null) {
				categories.add(company.getCategory());
			} else {
				categories.add("");
			}
		}

		autoStore.put("country", countries);
		autoStore.put("category", categories);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "company")
	public CompanyStoreReadResult read(ExtDirectStoreReadRequest request) {

		Ordering<Company> ordering = getOrdering(request);
		List<Predicate<Company>> predicates = getPredicates(request.getFilters());

		Collection<Company> resultCompanies = Collections2.filter(companies, Predicates.and(predicates));
		if (ordering != null) {
			resultCompanies = ordering.sortedCopy(resultCompanies);
		}

		CompanyStoreReadResult result = new CompanyStoreReadResult(resultCompanies);
		result.setAutoStores(autoStore);

		return result;
	}

	private static Ordering<Company> getOrdering(ExtDirectStoreReadRequest request) {
		Ordering<Company> ordering = null;
		for (SortInfo sortInfo : request.getSorters()) {

			Ordering<Company> colOrder = PropertyOrderingFactory.createOrdering(sortInfo.getProperty());
			if (colOrder != null) {
				if (sortInfo.getDirection() == SortDirection.DESCENDING) {
					colOrder = colOrder.reverse();
				}
				if (ordering == null) {
					ordering = colOrder;
				} else {
					ordering = ordering.compound(colOrder);
				}
			}
		}
		return ordering;
	}

	public List<Predicate<Company>> getPredicates(Collection<Filter> filters) {

		List<Predicate<Company>> predicates = Lists.newArrayList();
		for (Filter filter : filters) {

			if (filter.getField().equals("id")) {
				NumericFilter numericFilter = (NumericFilter) filter;
				predicates.add(new IdPredicate(numericFilter.getComparison(), numericFilter.getValue()));
			} else if (filter.getField().equals("company")) {
				predicates.add(new CompanyPredicate(((StringFilter) filter).getValue()));
			} else if (filter.getField().equals("country")) {
				predicates.add(new CountryPredicate(((ListFilter) filter).getValue()));
			} else if (filter.getField().equals("category")) {
				predicates.add(new CategoryPredicate(((ListFilter) filter).getValue()));
			} else if (filter.getField().equals("price")) {
				NumericFilter numericFilter = (NumericFilter) filter;
				predicates.add(new PricePredicate(numericFilter.getComparison(), numericFilter.getValue()));
			} else if (filter.getField().equals("change")) {
				NumericFilter numericFilter = (NumericFilter) filter;
				predicates.add(new ChangePredicate(numericFilter.getComparison(), numericFilter.getValue()));
			} else if (filter.getField().equals("pctChange")) {
				NumericFilter numericFilter = (NumericFilter) filter;
				predicates.add(new PctChangePredicate(numericFilter.getComparison(), numericFilter.getValue()));
			} else if (filter.getField().equals("lastChange")) {
				DateFilter dateFilter = (DateFilter) filter;
				predicates.add(new LastChangePredicate(dateFilter.getComparison(), dateFilter.getValue()));
			}
		}

		return predicates;

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
		public boolean apply(Company company) {
			switch (comparison) {
			case EQUAL:
				return company.getId() == value.intValue();
			case GREATER_THAN_OR_EQUAL:
				return company.getId() >= value.intValue();
			case LESS_THAN_OR_EQUAL:
				return company.getId() <= value.intValue();
			case NOT_EQUAL:
				return company.getId() != value.intValue();
			}
			return false;
		}
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

	private static class CountryPredicate implements Predicate<Company> {
		private static final String empty = "###NULL###";

		private final Set<String> values;

		CountryPredicate(List<String> values) {
			this.values = Sets.newHashSet(values);
		}

		@Override
		public boolean apply(Company company) {
			return (company.getCountry() == null && values.contains(empty)) || values.contains(company.getCountry());
		}
	}

	private static class CategoryPredicate implements Predicate<Company> {
		private static final String empty = "###NULL###";

		private final Set<String> values;

		CategoryPredicate(List<String> values) {
			this.values = Sets.newHashSet(values);
		}

		@Override
		public boolean apply(Company company) {
			return (company.getCategory() == null && values.contains(empty)) || values.contains(company.getCategory());
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
		public boolean apply(Company company) {
			BigDecimal v = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
			switch (comparison) {
			case EQUAL:
				return company.getPrice().compareTo(v) == 0;
			case GREATER_THAN_OR_EQUAL:
				return company.getPrice().compareTo(v) >= 0;
			case LESS_THAN_OR_EQUAL:
				return company.getPrice().compareTo(v) <= 0;
			case NOT_EQUAL:
				return company.getPrice().compareTo(v) != 0;
			}
			return false;
		}

	}

	private static class ChangePredicate implements Predicate<Company> {

		private final Comparison comparison;

		private final Number value;

		ChangePredicate(Comparison comparison, Number value) {
			this.comparison = comparison;
			this.value = value;
		}

		@SuppressWarnings("incomplete-switch")
		@Override
		public boolean apply(Company company) {
			BigDecimal v = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
			switch (comparison) {
			case EQUAL:
				return company.getChange().compareTo(v) == 0;
			case GREATER_THAN_OR_EQUAL:
				return company.getChange().compareTo(v) >= 0;
			case LESS_THAN_OR_EQUAL:
				return company.getChange().compareTo(v) <= 0;
			case NOT_EQUAL:
				return company.getChange().compareTo(v) != 0;
			}
			return false;
		}
	}

	private static class PctChangePredicate implements Predicate<Company> {
		private final Comparison comparison;

		private final Number value;

		PctChangePredicate(Comparison comparison, Number value) {
			this.comparison = comparison;
			this.value = value;
		}

		@SuppressWarnings("incomplete-switch")
		@Override
		public boolean apply(Company company) {
			BigDecimal v = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
			switch (comparison) {
			case EQUAL:
				return company.getPctChange().compareTo(v) == 0;
			case GREATER_THAN_OR_EQUAL:
				return company.getPctChange().compareTo(v) >= 0;
			case LESS_THAN_OR_EQUAL:
				return company.getPctChange().compareTo(v) <= 0;
			case NOT_EQUAL:
				return company.getPctChange().compareTo(v) != 0;
			}
			return false;
		}
	}

	private static class LastChangePredicate implements Predicate<Company> {
		private final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

		private final Comparison comparison;

		private final LocalDate value;

		LastChangePredicate(Comparison comparison, String value) {
			this.comparison = comparison;
			this.value = LocalDate.parse(value, formatter);
		}

		@SuppressWarnings("incomplete-switch")
		@Override
		public boolean apply(Company company) {
			switch (comparison) {
			case EQUAL:
				return company.getLastChange().compareTo(value) == 0;
			case GREATER_THAN_OR_EQUAL:
				return company.getLastChange().compareTo(value) > 0;
			case LESS_THAN_OR_EQUAL:
				return company.getLastChange().compareTo(value) < 0;
			case NOT_EQUAL:
				return company.getLastChange().compareTo(value) != 0;
			}
			return false;
		}
	}
}

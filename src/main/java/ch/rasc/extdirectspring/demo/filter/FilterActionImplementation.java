/**
 * Copyright 2010-2016 Ralph Schaer <ralphschaer@gmail.com>
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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.extdirectspring.demo.util.PropertyComparatorFactory;

@Service
public class FilterActionImplementation implements FilterActionInterface {

	@Autowired
	private CompanyDataBean dataBean;

	@Override
	public ExtDirectStoreResult<Company> load(ExtDirectStoreReadRequest request,
			@RequestParam(required = false) String dRif) {

		Stream<Company> companiesStream;
		int totalSize;

		if (!request.getFilters().isEmpty()) {
			List<Company> companies = this.dataBean.findCompanies(request.getFilters());
			totalSize = companies.size();
			companiesStream = companies.stream();
		}
		else {
			Collection<Company> companies = this.dataBean.findAllCompanies();
			totalSize = companies.size();
			companiesStream = companies.stream();
		}

		Comparator<Company> comparator = PropertyComparatorFactory
				.createComparatorFromSorters(request.getSorters());
		if (comparator != null) {
			companiesStream = companiesStream.sorted(comparator);
		}

		if (request.getStart() != null && request.getLimit() != null) {
			companiesStream = companiesStream.skip(request.getStart())
					.limit(request.getLimit());
		}

		return new ExtDirectStoreResult<>(totalSize,
				companiesStream.collect(Collectors.toList()));
	}

}

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
package ch.rasc.extdirectspring.demo.filterbar;

import java.util.Collection;
import java.util.Map;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;

public class CompanyStoreReadResult extends ExtDirectStoreResult<Company> {

	private Map<String, Collection<String>> autoStores;

	public CompanyStoreReadResult() {
		super();
	}

	public CompanyStoreReadResult(Collection<Company> records) {
		super(records);
	}

	public CompanyStoreReadResult(Company record) {
		super(record);
	}

	public CompanyStoreReadResult(Company[] record) {
		super(record);
	}

	public CompanyStoreReadResult(Integer total, Collection<Company> records, Boolean success) {
		super(total, records, success);
	}

	public CompanyStoreReadResult(Integer total, Collection<Company> records) {
		super(total, records);
	}

	public Map<String, Collection<String>> getAutoStores() {
		return autoStores;
	}

	public void setAutoStores(Map<String, Collection<String>> autoStores) {
		this.autoStores = autoStores;
	}

}

package ch.ralscha.extdirectspring.demo.filterbar;

import java.util.Collection;
import java.util.Map;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

public class CompanyStoreReadResult extends ExtDirectStoreReadResult<Company> {

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

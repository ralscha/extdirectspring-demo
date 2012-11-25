package ch.rasc.extdirectspring.demo.are;

import java.util.UUID;

import org.joda.time.DateTime;

import ch.ralscha.extdirectspring.generator.Model;
import ch.ralscha.extdirectspring.generator.ModelAssociation;
import ch.ralscha.extdirectspring.generator.ModelAssociationType;
import ch.ralscha.extdirectspring.generator.ModelField;
import ch.rasc.extdirectspring.demo.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Model(value = "Are.History", readMethod = "areService.historyRead")
public class History {
	private final String id = UUID.randomUUID().toString();

	@ModelField(dateFormat = "c")
	private final DateTime date;

	private final String text;

	private final String companyId;

	@JsonIgnore
	@ModelAssociation(value = ModelAssociationType.BELONGS_TO, foreignKey = "companyId")
	private final Company company;

	@JsonIgnore
	@ModelAssociation(value = ModelAssociationType.HAS_ONE, foreignKey = "companyId")
	private Company companyOne;

	public History(Company company, DateTime date, String text) {
		this.company = company;
		this.companyId = company.getCoId();
		this.date = date;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getDate() {
		return date;
	}

	public String getText() {
		return text;
	}

	public String getCompanyId() {
		return companyId;
	}

	public Company getCompany() {
		return company;
	}

}

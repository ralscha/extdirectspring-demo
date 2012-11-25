package ch.rasc.extdirectspring.demo.are;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.joda.time.DateTime;

import ch.ralscha.extdirectspring.generator.Model;
import ch.ralscha.extdirectspring.generator.ModelAssociation;
import ch.ralscha.extdirectspring.generator.ModelAssociationType;
import ch.ralscha.extdirectspring.generator.ModelField;
import ch.rasc.extdirectspring.demo.util.ISO8601DateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;

@Model(value = "Are.Company", readMethod = "areService.read", idProperty = "coId")
public class Company {

	private final String coId = UUID.randomUUID().toString();

	private final String company;

	private final BigDecimal price;

	private final BigDecimal change;

	private final BigDecimal pctChange;

	@ModelField(dateFormat = "c")
	private final DateTime lastChange;

	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = History.class, foreignKey = "companyId", autoLoad = true)
	@JsonIgnore
	private final List<History> history = Lists.newArrayList();

	public Company(String company) {
		this.company = company;

		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		this.price = new BigDecimal(rnd.nextDouble(10, 90)).setScale(2, RoundingMode.HALF_DOWN);
		this.change = new BigDecimal(rnd.nextDouble(0.1, 2.9)).setScale(2, RoundingMode.HALF_DOWN);
		this.pctChange = new BigDecimal(rnd.nextDouble(0.1, 9.9)).setScale(2, RoundingMode.HALF_DOWN);
		this.lastChange = DateTime.now().minusSeconds(rnd.nextInt(86400));

		addHistory(new History(this, DateTime.now().minusSeconds(rnd.nextInt(3600)), "Test"));
		addHistory(new History(this, DateTime.now().minusSeconds(rnd.nextInt(86400)), "Initial"));

	}

	public String getCoId() {
		return coId;
	}

	public String getCompany() {
		return company;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getChange() {
		return change;
	}

	public BigDecimal getPctChange() {
		return pctChange;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getLastChange() {
		return lastChange;
	}

	public List<History> getHistory() {
		return history;
	}

	public void addHistory(History h) {
		this.history.add(h);
	}
}

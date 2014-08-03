package ch.rasc.extdirectspring.demo.execdashboard;

import java.math.BigDecimal;
import java.util.List;

public class Kpi {
	private final int id;
	private final String category;
	private final String name;
	private final BigDecimal data1;
	private final BigDecimal data2;

	public Kpi(List<Object> line) {
		// [0,"clicks", "May 2010", 53.34321776, 100.1152082 ],
		id = (Integer) line.get(0);
		category = (String) line.get(1);
		name = (String) line.get(2);
		data1 = (BigDecimal)line.get(3);
		data2 = (BigDecimal)line.get(4);

	}

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getData1() {
		return data1;
	}

	public BigDecimal getData2() {
		return data2;
	}

}

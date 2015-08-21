package ch.rasc.extdirectspring.demo.infographic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class InfographicService {

	private final List<Unemployment> unemployments = new ArrayList<>();

	InfographicService() {
		unemployments.add(new Unemployment("year", 100));
		// Northeast region
		unemployments.add(new Unemployment("CT", 100, 0.2, 1.0, 2.6, 1.1, -0.4, -0.6,
				"Connecticut"));
		unemployments.add(
				new Unemployment("DE", 100, 0.0, 1.4, 3.0, 0.1, -0.6, -0.3, "Delaware"));
		unemployments.add(
				new Unemployment("ME", 100, 0.0, 0.7, 2.7, 0.1, -0.5, -0.5, "Maine"));
		unemployments.add(
				new Unemployment("MD", 100, -0.4, 0.9, 3.1, 0.5, -0.6, -0.4, "Maryland"));
		unemployments.add(new Unemployment("MA", 100, -0.3, 0.8, 2.9, 0.1, -1.0, -0.5,
				"Massachusetts"));
		unemployments.add(new Unemployment("NH", 100, 0.0, 0.4, 2.3, 0.0, -0.7, 0.0,
				"New Hampshire"));
		unemployments.add(new Unemployment("NJ", 100, -0.3, 1.2, 3.5, 0.6, -0.3, 0.0,
				"New Jersey"));
		unemployments.add(
				new Unemployment("NY", 100, 0.0, 0.8, 2.9, 0.3, -0.4, 0.3, "New York"));
		unemployments.add(new Unemployment("PA", 100, -0.1, 0.9, 2.6, 0.6, -0.5, -0.1,
				"Pennsylvania"));
		unemployments.add(new Unemployment("RI", 100, 0.1, 2.5, 3.2, 0.8, -0.5, -0.9,
				"Rhode Island"));
		unemployments.add(
				new Unemployment("VT", 100, 0.2, 0.6, 2.4, -0.5, -0.8, -0.7, "Vermont"));

		unemployments.add(new Unemployment("", 20));

		unemployments.add(
				new Unemployment("AL", 100, -0.1, 1.6, 4.7, -0.5, -0.7, -1.4, "Alabama"));
		unemployments.add(new Unemployment("DC", 100, -0.2, 1.1, 3.1, 0.4, 0.1, -1.1,
				"District of Columbia"));
		unemployments.add(
				new Unemployment("FL", 100, 0.7, 2.3, 4.1, 0.9, -1.0, -1.5, "Florida"));
		unemployments.add(
				new Unemployment("GA", 100, -0.1, 1.7, 3.4, 0.5, -0.3, -0.9, "Georgia"));
		unemployments.add(new Unemployment("LA", 100, -0.1, 0.6, 2.2, 0.8, -0.2, -0.7,
				"Louisiana"));
		unemployments.add(new Unemployment("MS", 100, -0.5, 0.5, 2.7, 1.1, 0.0, -0.9,
				"Mississippi"));
		unemployments.add(new Unemployment("NC", 100, 0.0, 1.5, 4.1, 0.4, -0.6, -1.0,
				"North Carolina"));
		unemployments.add(new Unemployment("SC", 100, -0.8, 1.2, 4.6, -0.3, -0.8, -1.3,
				"South Carolina"));
		unemployments.add(new Unemployment("TN", 100, -0.4, 1.8, 4.0, -0.7, -0.6, -1.1,
				"Tennessee"));
		unemployments.add(
				new Unemployment("VA", 100, 0.1, 0.9, 3.0, 0.1, -0.7, -0.5, "Virginia"));

		unemployments.add(new Unemployment("", 20));

		unemployments.add(new Unemployment("WI", 100, 0.1, 0.0, 3.9, -0.2, -1.0, -0.6,
				"Wisconsin"));
		unemployments.add(new Unemployment("WV", 100, -0.3, 0.0, 3.4, 0.9, -0.7, -0.6,
				"West Virginia"));
		unemployments.add(new Unemployment("SD", 100, -0.2, 0.1, 2.2, -0.1, -0.4, -0.5,
				"South Dakota"));
		unemployments.add(
				new Unemployment("OH", 100, 0.2, 1.0, 3.6, -0.2, -1.3, -1.3, "Ohio"));
		unemployments.add(new Unemployment("ND", 100, -0.1, 0.0, 1.0, -0.3, -0.4, -0.4,
				"North Dakota"));
		unemployments.add(
				new Unemployment("NE", 100, 0.0, 0.3, 1.4, 0.0, -0.2, -0.5, "Nebraska"));
		unemployments.add(
				new Unemployment("MO", 100, 0.2, 0.9, 3.5, -0.1, -0.8, -1.5, "Missouri"));
		unemployments.add(new Unemployment("MN", 100, 0.6, 0.7, 2.6, -0.6, -0.9, -0.9,
				"Minnesota"));
		unemployments.add(
				new Unemployment("MI", 100, 0.2, 1.2, 5.2, -0.8, -2.3, -1.3, "Michigan"));
		unemployments.add(new Unemployment("KY", 100, -0.3, 1.0, 3.7, -0.1, -0.7, -1.2,
				"Kentucky"));
		unemployments.add(
				new Unemployment("KS", 100, -0.3, 0.3, 2.7, 0.0, -0.6, -0.7, "Kansas"));
		unemployments
				.add(new Unemployment("IA", 100, 0.1, 0.2, 2.3, 0.0, -0.5, -0.6, "Iowa"));
		unemployments.add(
				new Unemployment("IN", 100, -0.4, 1.2, 4.5, -0.3, -1.2, -0.7, "Indiana"));
		unemployments.add(
				new Unemployment("IL", 100, 0.5, 1.3, 3.6, 0.5, -0.8, -0.8, "Illinois"));
		unemployments.add(
				new Unemployment("AR", 100, 0.0, 0.1, 2.1, 0.4, 0.1, -0.5, "Arkansas"));

		unemployments.add(new Unemployment("", 20));

		// Southwest region
		unemployments.add(
				new Unemployment("AZ", 100, -0.4, 2.3, 3.8, 0.6, -1.0, -1.1, "Arizona"));
		unemployments.add(new Unemployment("CA", 100, 0.5, 1.8, 4.1, 1.1, -0.6, -1.4,
				"California"));
		unemployments.add(
				new Unemployment("CO", 100, -0.5, 1.0, 3.3, 0.9, -0.5, -0.7, "Colorado"));
		unemployments.add(
				new Unemployment("HI", 100, 0.2, 1.4, 2.7, -0.1, -0.2, -0.8, "Hawaii"));
		unemployments.add(
				new Unemployment("NV", 100, 0.5, 2.4, 4.6, 2.1, -0.6, -1.7, "Nevada"));
		unemployments.add(new Unemployment("NM", 100, -0.6, 1.0, 2.4, 1.1, -0.4, -0.5,
				"New Mexico"));
		unemployments.add(
				new Unemployment("OK", 100, 0.0, -0.4, 3.0, 0.2, -1.0, -0.5, "Oklahoma"));
		unemployments.add(
				new Unemployment("TX", 100, -0.5, 0.5, 2.6, 0.7, -0.3, -1.1, "Texas"));
		unemployments.add(
				new Unemployment("UT", 100, -0.3, 0.7, 4.5, 0.3, -1.3, -1.4, "Utah"));

		unemployments.add(new Unemployment("", 20));

		// Northwest region
		unemployments.add(
				new Unemployment("AK", 100, -0.4, 0.3, 1.3, 0.3, -0.4, -0.7, "Alaska"));
		unemployments.add(
				new Unemployment("ID", 100, 0.0, 1.8, 2.6, 1.3, -0.3, -1.1, "Idaho"));
		unemployments.add(
				new Unemployment("MT", 100, 0.2, 1.1, 1.5, 0.7, -0.2, -0.5, "Montana"));
		unemployments.add(
				new Unemployment("OR", 100, -0.1, 1.3, 4.6, -0.3, -1.1, -0.9, "Oregon"));
		unemployments.add(new Unemployment("WA", 100, -0.3, 0.8, 3.9, 0.6, -0.7, -1.1,
				"Washington"));
		unemployments.add(
				new Unemployment("WY", 100, -0.4, 0.3, 3.2, 0.7, -0.9, -0.7, "Wyoming"));
	}

	@ExtDirectMethod(group = "infographic")
	public List<Unemployment> getUnemployments() {
		return this.unemployments;
	}

}

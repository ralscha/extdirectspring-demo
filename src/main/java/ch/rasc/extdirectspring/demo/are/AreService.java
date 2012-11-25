package ch.rasc.extdirectspring.demo.are;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.controller.RouterController;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.ralscha.extdirectspring.generator.ModelGenerator;
import ch.ralscha.extdirectspring.generator.OutputFormat;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Controller
public class AreService {

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "are")
	public ImmutableCollection<Company> read(@RequestParam(required = false) String coId) {
		if (coId != null) {
			return ImmutableList.of(companyDb.get(coId));
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

	@RequestMapping("/extjs41/associationrowexpander/models.js")
	public void models(HttpServletResponse response) throws IOException {
		String company = ModelGenerator.generateJavascript(Company.class, OutputFormat.EXTJS4, false);
		String history = ModelGenerator.generateJavascript(History.class, OutputFormat.EXTJS4, false);

		byte[] code = (company + history).getBytes(StandardCharsets.UTF_8);

		response.setContentType("application/javascript");
		response.setCharacterEncoding(RouterController.UTF8_CHARSET.name());
		response.setContentLength(code.length);

		@SuppressWarnings("resource")
		OutputStream out = response.getOutputStream();
		out.write(code);
	}

}

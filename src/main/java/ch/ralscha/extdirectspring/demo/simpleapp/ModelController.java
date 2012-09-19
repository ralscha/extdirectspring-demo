package ch.ralscha.extdirectspring.demo.simpleapp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.ralscha.extdirectspring.generator.ModelGenerator;
import ch.ralscha.extdirectspring.generator.OutputFormat;

@Controller
@RequestMapping("/extjs41/simple")
public class ModelController {

	@RequestMapping("/app/model/User.js")
	public void user(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelGenerator.writeModel(request, response, User.class, OutputFormat.EXTJS4);
	}

}

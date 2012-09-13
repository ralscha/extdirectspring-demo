package ch.ralscha.extdirectspring.demo.simpleapp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

@Controller
// @RequestMapping("/extjs41/simple")
public class ModelController {

	// @RequestMapping("/app/model/User.js")
	public void user(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(request);
		// ModelGenerator.writeModel(request, response, User.class,
		// OutputFormat.EXTJS4);
	}

}

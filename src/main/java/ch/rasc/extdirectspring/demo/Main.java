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
package ch.rasc.extdirectspring.demo;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.MultiPartConfigFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.extdirectspring.demo" })
@EnableAutoConfiguration
public class Main extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Main.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultiPartConfigFactory factory = new MultiPartConfigFactory();
		factory.setFileSizeThreshold("10MB");
		factory.setMaxFileSize("100MB");
		return factory.createMultipartConfig();
	}

	/*
	 * @Bean public ch.rasc.extdirectspring.controller.Configuration edsConfig() {
	 * ch.rasc.extdirectspring.controller.Configuration config = new ch.rasc.extdirectspring.controller.Configuration();
	 * config.setStreamResponse(true); config.setTimeout(12000); config.setMaxRetries(10);
	 * config.setEnableBuffer(false); return config; }
	 */

	@Bean
	@Lazy
	public ClassPathResource randomdata() {
		return new ClassPathResource("/randomdata.csv");
	}

	@Bean
	@Lazy
	public ClassPathResource pivotdata() {
		return new ClassPathResource("/pivodata.csv");
	}

	@Bean
	@Lazy
	public ClassPathResource userdata() {
		return new ClassPathResource("/users.csv");
	}

	@Bean
	@Lazy
	public ClassPathResource countries() {
		return new ClassPathResource("/countries.csv");
	}

	@Bean
	@Lazy
	public ClassPathResource employees() {
		return new ClassPathResource("/employees.json");
	}

	@Bean
	@Lazy
	public ClassPathResource contacts() {
		return new ClassPathResource("/contacts.json");
	}

	@Bean
	@Lazy
	public ClassPathResource friends() {
		return new ClassPathResource("/friends.json");
	}

}

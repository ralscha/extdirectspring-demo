/**
 * Copyright 2010-2013 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.extdirectspring.demo" })
public class SpringConfig extends WebMvcConfigurerAdapter {

	/*
	 * @Bean public ch.rasc.extdirectspring.controller.Configuration edsConfig()
	 * { ch.rasc.extdirectspring.controller.Configuration config = new
	 * ch.rasc.extdirectspring.controller.Configuration();
	 * config.setStreamResponse(true); config.setTimeout(12000);
	 * config.setMaxRetries(10); config.setEnableBuffer(false); return config; }
	 */

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index.html");
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

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
}

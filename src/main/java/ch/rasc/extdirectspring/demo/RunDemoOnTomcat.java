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
package ch.rasc.extdirectspring.demo;

import ch.rasc.embeddedtc.EmbeddedTomcat;

public class RunDemoOnTomcat {

	public static void main(String[] args) {

		// Skip jars during Tomcat scanning for web fragments, TLD files, etc.
		// Improves startup time
		String skipJars = "spring*.jar,cglib*.jar,slf4j*.jar,logback*.jar,jcl-over*.jar,urlrewrite*.jar,hamcrest*.jar,";
		skipJars += "jackson*.jar,hibernate*.jar,jboss*.jar,guava*.jar,commons-*.jar,opencsv*.jar,joda*.jar,imgscalr*.jar,";
		skipJars += "ecj*.jar,rome*.jar,jdom*.jar";

		EmbeddedTomcat.create().skipJarsDefaultJarScanner(skipJars).startAndWait();
	}
}

/*
 * Copyright the original author or authors.
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
package ch.rasc.extdirectspring.demo.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class ISO8601ZonedDateTimeSerializer extends ValueSerializer<ZonedDateTime> {

	private static final DateTimeFormatter DT_PATTERN = DateTimeFormatter
			.ofPattern("uuuu-MM-dd'T'HH:mm:ssZZZZ");

	@Override
	public void serialize(ZonedDateTime value, JsonGenerator jgen,
			SerializationContext provider) {

		jgen.writeString(value.format(DT_PATTERN));
	}

}

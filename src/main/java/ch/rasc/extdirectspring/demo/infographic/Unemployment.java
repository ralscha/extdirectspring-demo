/**
 * Copyright 2010-2017 the original author or authors.
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
package ch.rasc.extdirectspring.demo.infographic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Unemployment {

	private final String label;

	private final int span;

	private final Double y2007;
	private final Double y2008;
	private final Double y2009;
	private final Double y2010;
	private final Double y2011;
	private final Double y2012;

	private final String state;

	Unemployment(String label, int span) {
		this.label = label;
		this.span = span;
		this.y2007 = null;
		this.y2008 = null;
		this.y2009 = null;
		this.y2010 = null;
		this.y2011 = null;
		this.y2012 = null;
		this.state = null;
	}

	Unemployment(String label, int span, double y2007, double y2008, double y2009,
			double y2010, double y2011, double y2012, String state) {
		this.label = label;
		this.span = span;
		this.y2007 = y2007;
		this.y2008 = y2008;
		this.y2009 = y2009;
		this.y2010 = y2010;
		this.y2011 = y2011;
		this.y2012 = y2012;
		this.state = state;
	}

	public String getLabel() {
		return this.label;
	}

	public int getSpan() {
		return this.span;
	}

	public Double getY2007() {
		return this.y2007;
	}

	public Double getY2008() {
		return this.y2008;
	}

	public Double getY2009() {
		return this.y2009;
	}

	public Double getY2010() {
		return this.y2010;
	}

	public Double getY2011() {
		return this.y2011;
	}

	public Double getY2012() {
		return this.y2012;
	}

	public String getState() {
		return this.state;
	}

}

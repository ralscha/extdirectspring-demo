/**
 * Copyright 2010-2015 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo.chart;

import java.util.Random;

public class AreaData {

	private static Random rnd = new Random();

	private final String name;

	private final int data1;

	private final int data2;

	private final int data3;

	private final int data4;

	private final int data5;

	private final int data6;

	private final int data7;

	private final int data8;

	private final int data9;

	public AreaData(String name) {
		this.name = name;

		this.data1 = rnd.nextInt(100) + 1;
		this.data2 = rnd.nextInt(100) + 1;
		this.data3 = rnd.nextInt(100) + 1;
		this.data4 = rnd.nextInt(100) + 1;
		this.data5 = rnd.nextInt(100) + 1;
		this.data6 = rnd.nextInt(100) + 1;
		this.data7 = rnd.nextInt(100) + 1;
		this.data8 = rnd.nextInt(100) + 1;
		this.data9 = rnd.nextInt(100) + 1;
	}

	public String getName() {
		return this.name;
	}

	public int getData1() {
		return this.data1;
	}

	public int getData2() {
		return this.data2;
	}

	public int getData3() {
		return this.data3;
	}

	public int getData4() {
		return this.data4;
	}

	public int getData5() {
		return this.data5;
	}

	public int getData6() {
		return this.data6;
	}

	public int getData7() {
		return this.data7;
	}

	public int getData8() {
		return this.data8;
	}

	public int getData9() {
		return this.data9;
	}

}

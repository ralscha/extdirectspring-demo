/**
 * Copyright 2010-2016 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.extdirectspring.demo.group;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.GroupInfo;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.rasc.extdirectspring.demo.util.PropertyComparatorFactory;

@Service
public class GroupAction {

	private static List<Task> tasks;

	static {
		List<Task> builder = new ArrayList<>();
		builder.add(new Task(100, "Ext Forms: Field Anchoring", 112,
				"Integrate 2.0 Forms with 2.0 Layouts", new BigDecimal(6),
				new BigDecimal(150), 2010, 7, 24));
		builder.add(
				new Task(100, "Ext Forms: Field Anchoring", 113, "Implement AnchorLayout",
						new BigDecimal(4), new BigDecimal(150), 2010, 7, 25));
		builder.add(new Task(100, "Ext Forms: Field Anchoring", 114,
				"Add support for multiple types of anchors", new BigDecimal(4),
				new BigDecimal(150), 2010, 7, 27));
		builder.add(
				new Task(100, "Ext Forms: Field Anchoring", 115, "Testing and debugging",
						new BigDecimal(8), new BigDecimal(0), 2010, 7, 29));
		builder.add(new Task(101, "Ext Grid: Single-level Grouping", 101,
				"Add required rendering 'hooks' to GridView", new BigDecimal(6),
				new BigDecimal(100), 2010, 8, 1));
		builder.add(new Task(101, "Ext Grid: Single-level Grouping", 102,
				"Extend GridView and override rendering functions", new BigDecimal(6),
				new BigDecimal(100), 2010, 8, 3));
		builder.add(new Task(101, "Ext Grid: Single-level Grouping", 103,
				"Extend Store with grouping functionality", new BigDecimal(4),
				new BigDecimal(100), 2010, 8, 4));
		builder.add(new Task(101, "Ext Grid: Single-level Grouping", 121,
				"Default CSS Styling", new BigDecimal(2), new BigDecimal(100), 2010, 8,
				5));
		builder.add(new Task(101, "Ext Grid: Single-level Grouping", 104,
				"Testing and debugging", new BigDecimal(6), new BigDecimal(100), 2010, 8,
				6));
		builder.add(new Task(102, "Ext Grid: Summary Rows", 105,
				"Ext Grid plugin integration", new BigDecimal(4), new BigDecimal(125),
				2010, 8, 1));
		builder.add(new Task(102, "Ext Grid: Summary Rows", 106,
				"Summary creation during rendering phase", new BigDecimal(4),
				new BigDecimal(125), 2010, 8, 2));
		builder.add(new Task(102, "Ext Grid: Summary Rows", 107,
				"Dynamic summary updates in editor grids", new BigDecimal(6),
				new BigDecimal(125), 2010, 8, 5));
		builder.add(
				new Task(102, "Ext Grid: Summary Rows", 108, "Remote summary integration",
						new BigDecimal(4), new BigDecimal(125), 2010, 8, 5));
		builder.add(new Task(102, "Ext Grid: Summary Rows", 109,
				"Summary renderers and calculators", new BigDecimal(4),
				new BigDecimal(125), 2010, 8, 6));
		builder.add(new Task(102, "Ext Grid: Summary Rows", 110,
				"Integrate summaries with GroupingView", new BigDecimal(10),
				new BigDecimal(125), 2010, 8, 11));
		builder.add(new Task(102, "Ext Grid: Summary Rows", 111, "Testing and debugging",
				new BigDecimal(8), new BigDecimal(125), 2010, 8, 15));

		tasks = Collections.unmodifiableList(builder);

	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "total")
	public List<Task> load(ExtDirectStoreReadRequest request) {
		return sort(request);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "hybrid")
	public List<Task> loadHybrid(ExtDirectStoreReadRequest request) {
		return sort(request);
	}

	private static List<Task> sort(ExtDirectStoreReadRequest request) {
		Comparator<Task> comparator = null;

		if (!request.getGroups().isEmpty()) {
			GroupInfo groupInfo = request.getGroups().iterator().next();

			if (!request.getSorters().isEmpty()) {
				for (SortInfo sortInfo : request.getSorters()) {
					if (groupInfo.getProperty().equals(sortInfo.getProperty())) {
						groupInfo = new GroupInfo(groupInfo.getProperty(),
								sortInfo.getDirection());
					}
				}
			}

			comparator = PropertyComparatorFactory
					.createComparatorFromGroups(Collections.singletonList(groupInfo));
		}

		Comparator<Task> sortComparator = PropertyComparatorFactory
				.createComparatorFromSorters(request.getSorters());

		if (sortComparator != null) {
			if (comparator != null) {
				comparator = comparator.thenComparing(sortComparator);
			}
			else {
				comparator = sortComparator;
			}
		}

		if (comparator != null) {
			return tasks.stream().sorted(comparator).collect(Collectors.toList());
		}

		return tasks;
	}

	@ExtDirectMethod(group = "hybrid")
	public Summary updateSummary() {
		Summary summary = new Summary();
		summary.setDescription("22");
		summary.setEstimate(new BigDecimal(888));
		summary.setRate(new BigDecimal(999));
		summary.setDue(LocalDate.now());
		summary.setCost(new BigDecimal(8));
		return summary;
	}
}

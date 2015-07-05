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
package ch.rasc.extdirectspring.demo.util;

import java.util.Collection;
import java.util.Comparator;

import org.springframework.expression.ParseException;

import ch.ralscha.extdirectspring.bean.GroupInfo;
import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.ralscha.extdirectspring.bean.SortInfo;

public enum PropertyComparatorFactory {

	INSTANCE;

	public static <T> Comparator<T> createComparator(String propertyName) {
		try {
			return new PropertyComparator<>(propertyName);
		}
		catch (ParseException e) {
			return null;
		}
	}

	public static <T> Comparator<T> createComparator(String propertyName,
			SortDirection sortDirection) {
		try {
			Comparator<T> comparator = new PropertyComparator<>(propertyName);

			if (sortDirection == SortDirection.DESCENDING) {
				comparator = comparator.reversed();
			}

			return comparator;
		}
		catch (ParseException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> Comparator<T> createComparatorFromSorters(
			Collection<SortInfo> sortInfos) {
		Comparator<T> comparator = null;

		if (sortInfos != null) {
			comparator = sortInfos.stream()
					.map(a -> (Comparator<T>) createComparator(a.getProperty(),
							a.getDirection()))
					.reduce(comparator, (a, b) -> a != null ? a.thenComparing(b) : b);
		}

		return comparator;
	}

	@SuppressWarnings("unchecked")
	public static <T> Comparator<T> createComparatorFromGroups(
			Collection<GroupInfo> groupInfos) {
		Comparator<T> comparator = null;

		if (groupInfos != null) {
			comparator = groupInfos.stream()
					.map(a -> (Comparator<T>) createComparator(a.getProperty(),
							a.getDirection()))
					.reduce(comparator, (a, b) -> a != null ? a.thenComparing(b) : b);
		}

		return comparator;
	}

}

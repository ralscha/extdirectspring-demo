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
package ch.rasc.extdirectspring.demo.simpletask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class ListService {

	private final static Map<Integer, ListItem> DB = new ConcurrentHashMap<>();
	private final static AtomicInteger MAX_ID = new AtomicInteger(1);

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "simple-task")
	public List<ListItem> read() {

		List<ListItem> roots = new ArrayList<>();
		Map<Integer, ListItem> parents = new HashMap<>();

		List<ListItem> items = DB.values().stream()
				.sorted(Comparator.comparing(ListItem::getParentId))
				.collect(Collectors.toList());

		for (ListItem item : items) {
			System.out.println(item);
			parents.put(item.getId(), item);

			if (item.getParentId() == 0) {
				roots.add(item);
			}
			else {
				ListItem parent = parents.get(item.getParentId());
				if (parent.getChildren() == null) {
					parent.setChildren(new ArrayList<>());
				}
				parent.getChildren().add(item);
			}
		}

		parents.values().forEach(this::setLoadedToNonLeafItemsWithNoChildren);

		return roots;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simple-task")
	public List<ListItem> create(List<ListItem> newListItems) {
		newListItems.stream().forEach(newListItem -> {
			System.out.println(newListItem);
			newListItem.setId(MAX_ID.getAndIncrement());
			setLoadedToNonLeafItemsWithNoChildren(newListItem);
			DB.put(newListItem.getId(), newListItem);
		});
		return newListItems;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simple-task")
	public List<ListItem> update(List<ListItem> updatedListItems) {

		updatedListItems.stream().forEach(updatedListItem -> {
			System.out.println(updatedListItem);
			ListItem li = DB.get(updatedListItem.getId());
			if (updatedListItem.getName() != null) {
				li.setName(updatedListItem.getName());
			}
			if (updatedListItem.getParentId() != null) {
				li.setParentId(updatedListItem.getParentId());
			}
			if (updatedListItem.isLeaf() != null) {
				li.setLeaf(updatedListItem.isLeaf());
			}

			setLoadedToNonLeafItemsWithNoChildren(li);
		});
		return updatedListItems;
	}

	private void setLoadedToNonLeafItemsWithNoChildren(ListItem li) {
		li.setLoaded(null);

		if (!li.isLeaf()) {
			if ((li.getChildren() == null || li.getChildren().isEmpty())) {
				li.setLoaded(true);
			}
			else {
				li.setExpanded(true);
			}
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "simple-task")
	public void destroy(List<ListItem> destroyedListItems) {
		destroyedListItems.stream().map(ListItem::getId).forEach(DB::remove);
	}

}

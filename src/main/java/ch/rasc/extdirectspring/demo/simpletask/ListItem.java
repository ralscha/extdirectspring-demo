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
package ch.rasc.extdirectspring.demo.simpletask;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ListItem {

	private Integer id;

	private Integer parentId;

	private String name;

	private Boolean expanded;

	private Boolean leaf;

	private Boolean loaded;

	private List<ListItem> children;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isExpanded() {
		return this.expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean isLeaf() {
		return this.leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean isLoaded() {
		return this.loaded;
	}

	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}

	public List<ListItem> getChildren() {
		return this.children;
	}

	public void setChildren(List<ListItem> children) {
		this.children = children;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "ListItem [id=" + this.id + ", parentId=" + this.parentId + ", name="
				+ this.name + ", expanded=" + this.expanded + ", leaf=" + this.leaf
				+ ", loaded=" + this.loaded + ", children=" + this.children + "]";
	}

}

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
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}

	public List<ListItem> getChildren() {
		return children;
	}

	public void setChildren(List<ListItem> children) {
		this.children = children;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "ListItem [id=" + id + ", parentId=" + parentId + ", name=" + name
				+ ", expanded=" + expanded + ", leaf=" + leaf + ", loaded=" + loaded
				+ ", children=" + children + "]";
	}

}

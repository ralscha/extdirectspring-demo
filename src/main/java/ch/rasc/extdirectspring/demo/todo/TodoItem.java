package ch.rasc.extdirectspring.demo.todo;

public class TodoItem {
	private long id;
	private String text;
	private boolean complete;

	public TodoItem(long id, String text, boolean complete) {
		this.id = id;
		this.text = text;
		this.complete = complete;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

}

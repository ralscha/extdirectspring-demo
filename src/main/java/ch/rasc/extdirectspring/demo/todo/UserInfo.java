package ch.rasc.extdirectspring.demo.todo;

public class UserInfo {
	private String firstname;
	private String lastname;
	private String email;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserInfo [firstname=" + firstname + ", lastname=" + lastname + ", email="
				+ email + "]";
	}

}

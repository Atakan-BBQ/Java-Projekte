package app.fx;

public class Guest {
	// (guest_id, name, phone, email)
	
	private int guest_id = 0;
	private String name = null;
	private String phone = null;
	private String email = null;
	
	public Guest() {
		
	}

	public Guest(int guest_id, String name, String phone, String email) {
		this.guest_id = guest_id;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public int getGuest_id() {
		return guest_id;
	}

	public void setGuest_id(int guest_id) {
		this.guest_id = guest_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

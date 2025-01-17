package hotel_dto;

public class LoginDs {

	private String email;
	private String password;
	private String name;
	private int age;
	private long phone;
	
	public LoginDs(String email, String password, String name, int age, long phone) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.age = age;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "LoginDs [email=" + email + ", password=" + password + ", name=" + name + ", age=" + age + ", phone="
				+ phone + "]";
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public long getPhone() {
		return phone;
	}


	public void setPhone(long phone) {
		this.phone = phone;
	}


	public LoginDs() {
		
	}
	
}

package dummy;

import org.springframework.stereotype.Component;

@Component
public class Student {

	  private String name;
	  private String email_address;
	  private String purchased_package;
	  
	  
	  
	 public Student() {
		super();
	}



	public Student(String name, String email_address, String purchased_package) {
		super();
		this.name = name;
		this.email_address = email_address;
		this.purchased_package = purchased_package;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail_address() {
		return email_address;
	}



	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}



	public String getPurchased_package() {
		return purchased_package;
	}



	public void setPurchased_package(String purchased_package) {
		this.purchased_package = purchased_package;
	}



	@Override
	public String toString() {
		return "Student [name=" + name + ", email_address=" + email_address + ", purchased_package=" + purchased_package
				+ "]";
	}
	  
}

package com.cg.phonebook.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="UserEntity")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;
	/*
	 * User Id
	 */
	@Column(unique = true,updatable = false)
	@NotBlank(message="User name can't be blank")
	@Size(min=4, max = 8, message = "Size must be between 4 and 8 characters")
	private String userName;
	/*
	 * User Phone Number
	 */
	@Column(unique=true)
	@Size(min=10, max=10)
	private String phoneNumber;
	/*
	 * User first name
	 */
	private String firstName;
	/*
	 * User Last name
	 */
	private String lastName;
	/*
	 * User email
	 */
	private String email;
	/*
	 * user address
	 */
	private String address;
	/*
	 * user password
	 */
	private String password;
	private String confirmPassword;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "us_fk", referencedColumnName = "user_id" )
	private List<Contact> contactList;
	
	public UserEntity() {
		super();
	}
	
	public UserEntity(String firstName, String lastName, String userIdentifier, String email, String phoneNumber, String password,
			String confirmPassword,String address,List<Contact> contactList) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.contactList = new ArrayList<>();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
	
	
	  public List<Contact> getContactList() { return contactList; }
	  
	  
	  
	  public void setContactList(List<Contact> contactList) { this.contactList =
	  contactList; }
	 

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", userName=" + userName + ", phoneNumber=" + phoneNumber
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", address=" + address
				+ ", password=" + password + ", confirmPassword=" + confirmPassword+ " contactList=" + contactList
				+"]";
	}

}

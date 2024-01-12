package com.googlesheets.demo.dto;

public class EmpDTO {

	private int empId;
	private String firstName;
	private String lastName;
	private String age;
	private String phoneNumber;
	private String email;
	private String date;

	public EmpDTO() {
		super();
	}

	public EmpDTO(int empId, String date, String firstName, String lastName, String age, String phoneNumber,
			String email) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.date = date;
	}

	public EmpDTO(int empId, String firstName, String lastName, String age, String phoneNumber, String email) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

//	public EmpDTO(String firstName, String lastName, String email, String age, String phoneNumber) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.age = age;
//		this.phoneNumber = phoneNumber;
//		this.email = email;
//	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "EmpDTO [empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", date=" + date + "]";
	}

}

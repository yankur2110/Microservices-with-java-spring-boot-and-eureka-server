package com.kindsonthegenius.admissionsservice.models;

public class Employee {
	
	private String Id;
	private String firstname;
	private String lastname;
	private String speciality;
	
	public Employee(String id, String firstname, String lastname, String speciality) {
		super();
		Id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.speciality = speciality;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
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
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	@Override
	public String toString() {
		return "Employee [Id=" + Id + ", firstname=" + firstname + ", lastname=" + lastname + ", speciality="
				+ speciality + "]";
	}

	
	
}

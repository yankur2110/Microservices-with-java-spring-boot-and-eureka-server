package com.kindsonthegenius.admissionsservice.models;

import java.util.List;

public class EmployeesList {
	
	private List<Employee> employeesList;

	public EmployeesList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeesList(List<Employee> employeesList) {
		super();
		this.employeesList = employeesList;
	}

	public List<Employee> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employee> employeesList) {
		this.employeesList = employeesList;
	}

	@Override
	public String toString() {
		return "EmployeesList [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}

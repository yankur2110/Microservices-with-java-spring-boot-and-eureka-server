package com.kindsonthegenius.admissionsservice.models;

import java.util.List;

public class DiseasesList {
	
	private List<Disease> diseasesList;

	public DiseasesList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiseasesList(List<Disease> diseasesList) {
		super();
		this.diseasesList = diseasesList;
	}

	public List<Disease> getDiseases() {
		return diseasesList;
	}

	public void setDiseases(List<Disease> diseasesList) {
		this.diseasesList = diseasesList;
	}

	@Override
	public String toString() {
		return "DiseasesList [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	

}

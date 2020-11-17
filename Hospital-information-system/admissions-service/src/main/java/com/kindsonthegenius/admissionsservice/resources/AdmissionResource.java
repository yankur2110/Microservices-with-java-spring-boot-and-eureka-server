package com.kindsonthegenius.admissionsservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kindsonthegenius.admissionsservice.models.DiseasesList;
import com.kindsonthegenius.admissionsservice.models.EmployeesList;
import com.kindsonthegenius.admissionsservice.models.Patient;

@RestController
@RequestMapping("/admissions")
public class AdmissionResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	List<Patient> patients = Arrays.asList(
			new Patient("P1","Ramadhir", "Indian"),
			new Patient("P2","Christopher", "American"),
			new Patient("P3", "Irina", "England")
			);
	
	@RequestMapping("/physicians")
	public EmployeesList getPhysicians() {
		EmployeesList employeesList = restTemplate.getForObject("http://hr-service/hr/employees", EmployeesList.class);
		return employeesList;
	}
	
	@RequestMapping("/diseases")
	public DiseasesList getDiseases() {
		DiseasesList diseasesList = restTemplate.getForObject("http://pathology-service/pathology/diseases", DiseasesList.class);
		return diseasesList;
	}
	
	@RequestMapping("/patients")
	List<Patient> getPatients(){
		return patients;
	}
	
	@RequestMapping("/patients/{Id}")
	Patient getPatientById(@PathVariable("Id") String Id) {
		
		Patient p = patients.stream()
				.filter(patient -> Id.equals(patient.getId()))
				.findAny()
				.orElse(null);
		return p;
		
	}

}

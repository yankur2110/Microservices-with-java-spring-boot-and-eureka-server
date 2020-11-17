package com.kindsonthegenius.pathologyservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kindsonthegenius.pathologyservice.models.Disease;
import com.kindsonthegenius.pathologyservice.models.DiseasesList;

@RestController
@RequestMapping("/pathology")
public class PathologyResource {

	List<Disease> diseases = Arrays.asList(
			new Disease("D1", "Ashthma", "Warm water bath"),
			new Disease("D2", "Thyroid", "Ampicilin capsule!"),
			new Disease("D3", "Corona", "Cite go corona go song")
			);
	
	
	@RequestMapping("/diseases")
	DiseasesList getDiseases(){
		DiseasesList diseasesList = new DiseasesList(diseases);
		return diseasesList;
	}
	
	@RequestMapping("/diseases/{Id}")
	Disease getDiseaseById(@PathVariable("Id") String Id) {
		Disease d = diseases.stream()
				.filter(disease -> Id.equals(disease.getId()))
				.findAny()
				.orElse(null);
		return d;
	}
}

package com.forge.revature.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forge.revature.models.AdminChart;
import com.forge.revature.services.AdminChartService;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/adminChart")
@AllArgsConstructor
public class AdminChartController {
	
	private AdminChartService adminChartService;
	
	/**
	* getAdminWorkReport --- get a report in json object containing all the admin's approved or denied counts.
	* @author	Hong Wu
	* @param	none
	* @return	when search failed, return a response entity with status code 400
	*		    when search success, return a response entity with status code 200
	*	                             and a body containing the json object
	*/
	@GetMapping
	public ResponseEntity<List<AdminChart>> getAdminWorkReport() {
		return adminChartService.getAdminWorkReport();
	}
}

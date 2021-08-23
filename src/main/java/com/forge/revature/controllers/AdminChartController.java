package com.forge.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.revature.models.AdminChart;
import com.forge.revature.models.User;
import com.forge.revature.services.AdminChartService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/adminChart")
@NoArgsConstructor
public class AdminChartController {

	AdminChartService adminChartService;
	
	@Autowired
	public AdminChartController(AdminChartService adminChartService) {
		super();
		this.adminChartService = adminChartService;
	}
	
	/**
	 * getAdminWorkReport --- get a report in json object containing all the admin's approved or denied counts.
	 * @author	Hong Wu
	 * @param	none
	 * @return	when search failed, return a response entity with status code 400
	 *		    when search success, return a response entity with status code 200
     *	                             and a body containing the json object
	 */
    @PostMapping
    public ResponseEntity<List<AdminChart>> getAdminWorkReport() {
    	ArrayList<AdminChart> chartData = new ArrayList<AdminChart>();

    	//get all the admin user accounts
    	List<User> adminList = adminChartService.getAdminList();

    	//return failure if database request fail
		if(adminList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		//format the list of reports into AdminChart model
    	for (int i=0; i<adminList.size(); i++) {
    		
    		//get approved count by admin id
    		Integer approveCount = adminChartService.getCount(true, adminList.get(i).getId());
    		
    		//get denied count by admin id
    		Integer deniedCount = adminChartService.getCount(false, adminList.get(i).getId());

    		chartData.add(new AdminChart(adminList.get(i).getFName()+ ' ' + adminList.get(i).getLName(), (approveCount==null? 0: approveCount), (deniedCount==null? 0: deniedCount)));
    	}
    	
		return ResponseEntity.status(HttpStatus.OK).body(chartData);
    }
}

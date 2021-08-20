package com.forge.revature.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.revature.models.AdminChart;
import com.forge.revature.models.User;
import com.forge.revature.services.AdminChartService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/adminChart")
public class AdminChartController {

	@Autowired
	AdminChartService adminChartService;
	
    @GetMapping("/admin")
    public ResponseEntity<List<AdminChart>> getAdminWorkReport() {
    	ArrayList<AdminChart> chartData = new ArrayList<AdminChart>();

    	List<User> adminList = adminChartService.getAdminList();		
    	List<Integer> approveCount = adminChartService.getCount(true);
    	List<Integer> deniedCount = adminChartService.getCount(false);
    	
    	for (int i=0; i<adminList.size(); i++) {
    		chartData.add(new AdminChart(adminList.get(i).getFName(), adminList.get(i).getLName(), approveCount.get(i), deniedCount.get(i)));
    	}
    	
		if(chartData.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(chartData);
    }
}
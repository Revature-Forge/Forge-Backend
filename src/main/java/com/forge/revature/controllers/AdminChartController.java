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
@AllArgsConstructor
public class AdminChartController {
	
	@Autowired
	AdminChartService adminChartService;
	
    @PostMapping
    public ResponseEntity<List<AdminChart>> getAdminWorkReport() {
    	ArrayList<AdminChart> chartData = new ArrayList<AdminChart>();

    	List<User> adminList = adminChartService.getAdminList();
    	
		if(adminList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
    	for (int i=0; i<adminList.size(); i++) {
    		Integer approveCount = adminChartService.getCount(true, adminList.get(i).getId());
    		Integer deniedCount = adminChartService.getCount(false, adminList.get(i).getId());
    		chartData.add(new AdminChart(adminList.get(i).getFName()+ ' ' + adminList.get(i).getLName(), (approveCount==null? 0: approveCount), (deniedCount==null? 0: deniedCount)));
    	}
    	
		return ResponseEntity.status(HttpStatus.OK).body(chartData);
    }
}
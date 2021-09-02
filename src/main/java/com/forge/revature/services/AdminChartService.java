package com.forge.revature.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.forge.revature.models.AdminChart;
import com.forge.revature.models.User;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.UserRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminChartService {
	
	private UserRepo userRepo;
	private PortfolioRepo portfolioRepo;
	private PortfolioService portfolioService;
	
	/**
	* getCount --- program to get the count of approved or denied portfolios by one admin.
	* @author	Hong Wu
	* @param	a boolean, true for counting approved cases
	*	                   false for counting denied cases
	*	        an int, the admin's user id
	* @return	the count result
	*/
	public Integer getCount(boolean approveState, int admin_id) {
		if (approveState) return portfolioRepo.getApproveCount(admin_id);
		return portfolioRepo.getDeniedCount(admin_id);
	}
	
	/**
	* getAdminList --- program to get all the admin user accounts.
	* @author	Hong Wu
	* @param	none
	* @return	a list of objects, containing all the user accounts sorted by user id
	*/
	public List<User> getAdminList() {
		List<User> adminList = userRepo.findAllByAdmin(true);
		Collections.sort(adminList, new Comparator<User>() { 
				@Override
				public int compare(User u1, User u2) {
					return u1.getId() - u2.getId();
				}
			});
		return adminList;
	}
	
	/**
	* getAdminWorkReport --- get a report in json object containing all the admin's approved or denied counts.
	* @author	Hong Wu
	* @param	none
	* @return	when search failed, return a response entity with status code 400
	*		    when search success, return a response entity with status code 200
	*	                             and a body containing the json object
	*/
	public ResponseEntity<List<AdminChart>> getAdminWorkReport() {
		ArrayList<AdminChart> chartData = new ArrayList<AdminChart>();
	
		//get all the admin user accounts
		List<User> adminList = getAdminList();
	
		//return failure if database request fail
		if(adminList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	
		//format the list of reports into AdminChart model
		for (int i=0; i<adminList.size(); i++) {
			
			//get approved count by admin id
			Integer approveCount = getCount(true, adminList.get(i).getId());
			
			//get denied count by admin id
			Integer deniedCount = getCount(false, adminList.get(i).getId());
			
			//get response time in second
			Double avgResponseTime = portfolioService.calculateAverageResponseTime();
			
			String avgResponseTimeString = portfolioService.calculateAverageResponseTimeString();
	
			chartData.add(new AdminChart(adminList.get(i).getFName()+ ' ' + adminList.get(i).getLName(), (approveCount==null? 0: approveCount), (deniedCount==null? 0: deniedCount),
					(avgResponseTime==null? 0: avgResponseTime),avgResponseTimeString));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(chartData);
	}
	
	
}
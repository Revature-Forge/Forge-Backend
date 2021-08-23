package com.forge.revature.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forge.revature.models.User;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.UserRepo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class AdminChartService {

	private UserRepo userRepo;
	private PortfolioRepo portfolioRepo;

	@Autowired
	public AdminChartService(UserRepo userRepo, PortfolioRepo portfolioRepo) {
		super();
		this.userRepo = userRepo;
		this.portfolioRepo = portfolioRepo;
	}
	
	
	
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
}
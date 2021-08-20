package com.forge.revature.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.forge.revature.models.User;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.UserRepo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AdminChartService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PortfolioRepo portfolioRepo;
	
	public List<Integer> getCount(boolean approveState) {
		if (approveState) return portfolioRepo.getApproveCount();
		return portfolioRepo.getDeniedCount();
	}
	
	public List<User> getAdminList() {
		List<User> adminList = userRepo.findAllAdmin(true);
		Collections.sort(adminList, new Comparator<User>() { 
				@Override
				public int compare(User u1, User u2) {
					return u1.getId() - u2.getId();
				}
			});
		return adminList;
	}
}

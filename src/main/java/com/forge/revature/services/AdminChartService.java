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
@AllArgsConstructor
public class AdminChartService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PortfolioRepo portfolioRepo;
	
	public Integer getCount(boolean approveState, int admin_id) {
		if (approveState) return portfolioRepo.getApproveCount(admin_id);
		return portfolioRepo.getDeniedCount(admin_id);
	}
	
	public List<User> getAdminList() {
		List<User> adminList = userRepo.findAllAdmin();
		Collections.sort(adminList, new Comparator<User>() { 
				@Override
				public int compare(User u1, User u2) {
					return u1.getId() - u2.getId();
				}
			});
		return adminList;
	}
}

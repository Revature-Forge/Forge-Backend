package com.forge.revature.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatrixDTO {
	
	private int id;
	
	private String header;
	
	private Portfolio portfolio;
	
	private List<Skill> skills;
	
	public MatrixDTO(String header, List<Skill> skills, Portfolio portfolio) {
		this.header = header;
		this.skills = skills;
		this.portfolio = portfolio;
	}

}

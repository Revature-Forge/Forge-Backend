package com.forge.revature.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matrices")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Matrix {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	private String header;
	
	@ManyToOne
	@JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
	
	@Transient
	private List<Skill> skills;
	
	public Matrix(String header, Portfolio portfolio) {
		this.header = header;
		this.portfolio = portfolio;
	}
	
	public Matrix(String header, List<Skill> skills, Portfolio portfolio) {
		this.header = header;
		this.skills = skills;
		this.portfolio = portfolio;
	}
	
	public Matrix(String header, List<Skill> skills) {
		this.header = header;
		this.skills = skills;
	}
	
	public Matrix(String header) {
		this.header = header;
	}
}
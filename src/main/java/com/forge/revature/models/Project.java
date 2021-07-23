package com.forge.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "projectid")
	private long id;

	private String name;

	private String description;

	@Column(length = 10000)
	private String responsibilities;

	@Column(length = 10000)
	private String technologies;

	private String respositoryUrl;

	private String workProducts;

	@OneToOne
	@JoinColumn
	private Portfolio portfolio;

	public Project(String name, String description, String responsibilities, String technologies, String respositoryUrl,
			String workProducts, Portfolio portfolio) {
		this.name = name;
		this.description = description;
		this.responsibilities = responsibilities;
		this.technologies = technologies;
		this.respositoryUrl = respositoryUrl;
		this.workProducts = workProducts;
		this.portfolio = portfolio;
	}

	public Project(String name, String description, String responsibilities, String technologies, String respositoryUrl,
			Portfolio portfolio) {
		this.name = name;
		this.description = description;
		this.responsibilities = responsibilities;
		this.technologies = technologies;
		this.respositoryUrl = respositoryUrl;
		this.portfolio = portfolio;
	}

	public Project(String name, String description, String responsibilities, String technologies,
			String respositoryUrl) {
		this.name = name;
		this.description = description;
		this.responsibilities = responsibilities;
		this.technologies = technologies;
		this.respositoryUrl = respositoryUrl;
	}

}

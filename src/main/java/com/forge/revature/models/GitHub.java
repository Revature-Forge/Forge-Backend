package com.forge.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "github")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GitHub{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String url;

	private String image;

	@ManyToOne
	@JoinColumn
	private Portfolio portfolio;

	public GitHub(String url, String image) {
		this.url = url;
		this.image = image;
	}

}

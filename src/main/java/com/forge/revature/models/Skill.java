package com.forge.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skills")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Skill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	
	private int value;
	
	@ManyToOne
    @JoinColumn(name = "matrix_id")
    private Matrix matrix;

	public Skill(String name, int value, Matrix matrix) {
		super();
		this.name = name;
		this.value = value;
		this.matrix = matrix;
	}
	
	public Skill(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
}

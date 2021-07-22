package com.forge.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skills")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Skill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String header;
	
	@Column
	private int value;
	
	@ManyToOne
    @JoinColumn(name = "matrix_id")
    private Matrix matrix;

	public Skill(String header, int value, Matrix matrix) {
		super();
		this.header = header;
		this.value = value;
		this.matrix = matrix;
	}
	
}

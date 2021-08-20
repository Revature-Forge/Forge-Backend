package com.forge.revature.models;

import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.forge.revature.converter.HashMapConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "portfolios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Portfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(columnDefinition = "boolean default false")
	private boolean submitted;

	@Column(columnDefinition = "boolean default false")
	private boolean approved;

	@Column(columnDefinition = "boolean default false")
	private boolean reviewed;

	private String feedback;
	
	@ManyToOne
	@JoinColumn(name="approver_id")
	private User approver; 

	@Column
	@Convert(converter = HashMapConverter.class)
	private HashMap<String, String> flags;

	public Portfolio(String name, boolean submitted, boolean approved, boolean reviewed, String feedback) {
		this.name = name;
		this.submitted = submitted;
		this.approved = approved;
		this.reviewed = reviewed;
		this.feedback = feedback;
	}

}
